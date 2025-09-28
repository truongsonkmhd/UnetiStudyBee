package com.truongsonkmhd.unetistudy.controller;

import com.truongsonkmhd.unetistudy.context.UserContext;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionResponseDTO;
import com.truongsonkmhd.unetistudy.dto.ContestExerciseAttempt.AttemptInfoDTO;
import com.truongsonkmhd.unetistudy.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import com.truongsonkmhd.unetistudy.mapper.coding_submission.CodingSubmissionMapper;
import com.truongsonkmhd.unetistudy.model.*;
import com.truongsonkmhd.unetistudy.repository.ExerciseTestCaseRepository;
import com.truongsonkmhd.unetistudy.sevice.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;

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

    @PostMapping("/run")
    public JudgeRunResponseDTO handleRunCode(@RequestBody JudgeRequestDTO request, @PathVariable String language){
        return  judgeService.runUserCode(request,language);
    }

    @PostMapping("submit")
    public CodingSubmissionResponseDTO handleSubmitCode(@RequestBody JudgeRequestDTO request, @PathVariable String language){
        // lấy ra submission để lưu vào DB và trả ra cho client
        CodingSubmissionResponseDTO submission = judgeService.submitUserCode(request,language);
        submission.setExerciseID(request.getExerciseID());

        // Lưu Submission vào DB
        User userEntity = userService.findById(submission.getUserID());
        CodingExercise codingExercise = codingExerciseService.getExerciseEntityByID(request.getExerciseID());

        CodingSubmission codingSubmission = CodingSubmission.builder()
                .code(submission.getCode())
                .language(submission.getLanguage())
                .status(submission.getStatus())
                .testCasesPassed(submission.getTestCasesPassed())
                .totalTestCases(submission.getTotalTestCases())
                .score(submission.getScore())
                .exercise(codingExercise)
                .user(userEntity)
                .executionTime(1)
                .memoryUsed(10)
                .submittedAt(LocalDateTime.now())
                .build();

        codingSubmissionService.save(codingSubmission);


        // Kiểm tra nếu là contest thì cho vào attempt
        if(codingExerciseService.isExerciseInContestLesson(request.getExerciseID())){
            // CHECK SỐ LẦN NỘP BÀI VÀ LƯU VÀO ContestAttempt
            AttemptInfoDTO attemptInfo = contestExerciseAttemptService.getAttemptInfoDTOByUserIDAndExerciseID(UserContext.getUserID(), request.getExerciseID(), "coding");

            if(attemptInfo != null && attemptInfo.getAttemptNumber() != null && attemptInfo.getAttemptNumber() >0){
                System.out.println("Lần làm bài thứ " + (attemptInfo.getAttemptNumber() + 1));
            }

            if (attemptInfo == null){
                attemptInfo = new AttemptInfoDTO();
                attemptInfo.setAttemptNumber(0);
                attemptInfo.setExerciseType("coding");
                attemptInfo.setLessonID(codingExerciseService.getLessonIDByExerciseID(request.getExerciseID()));
            }

            ContestExerciseAttempt exerciseAttempt = new ContestExerciseAttempt();
            exerciseAttempt.setExerciseID(request.getExerciseID());
            CourseLesson lesson = lessonService.findById(attemptInfo.getLessonID()).orElseThrow(() -> new RuntimeException("Lesson not found"));
            exerciseAttempt.setLesson(lesson);
            User user = new User();
            user.setId(UserContext.getUserID());
            exerciseAttempt.setUser(user);
            exerciseAttempt.setSubmittedAt(LocalDateTime.now());
            exerciseAttempt.setExerciseType(attemptInfo.getExerciseType());
            int currentAttempt = attemptInfo.getAttemptNumber() == null ? 0 : attemptInfo.getAttemptNumber();
            exerciseAttempt.setAttemptNumber(currentAttempt + 1);
            Number score = submission.getScore();
            exerciseAttempt.setScore(score != null ? score.doubleValue() : 0.0);

            contestExerciseAttemptService.save(exerciseAttempt);
        }

        return submission;
    }
}
