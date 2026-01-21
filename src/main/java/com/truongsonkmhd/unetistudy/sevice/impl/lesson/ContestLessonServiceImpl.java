package com.truongsonkmhd.unetistudy.sevice.impl.lesson;

import com.truongsonkmhd.unetistudy.common.StatusContest;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonRequestDTO;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonResponseDTO;
import com.truongsonkmhd.unetistudy.dto.lesson_dto.CourseLessonRequest;
import com.truongsonkmhd.unetistudy.model.lesson.solid.course_lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.model.lesson.solid.course_lesson.ContestLesson;
import com.truongsonkmhd.unetistudy.model.lesson.solid.course_lesson.ExerciseTestCase;
import com.truongsonkmhd.unetistudy.model.lesson.template.CodingExerciseTemplate;
import com.truongsonkmhd.unetistudy.repository.coding.CodingExerciseTemplateRepository;
import com.truongsonkmhd.unetistudy.repository.course.ContestLessonRepository;
import com.truongsonkmhd.unetistudy.sevice.ContestLessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContestLessonServiceImpl implements ContestLessonService {

    private final ContestLessonRepository contestLessonRepository;
    
    private final CodingExerciseTemplateRepository templateRepository;


    @Override
    public ContestLessonResponseDTO addContestLesson(ContestLessonRequestDTO request) {

        ContestLesson contestLesson = ContestLesson.builder()
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .maxAttempts(request.getMaxAttempts())
                .isActive(request.getIsActive())
                .showLeaderboard(request.getShowLeaderboard())
                .build();

        addCodingExercisesToContest(request.getExerciseTemplateIds(),contestLesson);

        // save
        contestLessonRepository.save(contestLesson);

        return ContestLessonResponseDTO.builder()
                .contestLessonId(contestLesson.getContestLessonId())
                .startTime(contestLesson.getStartTime())
                .endTime(contestLesson.getEndTime())
                .totalPoints(contestLesson.getTotalPoints())
                .maxAttempts(contestLesson.getMaxAttempts())
                .isActive(contestLesson.getIsActive())
                .showLeaderboard(contestLesson.getShowLeaderboard())
                .status(StatusContest.ONGOING)
                .build();
    }

    @Override
    public void addCodingExercisesToContest(List<UUID> exerciseTemplateIds, ContestLesson contestLesson) {

        if (exerciseTemplateIds!= null && !exerciseTemplateIds.isEmpty()) {
            List<CodingExerciseTemplate> templates = templateRepository
                    .findAllById(exerciseTemplateIds);

            for (CodingExerciseTemplate template : templates) {
                CodingExercise contestExercise = template.toContestExercise();

                for (var templateTestCase : template.getTestCases()) {
                    ExerciseTestCase testCase = ExerciseTestCase.builder()
                            .input(templateTestCase.getInput())
                            .expectedOutput(templateTestCase.getExpectedOutput())
                            .isSample(templateTestCase.getIsSample())
                            .explanation(templateTestCase.getExplanation())
                            .orderIndex(templateTestCase.getOrderIndex())
                            .build();
                    contestExercise.addTestCase(testCase);
                }

                // Add to contest
                contestLesson.addCodingExercise(contestExercise);

                // Track usage
                template.incrementUsageCount();
            }
        }
    }

}