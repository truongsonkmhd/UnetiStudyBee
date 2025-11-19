package com.truongsonkmhd.unetistudy.controller;

import com.truongsonkmhd.unetistudy.context.UserContext;
import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.sevice.LessonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/management")
@Slf4j(topic = "MANAGEMENT-CONTROLLER")
@Tag(name = "management Controller")
@RequiredArgsConstructor
public class ManagementController {

    private final LessonService lessonService;

    @GetMapping("/contest")
    ResponseEntity<IResponseMessage> showCourse() {
        UUID moduleID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(lessonService.getContestManagementShowDTO(moduleID, UserContext.getUsername())));
    }

    @GetMapping("/contest/edit/{lessonSlug}")
    ResponseEntity<IResponseMessage> showCourse(@PathVariable("lessonSlug") String theSlug) {
        UUID moduleID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(lessonService.getEditLessonDTO(moduleID, theSlug)));
    }


}
