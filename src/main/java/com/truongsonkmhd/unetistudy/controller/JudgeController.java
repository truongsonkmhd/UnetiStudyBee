package com.truongsonkmhd.unetistudy.controller;

import com.truongsonkmhd.unetistudy.context.UserContext;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionResponseDTO;
import com.truongsonkmhd.unetistudy.dto.ContestExerciseAttempt.AttemptInfoDTO;
import com.truongsonkmhd.unetistudy.model.*;
import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.model.lesson.CodingSubmission;
import com.truongsonkmhd.unetistudy.model.lesson.ContestExerciseAttempt;
import com.truongsonkmhd.unetistudy.sevice.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/judge")
@Slf4j(topic = "JUDGE-CONTROLLER")
@Tag(name = "judge Controller")
@RequiredArgsConstructor
public class JudgeController {

    private final JudgeService judgeService;

    private final UserService userService;

    private final CodingExerciseService codingExerciseService;

    private final CodingSubmissionService codingSubmissionService;

    private final ContestExerciseAttemptService contestExerciseAttemptService;

    private final LessonService lessonService;

    @PostMapping("/run/{language}")
    public JudgeRunResponseDTO handleRunCode(@RequestBody JudgeRequestDTO request, @PathVariable String language){
        return  judgeService.runUserCode(request,language);
    }
    @PostMapping("/submit")
    public CodingSubmissionResponseDTO handleSubmitCode(
            @RequestBody JudgeRequestDTO request,
            @RequestParam String language
    ) {
        // 1) Call judge -> nhận kết quả
        CodingSubmissionResponseDTO submission = judgeService.submitUserCode(request, language);

        // 2) Ensure IDs (nếu judgeService chưa set)
        submission.setExerciseID(request.getExerciseID());
        submission.setUserID(UserContext.getUserID());

        // 3) Load entities
        User userEntity = userService.findById(submission.getUserID());
        CodingExercise codingExercise = codingExerciseService.getExerciseEntityByID(request.getExerciseID());

        // 4) Build entity để lưu DB (đúng theo entity CodingSubmission của UNETI)
        CodingSubmission codingSubmission = CodingSubmission.builder()
                .exercise(codingExercise)
                .user(userEntity)
                .code(submission.getCode())
                .language(submission.getLanguage() != null ? submission.getLanguage() : language)
                .verdict(submission.getVerdict())
                .passedTestcases(submission.getPassedTestcases() != null ? submission.getPassedTestcases() : 0)
                .totalTestcases(submission.getTotalTestcases() != null ? submission.getTotalTestcases() : 0)
                .runtimeMs(submission.getRuntimeMs())
                .memoryKb(submission.getMemoryKb())
                .score(submission.getScore() != null ? submission.getScore() : 0)
                // Không set submittedAt ở đây nếu entity dùng @CreationTimestamp (Instant)
                .build();

        CodingSubmission saved = codingSubmissionService.save(codingSubmission);

        // 5) Trả submissionId + submittedAt về client (nếu cần)
        submission.setSubmittedAt(saved.getSubmittedAt());

        // 6) Contest attempt (fix time type theo entity Attempt của bạn)
        if (codingExerciseService.isExerciseInContestLesson(request.getExerciseID())) {

            AttemptInfoDTO attemptInfo = contestExerciseAttemptService
                    .getAttemptInfoDTOByUserIDAndExerciseID(UserContext.getUserID(), request.getExerciseID(), "coding");

            if (attemptInfo == null) {
                attemptInfo = new AttemptInfoDTO();
                attemptInfo.setAttemptNumber(0);
                attemptInfo.setExerciseType("coding");
                attemptInfo.setLessonID(codingExerciseService.getLessonIDByExerciseID(request.getExerciseID()));
            }

            int currentAttempt = attemptInfo.getAttemptNumber() == null ? 0 : attemptInfo.getAttemptNumber();

            ContestExerciseAttempt exerciseAttempt = new ContestExerciseAttempt();
            exerciseAttempt.setExerciseID(request.getExerciseID());

            CourseLesson lesson = lessonService.findById(attemptInfo.getLessonID())
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));
            exerciseAttempt.setLesson(lesson);

            User user = new User();
            user.setId(UserContext.getUserID());
            exerciseAttempt.setUser(user);

            // Nếu ContestExerciseAttempt.submittedAt là LocalDateTime thì giữ LocalDateTime.now()
            // Nếu là Instant thì dùng Instant.now()
            exerciseAttempt.setSubmittedAt(LocalDateTime.now());

            exerciseAttempt.setExerciseType(attemptInfo.getExerciseType());
            exerciseAttempt.setAttemptNumber(currentAttempt + 1);
            exerciseAttempt.setScore(submission.getScore() != null ? submission.getScore().doubleValue() : 0.0);

            contestExerciseAttemptService.save(exerciseAttempt);
        }

        return submission;
    }

}
