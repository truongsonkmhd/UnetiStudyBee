package com.truongsonkmhd.unetistudy.controller;

import com.truongsonkmhd.unetistudy.common.SubmissionVerdict;
import com.truongsonkmhd.unetistudy.context.UserContext;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionResponseDTO;
import com.truongsonkmhd.unetistudy.dto.ContestExerciseAttempt.AttemptInfoDTO;
import com.truongsonkmhd.unetistudy.dto.a_common.ErrorResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.model.*;
import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.model.lesson.CodingSubmission;
import com.truongsonkmhd.unetistudy.model.lesson.ContestExerciseAttempt;
import com.truongsonkmhd.unetistudy.sevice.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

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

    private final CourseLessonService lessonService;

    @PostMapping("/run")
    public ResponseEntity<IResponseMessage>  handleRunCode(@RequestBody JudgeRequestDTO request){
        return  ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(judgeService.runUserCode(request)));
    }
    @PostMapping("/submit")
    public  ResponseEntity<IResponseMessage>  handleSubmitCode(
            @RequestBody JudgeRequestDTO request
    ) {
        // 1) Call judge -> nhận kết quả
        CodingSubmissionResponseDTO submission = judgeService.submitUserCode(request);

        // 2) Ensure IDs (nếu judgeService chưa set)
        submission.setExerciseID(request.getExerciseId());

        // 3) Load entities
     //   User userEntity = userService.findById(submission.getUserID());
        User userEntity = userService.findById(UUID.fromString("6937309f-c954-4ebc-a16c-72b2b82af869"));
        CodingExercise codingExercise = codingExerciseService.getExerciseEntityByID(request.getExerciseId());

        // 4) Build entity để lưu DB (đúng theo entity CodingSubmission của UNETI)
        CodingSubmission codingSubmission = CodingSubmission.builder()
                .exercise(codingExercise)
                .user(userEntity)
                .code(submission.getCode())
                .language(submission.getLanguage() != null ? submission.getLanguage() : request.getLanguage())
                .verdict(submission.getVerdict())
                .passedTestcases(submission.getPassedTestcases() != null ? submission.getPassedTestcases() : 0)
                .totalTestcases(submission.getTotalTestcases() != null ? submission.getTotalTestcases() : 0)
                .runtimeMs(submission.getRuntimeMs())
                .memoryKb(submission.getMemoryKb())
                .score(submission.getScore() != null ? submission.getScore() : 0)
                .build();

        CodingSubmission saved = codingSubmissionService.save(codingSubmission);

        // 5) Trả submissionId + submittedAt về client (nếu cần)
        submission.setSubmittedAt(saved.getSubmittedAt());

        // 6) Contest attempt (fix time type theo entity Attempt của bạn)
        if (codingExerciseService.isExerciseInContestLesson(request.getExerciseId())) {

            AttemptInfoDTO attemptInfo = contestExerciseAttemptService
                    .getAttemptInfoDTOByUserIDAndExerciseID(UserContext.getUserID(), request.getExerciseId(), "coding");

            if (attemptInfo == null) {
                attemptInfo = new AttemptInfoDTO();
                attemptInfo.setAttemptNumber(0);
                attemptInfo.setExerciseType("coding");
                attemptInfo.setLessonID(codingExerciseService.getLessonIDByExerciseID(request.getExerciseId()));
            }

            int currentAttempt = attemptInfo.getAttemptNumber() == null ? 0 : attemptInfo.getAttemptNumber();

            ContestExerciseAttempt exerciseAttempt = new ContestExerciseAttempt();
            exerciseAttempt.setExerciseID(request.getExerciseId());

            CourseLesson lesson = lessonService.findById(attemptInfo.getLessonID())
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));
            exerciseAttempt.setLesson(lesson);

            User user = new User();
            user.setId(UserContext.getUserID());
            exerciseAttempt.setUser(user);

            exerciseAttempt.setSubmittedAt(LocalDateTime.now());

            exerciseAttempt.setExerciseType(attemptInfo.getExerciseType());
            exerciseAttempt.setAttemptNumber(currentAttempt + 1);
            exerciseAttempt.setScore(submission.getScore() != null ? submission.getScore().doubleValue() : 0.0);

            contestExerciseAttemptService.save(exerciseAttempt);
        }
        return  ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(submission));
    }

    @PostMapping("/submitMQ")
    public ResponseEntity<IResponseMessage> handleSubmitCodeMQ(@RequestBody JudgeRequestDTO request) {
        try {
         //   UUID userId = UserContext.getUserID();
            UUID userId = UUID.fromString("6937309f-c954-4ebc-a16c-72b2b82af869");

            log.info("Async submit started: userId={}, exerciseId={}", userId, request.getExerciseId());

            // Validate exercise tồn tại trước khi lưu
            CodingExercise exercise = codingExerciseService.getExerciseEntityByID(request.getExerciseId());
            if (exercise == null) {
                log.error("Exercise not found: {}", request.getExerciseId());
                throw new RuntimeException("Exercise not found: " + request.getExerciseId());
            }

            // Load user
            User userEntity = userService.findById(userId);
            if (userEntity == null) {
                log.error("User not found: {}", userId);
                throw new RuntimeException("User not found: " + userId);
            }

            // Tạo submission với trạng thái PENDING
            CodingSubmission codingSubmission = CodingSubmission.builder()
                    .exercise(exercise)
                    .user(userEntity)
                    .code(request.getSourceCode())
                    .language(request.getLanguage())
                    .verdict(SubmissionVerdict.PENDING)
                    .passedTestcases(0)
                    .totalTestcases(0)
                    .runtimeMs(null)
                    .memoryKb(null)
                    .score(0)
                    .build();

            CodingSubmission saved = codingSubmissionService.save(codingSubmission);

            log.info("Submission saved with PENDING: submissionId={}", saved.getSubmissionId());

            // Publish job vào RabbitMQ để chấm bài
            judgeService.publishSubmitJob(saved, request);

            log.info("Judge job published: submissionId={}", saved.getSubmissionId());

            // Trả response ngay cho client (PENDING)
            CodingSubmissionResponseDTO response = CodingSubmissionResponseDTO.builder()
                    .submissionId(saved.getSubmissionId())
                    .exerciseID(request.getExerciseId())
                    .userID(userId)
                    .code(request.getSourceCode())
                    .language(request.getLanguage())
                    .verdict(SubmissionVerdict.PENDING)
                    .passedTestcases(0)
                    .totalTestcases(0)
                    .runtimeMs(null)
                    .memoryKb(null)
                    .score(0)
                    .submittedAt(saved.getSubmittedAt())
                    .build();

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(SuccessResponseMessage.CreatedSuccess(response));

        } catch (Exception e) {
            log.error("Async submit failed: exerciseId={}", request.getExerciseId(), e);
            throw e;
        }
    }


    @GetMapping("/submission/{submissionId}")
    public ResponseEntity<IResponseMessage> getSubmissionResult(
            @PathVariable UUID submissionId
    ){
        try {
            CodingSubmission submission = codingSubmissionService.getSubmissionById(submissionId);

            if (submission == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorResponseMessage.BadRequest("Submission not found"));
            }

            CodingSubmissionResponseDTO response = CodingSubmissionResponseDTO.builder()
                    .submissionId(submission.getSubmissionId())
                    .exerciseID(submission.getExercise().getExerciseId())
                    .userID(submission.getUser().getId())
                    .code(submission.getCode())
                    .language(submission.getLanguage())
                    .verdict(submission.getVerdict())
                    .passedTestcases(submission.getPassedTestcases())
                    .totalTestcases(submission.getTotalTestcases())
                    .runtimeMs(submission.getRuntimeMs())
                    .memoryKb(submission.getMemoryKb())
                    .score(submission.getScore())
                    .submittedAt(submission.getSubmittedAt())
                    .build();

            return ResponseEntity.ok()
                    .body(SuccessResponseMessage.CreatedSuccess(response));

        } catch (Exception e) {
            log.error("Failed to get submission: submissionId={}", submissionId, e);
            throw e;
        }
    }

//    @GetMapping("/submissions")
//    public ResponseEntity<IResponseMessage> getUserSubmissions(
//            @RequestParam UUID exerciseId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        try {
//            UUID userId = UserContext.getUserID();
//
//            // Giả sử bạn có method này trong service
//            var submissions = codingSubmissionService.getUserSubmissions(
//                    userId, exerciseId, page, size
//            );
//
//            return ResponseEntity.ok()
//                    .body(SuccessResponseMessage.CreatedSuccess(submissions));
//
//        } catch (Exception e) {
//            log.error("Failed to get user submissions: exerciseId={}", exerciseId, e);
//            throw e;
//        }
//    }

}
