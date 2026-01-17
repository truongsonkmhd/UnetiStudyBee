package com.truongsonkmhd.unetistudy.sevice.impl.lesson;

import com.github.slugify.Slugify;
import com.truongsonkmhd.unetistudy.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.CourseLessonResponse;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.CourseLessonRequest;
import com.truongsonkmhd.unetistudy.exception.ResourceNotFoundException;
import com.truongsonkmhd.unetistudy.exception.payload.DataNotFoundException;
import com.truongsonkmhd.unetistudy.mapper.coding_submission.CodingExerciseMapper;
import com.truongsonkmhd.unetistudy.mapper.coding_submission.ExerciseTestCaseMapper;
import com.truongsonkmhd.unetistudy.mapper.coding_submission.QuizExerciseMapper;
import com.truongsonkmhd.unetistudy.mapper.lesson.CourseLessonRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.lesson.CourseLessonResponseMapper;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;
import com.truongsonkmhd.unetistudy.model.course.CourseModule;
import com.truongsonkmhd.unetistudy.model.lesson.ExerciseTestCase;
import com.truongsonkmhd.unetistudy.model.lesson.Quiz;
import com.truongsonkmhd.unetistudy.repository.CourseLessonRepository;
import com.truongsonkmhd.unetistudy.repository.CourseModuleRepository;
import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.sevice.CourseLessonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseLessonServiceImpl implements CourseLessonService {

    private final CourseLessonRepository courseLessonRepository;

    private final CourseLessonResponseMapper courseLessonResponseMapper;

    private final CourseLessonRequestMapper courseLessonRequestMapper;

    private final ExerciseTestCaseMapper exerciseTestCaseMapper;

    private final CodingExerciseMapper codingExerciseMapper;

    private final QuizExerciseMapper quizExerciseMapper;

    private final CourseModuleRepository courseModuleRepository;

    private final UserRepository userRepository;

    @Override
    public List<CourseLessonResponse> getLessonByModuleId(UUID moduleId) {
        return courseLessonResponseMapper.toDto(courseLessonRepository.getLessonByModuleId(moduleId)) ;
    }

    @Override
    public List<CourseLessonResponse> getLessonByModuleIDAndSlug(UUID moduleID, String slug) {
        return courseLessonResponseMapper.toDto(courseLessonRepository.getLessonByModuleIdAndSlug(moduleID,slug));
    }

    @Override
    public List<CourseLessonResponse> getCodingContest(UUID moduleID) {
        return courseLessonResponseMapper.toDto(courseLessonRepository.getCodingContest(moduleID));
    }

    @Override
    public List<CourseLessonResponse> getMultipleChoiceContest(UUID moduleID) {
        return courseLessonResponseMapper.toDto(courseLessonRepository.getMultipleChoiceContest(moduleID));
    }

    @Override
    public List<CourseLessonResponse> getLessonAll() {
        var listCourseLesson = courseLessonRepository.findAll();
        return courseLessonResponseMapper.toDto(listCourseLesson);
    }

    @Override
    @Transactional
    public CourseLessonResponse addLesson(CourseLessonRequest request) {

        CourseModule existsCourseModule = courseModuleRepository
                .findById(request.getModuleId())
                .orElseThrow(() -> new DataNotFoundException(
                        "CourseModule not found: " + request.getModuleId()
                ));

        User user = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new DataNotFoundException(
                        "User not found: " + request.getCreatorId()
                ));

        CourseLesson lesson = courseLessonRequestMapper.toEntity(request);
        lesson.setCreator(user);
        lesson.setModule(existsCourseModule);

        // Set slug
        String baseSlug = new Slugify().slugify(request.getTitle());
        lesson.setSlug(generateUniqueSlug(baseSlug));

        // Map coding exercises
        if (request.getCodingExercises() != null && !request.getCodingExercises().isEmpty()) {
            for (var exerciseDto : request.getCodingExercises()) {
                CodingExercise exercise = codingExerciseMapper.toEntity(exerciseDto);

                // Set owning side
                exercise.setLesson(lesson);

                if (exerciseDto.getExerciseTestCases() != null && !exerciseDto.getExerciseTestCases().isEmpty()) {
                    for (ExerciseTestCasesDTO testCaseDto : exerciseDto.getExerciseTestCases()) {
                        ExerciseTestCase testCase = exerciseTestCaseMapper.toEntity(testCaseDto);
                        exercise.addTestCase(testCase);
                    }
                }
                lesson.getCodingExercises().add(exercise);
            }
        }

        // Map quizzes
        if (request.getQuizzes() != null && !request.getQuizzes().isEmpty()) {
            for (var dto : request.getQuizzes()) {
                Quiz q = quizExerciseMapper.toEntity(dto);
                q.setLesson(lesson);  // set owning side
                lesson.getQuizzes().add(q);
            }
        }

        courseLessonRepository.save(lesson);
        return courseLessonResponseMapper.toDto(lesson);
    }

    @Override
    @Transactional
    public CourseLessonResponse update(UUID theId, CourseLessonRequest request) {
        CourseLesson existing = courseLessonRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("CourseLesson not found with id = " + theId));
        courseLessonRequestMapper.partialUpdate(existing, request);
        CourseLesson updated = courseLessonRepository.save(existing);
        return courseLessonResponseMapper.toDto(updated);
    }

    @Override
    public UUID delete(UUID theId) {
        courseLessonRepository.deleteById(theId);
        return theId;
    }

    public String generateUniqueSlug(String baseSlug) {
        String slug= baseSlug;
        int counter =1;
        while (courseLessonRepository.existsBySlug(slug)){
            slug = baseSlug + "-" + counter;
            counter++;
        }
        return slug;
    }

    @Override
    public Optional<CourseLesson> findById(UUID id) {
        return courseLessonRepository.findById(id);
    }
}
