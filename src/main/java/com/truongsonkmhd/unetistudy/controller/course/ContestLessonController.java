package com.truongsonkmhd.unetistudy.controller.course;

import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonRequestDTO;
import com.truongsonkmhd.unetistudy.sevice.ContestLessonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course-contest-lesson")
@Slf4j(topic = "LESSON-CONTROLLER")
@Tag(name = "lesson Controller")
@RequiredArgsConstructor
public class ContestLessonController {

    private final ContestLessonService contestLessonService;

    @PostMapping("/add")
    ResponseEntity<IResponseMessage> addContestLesson(@RequestBody ContestLessonRequestDTO request) {
        return ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(contestLessonService.addContestLesson(request)));
    }




}
