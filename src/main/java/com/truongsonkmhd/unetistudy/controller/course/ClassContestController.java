package com.truongsonkmhd.unetistudy.controller.course;

import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.CreateClassContestRequest;
import com.truongsonkmhd.unetistudy.sevice.ClassContestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/class-contests")
@Slf4j(topic = "CLASS-CONTEST-CONTROLLER")
@Tag(name = "Class Contest Controller")
@RequiredArgsConstructor
public class ClassContestController {

    private final ClassContestService classContestService;

    // ================= CREATE =================

    @PostMapping
    public ResponseEntity<IResponseMessage> createClassContest(
            @RequestBody CreateClassContestRequest request) {

        return ResponseEntity.ok(
                SuccessResponseMessage.CreatedSuccess(
                        classContestService.createClassContest(request)
                )
        );
    }

    // ================= GET ALL CONTESTS OF CLASS =================

    @GetMapping("/class/{classId}")
    public ResponseEntity<IResponseMessage> getClassContests(
            @PathVariable UUID classId) {

        return ResponseEntity.ok(
                SuccessResponseMessage.LoadedSuccess(
                        classContestService.getClassContests(classId)
                )
        );
    }

    // ================= GET ONGOING =================

    @GetMapping("/class/{classId}/ongoing")
    public ResponseEntity<IResponseMessage> getOngoingContests(
            @PathVariable UUID classId) {

        return ResponseEntity.ok(
                SuccessResponseMessage.LoadedSuccess(
                        classContestService.getOngoingContests(classId)
                )
        );
    }

    // ================= GET UPCOMING =================

    @GetMapping("/class/{classId}/upcoming")
    public ResponseEntity<IResponseMessage> getUpcomingContests(
            @PathVariable UUID classId) {

        return ResponseEntity.ok(
                SuccessResponseMessage.LoadedSuccess(
                        classContestService.getUpcomingContests(classId)
                )
        );
    }

    // ================= CANCEL =================

    @PutMapping("/{classContestId}/cancel")
    public ResponseEntity<IResponseMessage> cancelClassContest(
            @PathVariable UUID classContestId) {

        return ResponseEntity.ok(
                SuccessResponseMessage.UpdatedSuccess(
                        classContestService.cancelClassContest(classContestId)
                )
        );
    }

    // ================= RESCHEDULE =================

    @PutMapping("/{classContestId}/reschedule")
    public ResponseEntity<IResponseMessage> rescheduleClassContest(
            @PathVariable UUID classContestId,
            @RequestParam Instant newStartTime,
            @RequestParam Instant newEndTime) {

        return ResponseEntity.ok(
                SuccessResponseMessage.UpdatedSuccess(
                        classContestService.rescheduleClassContest(
                                classContestId, newStartTime, newEndTime
                        )
                )
        );
    }

    @PutMapping("/{classId}/contest-status")
    public ResponseEntity<IResponseMessage> updateContestStatuses(
            @PathVariable UUID classId) {

        return ResponseEntity.ok(
                SuccessResponseMessage.UpdatedSuccess(
                        classContestService.updateContestStatuses(
                                classId
                        )
                )
        );
    }
}
