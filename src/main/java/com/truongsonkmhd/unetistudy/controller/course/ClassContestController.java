package com.truongsonkmhd.unetistudy.controller.course;

import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.ResponseMessage;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.CreateClassContestRequest;
import com.truongsonkmhd.unetistudy.service.ClassContestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/class-contests")
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
                                ResponseMessage.CreatedSuccess(
                                                classContestService.createClassContest(request)));
        }

        // ================= GET ALL CONTESTS OF CLASS =================

        @GetMapping("/{classId}")
        public ResponseEntity<IResponseMessage> getClassContests(
                        @PathVariable UUID classId) {

                return ResponseEntity.ok(
                                ResponseMessage.LoadedSuccess(
                                                classContestService.getClassContests(classId)));
        }

        // ================= GET ONGOING =================

        @GetMapping("/{classId}/ongoing")
        public ResponseEntity<IResponseMessage> getOngoingContests(
                        @PathVariable UUID classId) {

                return ResponseEntity.ok(
                                ResponseMessage.LoadedSuccess(
                                                classContestService.getOngoingContests(classId)));
        }

        // ================= GET UPCOMING =================

        @GetMapping("/{classId}/upcoming")
        public ResponseEntity<IResponseMessage> getUpcomingContests(
                        @PathVariable UUID classId) {

                return ResponseEntity.ok(
                                ResponseMessage.LoadedSuccess(
                                                classContestService.getUpcomingContests(classId)));
        }

        // ================= CANCEL =================

        @PutMapping("/{classContestId}/cancel")
        public ResponseEntity<IResponseMessage> cancelClassContest(
                        @PathVariable UUID classContestId) {

                return ResponseEntity.ok(
                                ResponseMessage.UpdatedSuccess(
                                                classContestService.cancelClassContest(classContestId)));
        }

        // ================= RESCHEDULE =================

        @PutMapping("/{classContestId}/reschedule")
        public ResponseEntity<IResponseMessage> rescheduleClassContest(
                        @PathVariable UUID classContestId,
                        @RequestParam Instant newStartTime,
                        @RequestParam Instant newEndTime) {

                return ResponseEntity.ok(
                                ResponseMessage.UpdatedSuccess(
                                                classContestService.rescheduleClassContest(
                                                                classContestId, newStartTime, newEndTime)));
        }

        @PutMapping("/{classId}/contest-status")
        public ResponseEntity<IResponseMessage> updateContestStatuses(
                        @PathVariable UUID classId) {

                return ResponseEntity.ok(
                                ResponseMessage.UpdatedSuccess(
                                                classContestService.updateContestStatuses(
                                                                classId)));
        }
}
