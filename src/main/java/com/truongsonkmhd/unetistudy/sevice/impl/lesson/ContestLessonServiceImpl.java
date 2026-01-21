package com.truongsonkmhd.unetistudy.sevice.impl.lesson;

import com.truongsonkmhd.unetistudy.common.StatusContest;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonRequestDTO;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonResponseDTO;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.ContestLesson;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.ExerciseTestCase;
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
                .title(request.getTitle())
                .description(request.getDescription())
                .defaultDurationMinutes(request.getDefaultDurationMinutes())
                .totalPoints(request.getTotalPoints())
                .defaultMaxAttempts(request.getDefaultMaxAttempts())
                .passingScore(request.getPassingScore())
                .showLeaderboardDefault(request.getShowLeaderboardDefault())
                .instructions(request.getInstructions())
                .status(StatusContest.DRAFT)
                .build();

        addCodingExercisesToContest(request.getExerciseTemplateIds(),contestLesson);

        // save
        contestLessonRepository.save(contestLesson);

        return ContestLessonResponseDTO.builder()
                .title(contestLesson.getTitle())
                .description(contestLesson.getDescription())
                .defaultDurationMinutes(contestLesson.getDefaultDurationMinutes())
                .totalPoints(contestLesson.getTotalPoints())
                .defaultMaxAttempts(contestLesson.getDefaultMaxAttempts())
                .passingScore(contestLesson.getPassingScore())
                .showLeaderboardDefault(contestLesson.getShowLeaderboardDefault())
                .instructions(contestLesson.getInstructions())
                .status(contestLesson.getStatus())
                .build();
    }

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