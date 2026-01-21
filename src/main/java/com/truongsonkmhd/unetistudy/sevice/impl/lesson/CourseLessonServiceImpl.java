package com.truongsonkmhd.unetistudy.sevice.impl.lesson;

import com.github.slugify.Slugify;
import com.truongsonkmhd.unetistudy.dto.exercise_test_cases_dto.ExerciseTestCasesDTO;
import com.truongsonkmhd.unetistudy.dto.lesson_dto.CourseLessonResponse;
import com.truongsonkmhd.unetistudy.dto.lesson_dto.CourseLessonRequest;
import com.truongsonkmhd.unetistudy.exception.ResourceNotFoundException;
import com.truongsonkmhd.unetistudy.exception.payload.DataNotFoundException;
import com.truongsonkmhd.unetistudy.mapper.coding_submission.ExerciseTestCaseMapper;
import com.truongsonkmhd.unetistudy.mapper.coding_submission.QuizExerciseMapper;
import com.truongsonkmhd.unetistudy.mapper.lesson.CourseLessonRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.lesson.CourseLessonResponseMapper;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.model.lesson.solid.course_lesson.*;
import com.truongsonkmhd.unetistudy.model.course.CourseModule;
import com.truongsonkmhd.unetistudy.model.lesson.template.CodingExerciseTemplate;
import com.truongsonkmhd.unetistudy.repository.coding.CodingExerciseTemplateRepository;
import com.truongsonkmhd.unetistudy.repository.course.CourseLessonRepository;
import com.truongsonkmhd.unetistudy.repository.course.CourseModuleRepository;
import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.sevice.ContestLessonService;
import com.truongsonkmhd.unetistudy.sevice.CourseLessonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseLessonServiceImpl implements CourseLessonService {

    private final CourseLessonRepository courseLessonRepository;
    private final CourseLessonResponseMapper courseLessonResponseMapper;
    private final CourseLessonRequestMapper courseLessonRequestMapper;
    private final ExerciseTestCaseMapper exerciseTestCaseMapper;
    private final QuizExerciseMapper quizExerciseMapper;
    private final CourseModuleRepository courseModuleRepository;
    private final UserRepository userRepository;
    private final CodingExerciseTemplateRepository templateRepository;
    private final ContestLessonService contestLessonService;

    @Override
    public List<CourseLessonResponse> getLessonByModuleId(UUID moduleId) {
        return courseLessonResponseMapper.toDto(courseLessonRepository.getLessonByModuleId(moduleId));
    }

    @Override
    public List<CourseLessonResponse> getLessonByModuleIDAndSlug(UUID moduleID, String slug) {
        return courseLessonResponseMapper.toDto(courseLessonRepository.getLessonByModuleIdAndSlug(moduleID, slug));
    }

    @Override
    public List<CourseLessonResponse> getLessonAll() {
        var listCourseLesson = courseLessonRepository.findAll();
        return courseLessonResponseMapper.toDto(listCourseLesson);
    }

    @Override
    @Transactional
    public CourseLessonResponse addLesson(CourseLessonRequest request) {
        // Validate module exists
        CourseModule existsCourseModule = courseModuleRepository
                .findById(request.getModuleId())
                .orElseThrow(() -> new DataNotFoundException(
                        "CourseModule not found: " + request.getModuleId()
                ));

        // Validate user exists
        User user = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new DataNotFoundException(
                        "User not found: " + request.getCreatorId()
                ));

        // Map request to CourseLesson entity
        CourseLesson lesson = courseLessonRequestMapper.toEntity(request);
        lesson.setCreator(user);
        lesson.setModule(existsCourseModule);

        // Generate unique slug
        String baseSlug = new Slugify().slugify(request.getTitle());
        lesson.setSlug(generateUniqueSlug(baseSlug));

        ContestLesson contestLesson = createContestLesson(request, lesson);
        lesson.setContestLesson(contestLesson);

        // Save lesson (will cascade to contest if present)
        CourseLesson savedLesson = courseLessonRepository.save(lesson);

        log.info("Successfully saved lesson with ID: {}", savedLesson.getLessonId());

        return courseLessonResponseMapper.toDto(savedLesson);
    }

    /**
     * Create ContestLesson with exercises
     * Supports 2 modes:
     * 1. Create from DTO (new exercises)
     * 2. Create from Template IDs (reuse existing templates)
     */
    private ContestLesson createContestLesson(CourseLessonRequest request, CourseLesson lesson) {
        ContestLesson contestLesson = ContestLesson.builder()
                .courseLesson(lesson)
                .startTime(request.getContestStartTime() != null
                        ? request.getContestStartTime().toInstant()
                        : Instant.now())
                .endTime(request.getContestEndTime() != null
                        ? request.getContestEndTime().toInstant()
                        : Instant.now().plusSeconds(3600))
                .totalPoints(request.getTotalPoints() != null ? request.getTotalPoints() : 0)
                .isActive(true)
                .showLeaderboard(true)
                .build();

        // Add coding exercises
        contestLessonService.addCodingExercisesToContest(request.getExerciseTemplateIds(), contestLesson);

        // Add quiz questions
       addQuizQuestionsToContest(request, contestLesson);

        return contestLesson;
    }

    /**
     * Add quiz questions to contest
     */
    private void addQuizQuestionsToContest(CourseLessonRequest request, ContestLesson contestLesson) {
        if (request.getQuizzes() != null && !request.getQuizzes().isEmpty()) {
            for (var quizDto : request.getQuizzes()) {
                QuizQuestion quiz = quizExerciseMapper.toEntity(quizDto);
                contestLesson.addQuizQuestion(quiz);
            }
        }
    }

    @Override
    @Transactional
    public CourseLessonResponse update(UUID theId, CourseLessonRequest request) {
        CourseLesson existing = courseLessonRepository.findById(theId)
                .orElseThrow(() -> new ResourceNotFoundException("CourseLesson not found with id = " + theId));

        courseLessonRequestMapper.partialUpdate(existing, request);

        CourseLesson updated = courseLessonRepository.save(existing);
        return courseLessonResponseMapper.toDto(updated);
    }

    @Override
    public UUID delete(UUID theId) {
        courseLessonRepository.deleteById(theId);
        return theId;
    }

    @Override
    public Optional<CourseLesson> findById(UUID id) {
        return courseLessonRepository.findById(id);
    }

    private String generateUniqueSlug(String baseSlug) {
        String slug = baseSlug;
        int counter = 1;
        while (courseLessonRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        return slug;
    }
}