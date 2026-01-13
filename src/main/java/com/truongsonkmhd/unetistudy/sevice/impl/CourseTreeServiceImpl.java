package com.truongsonkmhd.unetistudy.sevice.impl;

import com.github.slugify.Slugify;
import com.truongsonkmhd.unetistudy.common.UserType;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.*;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonRequest;
import com.truongsonkmhd.unetistudy.exception.payload.DataNotFoundException;
import com.truongsonkmhd.unetistudy.mapper.course.CourseModuleRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.course.CourseModuleResponseMapper;
import com.truongsonkmhd.unetistudy.mapper.course.CourseRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.course.CourseResponseMapper;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.model.course.Course;
import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;
import com.truongsonkmhd.unetistudy.model.course.CourseModule;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.model.lesson.Quiz;
import com.truongsonkmhd.unetistudy.repository.CourseModuleRepository;
import com.truongsonkmhd.unetistudy.repository.CourseRepository;
import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.sevice.CourseTreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseTreeServiceImpl implements CourseTreeService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    private final CourseResponseMapper courseResponseMapper;
    private final CourseRequestMapper courseRequestMapper;

    private final CourseModuleRequestMapper courseModuleRequestMapper;
    private final CourseModuleResponseMapper courseModuleResponseMapper;

    private final CourseModuleRepository courseModuleRepository;

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

        String baseSlug = new Slugify().slugify(req.getTitle());
        course.setSlug(generateUniqueSlug(baseSlug));

        course.setModules(convertToCourseModule(req.getModules(), course));

        courseRepository.save(course);
        return courseResponseMapper.toDto(course);
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
        String newBaseSlug = new Slugify().slugify(req.getTitle());
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

    // =========================
    // TREE API (NO DUPLICATION)
    // =========================

    @Transactional(readOnly = true)
    @Override
    public CourseTreeResponse getCourseTreeAdminDetail(String slug) {
        return getCourseTree(slug, UserType.ADMIN);
    }

    @Override
    @Cacheable(cacheNames = "course_published_tree", key = "#slug")
    @Transactional(readOnly = true)
    public CourseTreeResponse getCourseTreeStudentDetailPublished(String slug) {
        return getCourseTree(slug, UserType.STUDENT);
    }

    private CourseTreeResponse getCourseTree(String slug, UserType mode) {
        Course course = courseRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Course not found: " + slug));
        return mapCourse(course, mode);
    }

    // =========================
    // MAPPING (FILTER INSIDE)
    // =========================

    private CourseTreeResponse mapCourse(Course c, UserType mode) {

        List<CourseModuleResponse> modules = c.getModules().stream()
                .sorted(Comparator.comparing(CourseModule::getOrderIndex, NULL_SAFE_INT))
                .filter(m -> !isStudent(mode) || allowPublished(m.getIsPublished()))
                .map(m -> mapModule(m, mode))
                .toList();

        return new CourseTreeResponse(
                c.getCourseId(),
                c.getTitle(),
                c.getSlug(),
                c.getDescription(),
                c.getIsPublished(),
                c.getStatus(),
                modules
        );
    }

    private CourseModuleResponse mapModule(CourseModule m, UserType mode) {

        List<CourseLessonResponse> lessons = m.getLessons().stream()
                .sorted(Comparator.comparing(CourseLesson::getOrderIndex, NULL_SAFE_INT))
                .filter(l -> !isStudent(mode) || allowLessonForStudent(l))
                .map(l -> mapLesson(l, mode))
                .toList();

        return new CourseModuleResponse(
                m.getModuleId(),
                m.getTitle(),
                m.getOrderIndex(),
                m.getIsPublished(),
                lessons
        );
    }

    private CourseLessonResponse mapLesson(CourseLesson l, UserType mode) {

        List<CodingExerciseDTO> coding = l.getCodingExercises().stream()
                .filter(e -> !isStudent(mode) || allowPublished(e.getIsPublished()))
                .map(this::mapCoding)
                .toList();

        List<QuizDTO> quizzes = l.getQuizzes().stream()
                .filter(q -> !isStudent(mode) || allowPublished(q.getIsPublished()))
                .map(this::mapQuiz)
                .toList();

        return new CourseLessonResponse(
                l.getLessonId(),
                l.getTitle(),
                l.getType(),
                l.getOrderIndex(),
                l.getIsPreview(),
                l.getIsPublished(),
                coding,
                quizzes
        );
    }

    private CodingExerciseDTO mapCoding(CodingExercise e) {
        if (e == null) return null;

        return CodingExerciseDTO.builder()
                .exerciseId(e.getExerciseId())
                .lessonId(e.getLesson() != null ? e.getLesson().getLessonId() : null)
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

        // đổi sang builder để đồng bộ style (QuizDTO của bạn cần @Builder)
        return QuizDTO.builder()
                .quizId(q.getQuizId())
                .lessonId(q.getLesson() != null ? q.getLesson().getLessonId() : null)
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

    private void syncLessons(CourseModule module, List<LessonRequest> lessonRequests) {
        if (lessonRequests == null) lessonRequests = List.of();

        Map<UUID, CourseLesson> existing = new HashMap<>();
        for (CourseLesson l : module.getLessons()) {
            existing.put(l.getLessonId(), l);
        }

        List<CourseLesson> newList = new ArrayList<>();

        for (LessonRequest lr : lessonRequests) {
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
            lesson.setType(lr.getType());
            lesson.setContent(lr.getContent());
            lesson.setVideoUrl(lr.getVideoUrl());
            lesson.setDuration(lr.getDuration());
            lesson.setOrderIndex(lr.getOrderIndex());
            lesson.setIsPreview(Boolean.TRUE.equals(lr.getIsPreview()));
            lesson.setIsPublished(Boolean.TRUE.equals(lr.getIsPublished()));
            lesson.setSlug(lr.getSlug());
            lesson.setIsContest(Boolean.TRUE.equals(lr.getIsContest()));
            lesson.setTotalPoints(lr.getTotalPoints() != null ? lr.getTotalPoints() : 0);
            lesson.setContestStartTime(lr.getContestStartTime());
            lesson.setContestEndTime(lr.getContestEndTime());

            newList.add(lesson);
        }

        module.getLessons().clear();
        module.getLessons().addAll(newList);
    }

    // =========================
    // CREATE HELPERS
    // =========================

    private @NonNull List<CourseModule> convertToCourseModule(List<CourseModuleRequest> courseModules, Course course) {
        if (courseModules == null) return new ArrayList<>();

        List<CourseModule> result = new ArrayList<>();
        courseModules.forEach(a -> {
            var courseModule = courseModuleRequestMapper.toEntity(a);
            courseModule.setCourse(course);
            result.add(courseModule);
        });
        return result;
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

    private boolean isStudent(UserType mode) {
        return mode == UserType.STUDENT;
    }

    private boolean allowPublished(Boolean flag) {
        return Boolean.TRUE.equals(flag);
    }

    private boolean allowLessonForStudent(CourseLesson l) {
        return Boolean.TRUE.equals(l.getIsPublished()) || Boolean.TRUE.equals(l.getIsPreview());
    }
}
