package com.truongsonkmhd.unetistudy.service.impl.course;

import com.github.slugify.Slugify;
import com.truongsonkmhd.unetistudy.cache.CacheConstants;
import com.truongsonkmhd.unetistudy.common.CourseStatus;
import com.truongsonkmhd.unetistudy.context.UserContext;
import com.truongsonkmhd.unetistudy.dto.a_common.PageResponse;
import com.truongsonkmhd.unetistudy.dto.coding_exercise_dto.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.course_dto.*;
import com.truongsonkmhd.unetistudy.dto.lesson_dto.CourseLessonRequest;
import com.truongsonkmhd.unetistudy.dto.quiz_dto.QuizDTO;
import com.truongsonkmhd.unetistudy.exception.payload.DataNotFoundException;
import com.truongsonkmhd.unetistudy.mapper.course.CourseModuleRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.course.CourseModuleResponseMapper;
import com.truongsonkmhd.unetistudy.mapper.course.CourseRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.course.CourseResponseMapper;
import com.truongsonkmhd.unetistudy.model.Role;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.model.course.Course;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.CourseLesson;
import com.truongsonkmhd.unetistudy.model.course.CourseModule;
import com.truongsonkmhd.unetistudy.model.quiz.Quiz;
import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.repository.coding.CodingExerciseRepository;
import com.truongsonkmhd.unetistudy.repository.course.CourseRepository;
import com.truongsonkmhd.unetistudy.repository.course.CourseModuleRepository;
import com.truongsonkmhd.unetistudy.repository.course.LessonRepository;
import com.truongsonkmhd.unetistudy.repository.course.QuizRepository;
import com.truongsonkmhd.unetistudy.service.CourseTreeService;
import com.truongsonkmhd.unetistudy.service.infrastructure.PocketBaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseTreeServiceImpl implements CourseTreeService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final CourseModuleRepository courseModuleRepository;
    private final CodingExerciseRepository codingExerciseRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CourseResponseMapper courseResponseMapper;
    private final CourseRequestMapper courseRequestMapper;
    private final CourseModuleRequestMapper courseModuleRequestMapper;
    private final CourseModuleResponseMapper courseModuleResponseMapper;
    private final Slugify slugify;
    private final PocketBaseService pocketBaseService;

    private static final Comparator<Integer> NULL_SAFE_INT = Comparator.nullsLast(Integer::compareTo);

    // =========================
    // BASIC CRUD
    // =========================
    /**
     * Cache-Aside: Lấy course by ID
     */
    @Override
    @Cacheable(cacheNames = CacheConstants.COURSE_BY_ID, key = "#theId", unless = "#result == null")
    public CourseTreeResponse findById(UUID theId) {
        log.debug("Cache MISS - Loading course from DB: {}", theId);
        Course course = courseRepository.findById(theId)
                .orElseThrow(() -> new RuntimeException("Course not found with id =" + theId));
        return courseResponseMapper.toDto(course);
    }

    @Override
    @Transactional
    public CourseTreeResponse save(@NonNull CourseShowRequest req) {

        UUID userID = UserContext.getUserID();
        User instructor = userRepository.findById(userID)
                .orElseThrow(() -> new DataNotFoundException("Instructor not found: " + userID));

        Course course = courseRequestMapper.toEntity(req);
        course.setInstructor(instructor);

        // Upload intro video if exists
        if (req.getVideoFile() != null && !req.getVideoFile().isEmpty()) {
            String pbUrl = pocketBaseService.uploadFile("course_videos", req.getVideoFile());
            if (pbUrl != null) {
                course.setVideoUrl(pbUrl);
            }
        }

        // Upload course image if exists
        if (req.getImageFile() != null && !req.getImageFile().isEmpty()) {
            String pbUrl = pocketBaseService.uploadFile("course_images", req.getImageFile());
            if (pbUrl != null) {
                course.setImageUrl(pbUrl);
            }
        }

        String baseSlug = slugify.slugify(req.getTitle());
        course.setSlug(generateUniqueSlug(baseSlug));

        if (course.getEnrolledCount() == null)
            course.setEnrolledCount(0);
        if (course.getRating() == null)
            course.setRating(java.math.BigDecimal.ZERO);
        if (course.getRatingCount() == null)
            course.setRatingCount(0);

        if (course.getStatus() == null)
            course.setStatus(CourseStatus.DRAFT);
        if (course.getIsPublished() == null)
            course.setIsPublished(req.getIsPublished() != null ? req.getIsPublished() : false);

        // publishedAt chỉ set khi publish
        if (Boolean.TRUE.equals(course.getIsPublished())) {
            if (course.getPublishedAt() == null) {
                course.setPublishedAt(
                        req.getPublishedAt() != null ? req.getPublishedAt() : java.time.LocalDateTime.now());
            }
        } else {
            course.setPublishedAt(null);
        }

        course.setModules(new ArrayList<>());
        if (req.getModules() != null) {
            syncModules(course, req.getModules(), instructor);
        }

        Course saved = courseRepository.save(course);
        return getCourseTree(saved.getSlug(), userRepository.findRolesByUserId(instructor.getId()));
    }

    /**
     * Cache Invalidation: Evict cache khi update course
     */
    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheConstants.COURSE_PUBLISHED_TREE, allEntries = true),
            @CacheEvict(cacheNames = CacheConstants.COURSE_BY_ID, key = "#courseId"),
            @CacheEvict(cacheNames = CacheConstants.COURSE_BY_SLUG, allEntries = true),
            @CacheEvict(cacheNames = CacheConstants.COURSE_MODULES, allEntries = true)
    })
    public CourseTreeResponse update(UUID courseId, CourseShowRequest req) {
        log.info("Updating course: {} - Evicting cache", courseId);
        UUID userID = UserContext.getUserID();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new DataNotFoundException("Course not found: " + courseId));
        if (userID != null) {
            User instructor = userRepository.findById(userID)
                    .orElseThrow(() -> new DataNotFoundException("Instructor not found: " + userID));
            course.setInstructor(instructor);
        }

        // fields
        course.setTitle(req.getTitle());
        course.setDescription(req.getDescription());
        course.setShortDescription(req.getShortDescription());
        course.setLevel(req.getLevel());
        course.setCategory(req.getCategory());
        course.setSubCategory(req.getSubCategory());
        course.setDuration(req.getDuration());
        course.setCapacity(req.getCapacity());
        course.setRequirements(req.getRequirements());
        course.setObjectives(req.getObjectives());
        course.setSyllabus(req.getSyllabus());
        course.setStatus(req.getStatus());
        course.setIsPublished(req.getIsPublished());
        course.setPublishedAt(req.getPublishedAt());

        // Upload/Update intro video if exists
        if (req.getVideoFile() != null && !req.getVideoFile().isEmpty()) {
            String pbUrl = pocketBaseService.uploadFile("course_videos", req.getVideoFile());
            if (pbUrl != null) {
                course.setVideoUrl(pbUrl);
            }
        }

        // Upload/Update course image if exists
        if (req.getImageFile() != null && !req.getImageFile().isEmpty()) {
            String pbUrl = pocketBaseService.uploadFile("course_images", req.getImageFile());
            if (pbUrl != null) {
                course.setImageUrl(pbUrl);
            }
        }

        // slug update (optional)
        String newBaseSlug = slugify.slugify(req.getTitle());
        if (newBaseSlug != null && !newBaseSlug.isBlank()) {
            if (!newBaseSlug.equals(course.getSlug())) {
                course.setSlug(generateUniqueSlugForUpdate(newBaseSlug, course.getCourseId()));
            }
        }

        // upsert tree
        syncModules(course, req.getModules(), course.getInstructor());

        Course saved = courseRepository.save(course);
        return getCourseTree(saved.getSlug(), userRepository.findRolesByUserId(course.getInstructor().getId()));
    }

    /**
     * Cache Invalidation: Evict cache khi delete course
     */
    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheConstants.COURSE_PUBLISHED_TREE, allEntries = true),
            @CacheEvict(cacheNames = CacheConstants.COURSE_BY_ID, key = "#theId"),
            @CacheEvict(cacheNames = CacheConstants.COURSE_BY_SLUG, allEntries = true),
            @CacheEvict(cacheNames = CacheConstants.COURSE_MODULES, allEntries = true)
    })
    public UUID deleteById(UUID theId) {
        log.info("Deleting course: {} - Evicting cache", theId);
        Course course = courseRepository.findById(theId)
                .orElseThrow(() -> new DataNotFoundException("course not found: " + theId));

        courseRepository.deleteById(course.getCourseId());
        return theId;
    }

    /**
     * Cache-Aside: Lấy modules by course slug
     */
    @Override
    @Cacheable(cacheNames = CacheConstants.COURSE_MODULES, key = "#theSlug", unless = "#result.isEmpty()")
    public List<CourseModuleResponse> getCourseModuleByCourseSlug(String theSlug) {
        log.debug("Cache MISS - Loading course modules for slug: {}", theSlug);
        return courseModuleResponseMapper.toDto(courseModuleRepository.getCourseModuleByCourseSlug(theSlug));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CourseCardResponse> getAllCourses(Integer page, Integer size, String q, String status,
            String category) {
        int safePage = (page != null) ? Math.max(page, 0) : 0;
        int safeSize = (size != null) ? Math.min(Math.max(size, 1), 50) : 10;

        Pageable pageable = PageRequest.of(safePage, safeSize);
        CourseStatus courseStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                courseStatus = CourseStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("Invalid course status: {}", status);
            }
        }
        Page<CourseCardResponse> result = courseRepository.findCourseCardsWithFilters(q, courseStatus, category,
                pageable);
        return PageResponse.<CourseCardResponse>builder()
                .items(result.getContent())
                .page(result.getNumber())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .hasNext(result.hasNext())
                .build();
    }

    @Override
    @Cacheable(cacheNames = "course_published_tree", key = "#slug")
    @Transactional(readOnly = true)
    public CourseTreeResponse getCourseTreeDetailPublished(String slug) {
        if (slug == null || slug.isBlank()) {
            throw new DataNotFoundException("Course slug must not be null or empty");
        }
        UUID userId = UserContext.getUserID();
        Set<Role> roles = (userId != null) ? userRepository.findRolesByUserId(userId) : Collections.emptySet();
        return getCourseTree(slug, roles);
    }

    private CourseTreeResponse getCourseTree(String slug, Set<Role> roles) {
        if (slug == null || slug.isBlank()) {
            throw new DataNotFoundException("Course slug must not be null or empty");
        }
        Course course = courseRepository.findBySlug(slug)
                .orElseThrow(() -> new DataNotFoundException("Course not found with slug: " + slug));

        UUID courseId = course.getCourseId();

        List<CourseModule> courseModule = courseModuleRepository.findModulesByCourseId(courseId);
        List<UUID> moduleIds = courseModule.stream().map(CourseModule::getModuleId).toList();

        List<CourseLesson> lessons = lessonRepository.findLessonsByModuleIds(moduleIds);
        List<UUID> lessonIds = lessons.stream().map(CourseLesson::getLessonId).toList();

        List<CodingExercise> exercises = codingExerciseRepository.findExercisesByLessonIds(lessonIds);
        List<Quiz> quizzes = quizRepository.findQuizzesByLessonIds(lessonIds);

        Map<UUID, List<CodingExercise>> exByLesson = exercises.stream()
                .filter(e -> e.getCourseLesson() != null)
                .collect(Collectors.groupingBy(e -> e.getCourseLesson().getLessonId()));

        Map<UUID, List<Quiz>> quizByLesson = quizzes.stream()
                .filter(q -> q.getCourseLesson() != null)
                .collect(Collectors.groupingBy(q -> q.getCourseLesson().getLessonId()));

        return mapCourse(course, courseModule, lessons, roles, exByLesson, quizByLesson);
    }

    // =========================
    // MAPPING (FILTER INSIDE)
    // =========================

    private CourseTreeResponse mapCourse(Course course, List<CourseModule> courseModule,
            List<CourseLesson> courseLessons, Set<Role> roles, Map<UUID, List<CodingExercise>> exByLesson,
            Map<UUID, List<Quiz>> quizByLesson) {

        List<CourseModuleResponse> modules = courseModule.stream()
                .sorted(Comparator.comparing(CourseModule::getOrderIndex, NULL_SAFE_INT))
                .filter(m -> !isOnlyStudent(roles) || allowPublished(m.getIsPublished()))
                .map(m -> mapModule(m, courseLessons, roles, exByLesson, quizByLesson))
                .toList();

        return new CourseTreeResponse(
                course.getCourseId(),
                course.getTitle(),
                course.getSlug(),
                course.getDescription(),
                course.getIsPublished(),
                course.getStatus(),
                pocketBaseService.toDisplayUrl(course.getImageUrl()),
                pocketBaseService.toDisplayUrl(course.getVideoUrl()),
                modules);
    }

    private CourseModuleResponse mapModule(CourseModule m, List<CourseLesson> courseLessons, Set<Role> roles,
            Map<UUID, List<CodingExercise>> exByLesson, Map<UUID, List<Quiz>> quizByLesson) {

        List<CourseLessonResponse> lessons = courseLessons.stream()
                .sorted(Comparator.comparing(CourseLesson::getOrderIndex, NULL_SAFE_INT))
                .filter(l -> !isOnlyStudent(roles) || allowLessonForStudent(l))
                .map(l -> mapLesson(l, roles, exByLesson, quizByLesson))
                .toList();

        return new CourseModuleResponse(
                m.getModuleId(),
                m.getTitle(),
                m.getOrderIndex(),
                m.getIsPublished(),
                lessons);
    }

    private CourseLessonResponse mapLesson(CourseLesson courseLesson, Set<Role> roles,
            Map<UUID, List<CodingExercise>> exByLesson, Map<UUID, List<Quiz>> quizByLesson) {

        List<CodingExerciseDTO> coding = exByLesson.getOrDefault(courseLesson.getLessonId(), List.of()).stream()
                .filter(e -> allowPublished(e.getIsPublished()))
                .map(this::mapCoding)
                .toList();

        List<QuizDTO> quizzes = quizByLesson.getOrDefault(courseLesson.getLessonId(), List.of()).stream()
                .filter(q -> allowPublished(q.getIsPublished()))
                .map(this::mapQuiz)
                .toList();

        return CourseLessonResponse.builder()
                .lessonId(courseLesson.getLessonId())
                .title(courseLesson.getTitle())
                .orderIndex(courseLesson.getOrderIndex())
                .lessonType(courseLesson.getLessonType())
                .isPreview(courseLesson.getIsPreview())
                .isPublished(courseLesson.getIsPublished())
                .videoUrl(pocketBaseService.toDisplayUrl(courseLesson.getVideoUrl()))
                .description(courseLesson.getDescription())
                .content(courseLesson.getContent())
                .codingExercises(coding)
                .quizzes(quizzes)
                .build();
    }

    private CodingExerciseDTO mapCoding(CodingExercise e) {
        if (e == null)
            return null;

        return CodingExerciseDTO.builder()
                .contestLessonId(e.getContestLesson() != null ? e.getContestLesson().getContestLessonId() : null)
                .title(e.getTitle())
                .description(e.getDescription())
                .programmingLanguage(e.getProgrammingLanguage())
                .difficulty(e.getDifficulty())
                .points(e.getPoints())
                .isPublished(Boolean.TRUE.equals(e.getIsPublished()))
                .timeLimitMs(e.getTimeLimitMs())
                .memoryLimitMb(e.getMemoryLimitMb())
                .slug(e.getSlug())
                .inputFormat(e.getInputFormat())
                .outputFormat(e.getOutputFormat())
                .constraintName(e.getConstraintName())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    private QuizDTO mapQuiz(Quiz q) {
        if (q == null)
            return null;

        return QuizDTO.builder()
                .quizId(q.getId())
                .lessonId(q.getContestLesson() != null ? q.getContestLesson().getContestLessonId() : null)
                .title(q.getTitle())
                .totalQuestions(q.getTotalQuestions())
                .passScore(q.getPassScore())
                .isPublished(Boolean.TRUE.equals(q.getIsPublished()))
                .build();
    }

    // =========================
    // UPDATE TREE HELPERS
    // =========================

    private void syncModules(Course course, List<CourseModuleRequest> moduleRequests, User instructor) {
        if (moduleRequests == null)
            moduleRequests = List.of();

        Map<UUID, CourseModule> existing = new HashMap<>();
        for (CourseModule m : course.getModules()) {
            existing.put(m.getModuleId(), m);
        }

        List<CourseModule> newList = new ArrayList<>();

        for (CourseModuleRequest mr : moduleRequests) {
            UUID moduleId = mr.getModuleId();
            CourseModule module;

            if (moduleId != null && existing.containsKey(moduleId)) {
                module = existing.get(moduleId);
                module.setTitle(mr.getTitle());
                module.setDescription(mr.getDescription());
                module.setOrderIndex(mr.getOrderIndex());
                module.setDuration(mr.getDuration());
                module.setIsPublished(mr.getIsPublished());
                module.setSlug(mr.getSlug());
            } else {
                module = courseModuleRequestMapper.toEntity(mr);
                module.setCourse(course);
            }

            syncLessons(module, mr.getLessons(), instructor);
            newList.add(module);
        }

        course.getModules().clear();
        course.getModules().addAll(newList);
    }

    private void syncLessons(CourseModule module, List<CourseLessonRequest> courseLessonRequests, User instructor) {
        if (courseLessonRequests == null)
            courseLessonRequests = List.of();

        Map<UUID, CourseLesson> existing = new HashMap<>();
        for (CourseLesson l : module.getLessons()) {
            existing.put(l.getLessonId(), l);
        }

        List<CourseLesson> newList = new ArrayList<>();

        for (CourseLessonRequest lr : courseLessonRequests) {
            UUID lessonId = lr.getLessonId();
            CourseLesson lesson;

            if (lessonId != null && existing.containsKey(lessonId)) {
                lesson = existing.get(lessonId);
            } else {
                lesson = new CourseLesson();
                lesson.setLessonId(null);
                lesson.setModule(module);
                lesson.setCreator(instructor); // Set creator for new lesson
            }

            // Always ensure creator is set if it was somehow missing (though it shouldn't
            // be for existing ones)
            if (lesson.getCreator() == null) {
                lesson.setCreator(instructor);
            }

            // set/update fields
            lesson.setTitle(lr.getTitle());
            lesson.setDescription(lr.getDescription());
            lesson.setLessonType(lr.getLessonType());
            lesson.setContent(lr.getContent());
            lesson.setVideoUrl(lr.getVideoUrl());
            lesson.setOrderIndex(lr.getOrderIndex());
            lesson.setIsPreview(Boolean.TRUE.equals(lr.getIsPreview()));
            lesson.setIsPublished(Boolean.TRUE.equals(lr.getIsPublished()));


            String lessonSlug = lr.getSlug();
            if (lessonSlug == null || lessonSlug.isBlank()) {
                String title = lr.getTitle() != null ? lr.getTitle() : "lesson";
                String baseSlug = slugify.slugify(title);
                lessonSlug = generateUniqueLessonSlug(baseSlug);
            }
            lesson.setSlug(lessonSlug);

            // Upload lesson video if exists
            if (lr.getVideoFile() != null && !lr.getVideoFile().isEmpty()) {
                String pbUrl = pocketBaseService.uploadFile("lesson_videos", lr.getVideoFile());
                if (pbUrl != null) {
                    lesson.setVideoUrl(pbUrl);
                }
            } else if (lr.getVideoUrl() != null) {
                lesson.setVideoUrl(lr.getVideoUrl());
            }

            newList.add(lesson);
        }

        module.getLessons().clear();
        module.getLessons().addAll(newList);
    }

    // =========================
    // SLUG
    // =========================

    public String generateUniqueSlug(String baseSlug) {
        String slug = baseSlug;
        int counter = 1;
        while (courseRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        return slug;
    }

    public String generateUniqueSlugForUpdate(String baseSlug, UUID courseId) {
        String slug = baseSlug;
        int counter = 1;

        while (true) {
            Optional<Course> found = courseRepository.findBySlug(slug);
            if (found.isEmpty())
                return slug;
            if (found.get().getCourseId().equals(courseId))
                return slug;

            slug = baseSlug + "-" + counter;
            counter++;
        }
    }

    private String generateUniqueLessonSlug(String baseSlug) {
        String slug = baseSlug;
        int counter = 1;
        while (lessonRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        return slug;
    }

    // =========================
    // FILTER HELPERS
    // =========================
    private boolean isOnlyStudent(Set<Role> roles) {
        if (roles == null || roles.isEmpty())
            return true;
        return roles.stream()
                .allMatch(r -> com.truongsonkmhd.unetistudy.common.UserType.STUDENT.getValue().equals(r.getCode()));
    }

    private boolean allowPublished(Boolean flag) {
        return Boolean.TRUE.equals(flag);
    }

    private boolean allowLessonForStudent(CourseLesson l) {
        return Boolean.TRUE.equals(l.getIsPublished()) || Boolean.TRUE.equals(l.getIsPreview());
    }
}
