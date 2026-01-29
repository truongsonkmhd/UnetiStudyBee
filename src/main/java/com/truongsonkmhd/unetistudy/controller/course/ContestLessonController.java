package com.truongsonkmhd.unetistudy.controller.course;

import com.truongsonkmhd.unetistudy.common.StatusContest;
import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.ResponseMessage;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonRequestDTO;
import com.truongsonkmhd.unetistudy.service.ContestLessonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/contest-lesson")
@Slf4j(topic = "LESSON-CONTROLLER")
@Tag(name = "lesson Controller")
@RequiredArgsConstructor
public class ContestLessonController {

    private final ContestLessonService contestLessonService;

    @PostMapping("/add")
    public ResponseEntity<IResponseMessage> addContestLesson(@RequestBody ContestLessonRequestDTO request) {
        return ResponseEntity.ok()
                .body(ResponseMessage.CreatedSuccess(contestLessonService.addContestLesson(request)));
    }

    @GetMapping("/search")
    public ResponseEntity<IResponseMessage> searchAdvanceContest(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) StatusContest statusContest) {

        return ResponseEntity.ok().body(ResponseMessage.LoadedSuccess(
                contestLessonService.searchContestLessons(page, size, q, statusContest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IResponseMessage> getById(@PathVariable UUID id) {
        return ResponseEntity.ok()
                .body(ResponseMessage.LoadedSuccess(contestLessonService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IResponseMessage> updateContestLesson(
            @PathVariable UUID id,
            @RequestBody ContestLessonRequestDTO request) {
        return ResponseEntity.ok()
                .body(ResponseMessage.UpdatedSuccess(contestLessonService.updateContestLesson(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IResponseMessage> deleteContestLesson(@PathVariable UUID id) {
        contestLessonService.deleteContestLesson(id);
        return ResponseEntity.ok()
                .body(ResponseMessage.DeletedSuccess("Deleted contest lesson successfully"));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<IResponseMessage> publishContestLesson(@PathVariable UUID id) {
        contestLessonService.publishContestLesson(id);
        return ResponseEntity.ok()
                .body(ResponseMessage.ProcessSuccess("Published contest lesson successfully"));
    }

    @PostMapping("/{id}/archive")
    public ResponseEntity<IResponseMessage> archiveContestLesson(@PathVariable UUID id) {
        contestLessonService.archiveContestLesson(id);
        return ResponseEntity.ok()
                .body(ResponseMessage.ProcessSuccess("Archived contest lesson successfully"));
    }

}
