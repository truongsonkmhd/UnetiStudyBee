package com.truongsonkmhd.unetistudy.sevice.impl.course;

import com.github.slugify.Slugify;
import com.truongsonkmhd.unetistudy.common.CourseStatus;
import com.truongsonkmhd.unetistudy.context.UserContext;
import com.truongsonkmhd.unetistudy.dto.coding_exercise_dto.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.course_dto.*;
import com.truongsonkmhd.unetistudy.dto.lesson_dto.CourseLessonRequest;
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
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.Quiz;
import com.truongsonkmhd.unetistudy.repository.*;
import com.truongsonkmhd.unetistudy.repository.coding.CodingExerciseRepository;
import com.truongsonkmhd.unetistudy.repository.course.*;
import com.truongsonkmhd.unetistudy.sevice.CourseTreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    private static final Comparator<Integer> NULL_SAFE_INT =
            Comparator.nullsLast(Integer::compareTo);

    // =========================
    // BASIC CRUD
    // =========================
    @Override
    public CourseTreeResponse findById(UUID theId) {
        Course course = courseRepository.findById(theId)
                .orElseThrow(() -> new RuntimeException("Course not found with id =" + theId));
        return courseResponseMapper.toDto(course);
    }

    @Override
    @Transactional
    public CourseTreeResponse save(CourseShowRequest req) {
        User instructor = userRepository.findById(req.getInstructorId())
                .orElseThrow(() -> new DataNotFoundException("Instructor not found: " + req.getInstructorId()));

        Course course = courseRequestMapper.toEntity(req);
        course.setInstructor(instructor);

        String baseSlug = slugify.slugify(req.getTitle());
        course.setSlug(generateUniqueSlug(baseSlug));

        if (course.getEnrolledCount() == null) course.setEnrolledCount(0);
        if (course.getRating() == null) course.setRating(java.math.BigDecimal.ZERO);
        if (course.getRatingCount() == null) course.setRatingCount(0);

        if (course.getStatus() == null) course.setStatus(CourseStatus.DRAFT);
        if (course.getIsPublished() == null) course.setIsPublished(req.getIsPublished() != null ? req.getIsPublished() : false);

        // publishedAt chỉ set khi publish
        if (Boolean.TRUE.equals(course.getIsPublished())) {
            if (course.getPublishedAt() == null) {
                course.setPublishedAt(req.getPublishedAt() != null ? req.getPublishedAt() : java.time.LocalDateTime.now());
            }
        } else {
            course.setPublishedAt(null);
        }

        List<CourseModule> modules = courseModuleRequestMapper.toEntity(req.getModules());

//        if (modules != null) {
//            for (CourseModule m : modules) {
//                course.addCourse(m);
//            }
//        }

        if (modules != null) {
            for (CourseModule m : modules) {
                m.setCourse(course);
            }
        }
        course.setModules(modules);
        Course saved = courseRepository.save(course);
        return courseResponseMapper.toDto(saved);

    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "course_published_tree", allEntries = true)
    public CourseTreeResponse update(UUID courseId, CourseShowRequest req) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new DataNotFoundException("Course not found: " + courseId));

        // instructor
        if (req.getInstructorId() != null) {
            User instructor = userRepository.findById(req.getInstructorId())
                    .orElseThrow(() -> new DataNotFoundException("Instructor not found: " + req.getInstructorId()));
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
        course.setImageUrl(req.getImageUrl());
        course.setVideoUrl(req.getVideoUrl());
        course.setRequirements(req.getRequirements());
        course.setObjectives(req.getObjectives());
        course.setSyllabus(req.getSyllabus());
        course.setStatus(req.getStatus());
        course.setIsPublished(req.getIsPublished());
        course.setPublishedAt(req.getPublishedAt());

        // slug update (optional)
        String newBaseSlug = slugify.slugify(req.getTitle());
        if (newBaseSlug != null && !newBaseSlug.isBlank()) {
            if (!newBaseSlug.equals(course.getSlug())) {
                course.setSlug(generateUniqueSlugForUpdate(newBaseSlug, course.getCourseId()));
            }
        }

        // upsert tree
        syncModules(course, req.getModules());

        Course saved = courseRepository.save(course);
        return courseResponseMapper.toDto(saved);
    }

    @Override
    @Transactional
    public UUID deleteById(UUID theId) {
        courseRepository.deleteById(theId);
        return theId;
    }

    @Override
    public List<CourseModuleResponse> getCourseModuleByCourseSlug(String theSlug) {
        return courseModuleResponseMapper.toDto(courseModuleRepository.getCourseModuleByCourseSlug(theSlug));
    }


    @Override
    @Cacheable(cacheNames = "course_published_tree", key = "#slug")
    @Transactional(readOnly = true)
    public CourseTreeResponse getCourseTreeDetailPublished(String slug) {
        UUID userId = UserContext.getUserID();
        return getCourseTree(slug, userRepository.findRolesByUserId(userId));
    }

    private CourseTreeResponse getCourseTree(String slug, Set<Role> mode) {
        Course course = courseRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException("Course not found: " + slug));
        UUID courseId = course.getCourseId();

        List<CourseModule> courseModule = courseModuleRepository.findModulesByCourseId(courseId);
        List<UUID> moduleIds = courseModule.stream().map(CourseModule::getModuleId).toList();

        List<CourseLesson> lessons = lessonRepository.findLessonsByModuleIds(moduleIds);
        List<UUID> lessonIds = lessons.stream().map(CourseLesson::getLessonId).toList();

        List<CodingExercise> exercises = codingExerciseRepository.findExercisesByLessonIds(lessonIds);
        List<Quiz> quizzes = quizRepository.findQuizzesByLessonIds(lessonIds);

        Map<UUID, List<CodingExercise>> exByLesson = exercises.stream()
                .collect(Collectors.groupingBy(e -> e.getContestLesson().getContestLessonId()));

        Map<UUID, List<Quiz>> quizByLesson = quizzes.stream()
                .collect(Collectors.groupingBy(q -> q.getContestLesson().getContestLessonId()));

        return mapCourse(course,courseModule,lessons, mode , exByLesson ,quizByLesson);
    }

    // =========================
    // MAPPING (FILTER INSIDE)
    // =========================

    private CourseTreeResponse mapCourse(Course course, List<CourseModule> courseModule, List<CourseLesson> courseLessons, Set<Role> mode , Map<UUID, List<CodingExercise>> exByLesson, Map<UUID, List<Quiz>> quizByLesson) {
        List<CourseModuleResponse> modules = courseModule.stream()
                .sorted(Comparator.comparing(CourseModule::getOrderIndex, NULL_SAFE_INT))
               // .filter(m -> !isOnlyStudent(mode) || allowPublished(m.getIsPublished()))
                .filter(m ->allowPublished(m.getIsPublished()))
                .map(m -> mapModule(m ,courseLessons, mode , exByLesson,quizByLesson))
                .toList();

        return new CourseTreeResponse(
                course.getCourseId(),
                course.getTitle(),
                course.getSlug(),
                course.getDescription(),
                course.getIsPublished(),
                course.getStatus(),
                modules
        );
    }

    private CourseModuleResponse mapModule(CourseModule m, List<CourseLesson> courseLessons, Set<Role> mode , Map<UUID, List<CodingExercise>> exByLesson, Map<UUID, List<Quiz>> quizByLesson) {

        List<CourseLessonResponse> lessons = courseLessons.stream()
                .sorted(Comparator.comparing(CourseLesson::getOrderIndex, NULL_SAFE_INT))
               // .filter(l -> !isOnlyStudent(mode) || allowLessonForStudent(l))
                 .filter(l -> allowLessonForStudent(l))
                .map(l -> mapLesson(l, mode, exByLesson,quizByLesson))
                .toList();

        return new CourseModuleResponse(
                m.getModuleId(),
                m.getTitle(),
                m.getOrderIndex(),
                m.getIsPublished(),
                lessons
        );
    }

    private CourseLessonResponse mapLesson(CourseLesson courseLesson, Set<Role> mode , Map<UUID, List<CodingExercise>> exByLesson, Map<UUID, List<Quiz>> quizByLesson) {

        List<CodingExerciseDTO> coding = exByLesson.getOrDefault(courseLesson.getLessonId(), List.of()).stream()
                .filter(e ->  allowPublished(e.getIsPublished()))
                .map(this::mapCoding)
                .toList();


        List<QuizDTO> quizzes = quizByLesson.getOrDefault(courseLesson.getLessonId(), List.of()).stream()
                .filter(q -> allowPublished(q.getIsPublished()))
                .map(this::mapQuiz)
                .toList();


        return new CourseLessonResponse(
                courseLesson.getLessonId(),
                courseLesson.getTitle(),
                courseLesson.getOrderIndex(),
                courseLesson.getLessonType(),
                courseLesson.getIsPreview(),
                courseLesson.getIsPublished(),
                coding,
                quizzes
        );
    }

    private CodingExerciseDTO mapCoding(CodingExercise e) {
        if (e == null) return null;

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
        if (q == null) return null;

        return QuizDTO.builder()
                .quizId(q.getQuizId())
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

    private void syncModules(Course course, List<CourseModuleRequest> moduleRequests) {
        if (moduleRequests == null) moduleRequests = List.of();

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

            syncLessons(module, mr.getLessons());
            newList.add(module);
        }

        course.getModules().clear();
        course.getModules().addAll(newList);
    }

    private void syncLessons(CourseModule module, List<CourseLessonRequest> courseLessonRequests) {
        if (courseLessonRequests == null) courseLessonRequests = List.of();

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
            }

            // set/update fields
            lesson.setTitle(lr.getTitle());
            lesson.setDescription(lr.getDescription());
            lesson.setLessonType(lr.getLessonType());
            lesson.setContent(lr.getContent());
            lesson.setVideoUrl(lr.getVideoUrl());
            lesson.setDuration(lr.getDuration());
            lesson.setOrderIndex(lr.getOrderIndex());
            lesson.setIsPreview(Boolean.TRUE.equals(lr.getIsPreview()));
            lesson.setIsPublished(Boolean.TRUE.equals(lr.getIsPublished()));
            lesson.setSlug(lr.getSlug());
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
            if (found.isEmpty()) return slug;
            if (found.get().getCourseId().equals(courseId)) return slug;

            slug = baseSlug + "-" + counter;
            counter++;
        }
    }

    // =========================
    // FILTER HELPERS
    // =========================
//    private boolean isOnlyStudent(Set<Role> roles) {
//        return roles != null && roles.stream()
//                        .anyMatch(r -> UserType.STUDENT.getValue().equals(r.getCode()));
//    }

    private boolean allowPublished(Boolean flag) {
        return Boolean.TRUE.equals(flag);
    }

    private boolean allowLessonForStudent(CourseLesson l) {
        return Boolean.TRUE.equals(l.getIsPublished()) || Boolean.TRUE.equals(l.getIsPreview());
    }
}
