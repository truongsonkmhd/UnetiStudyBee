package com.truongsonkmhd.unetistudy.controller;

import com.truongsonkmhd.unetistudy.dto.common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.dto.custom.request.lesson.LessonRequest;
import com.truongsonkmhd.unetistudy.dto.custom.request.permission.PermissionRequest;
import com.truongsonkmhd.unetistudy.sevice.CourseLessonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/course-lesson")
@Slf4j(topic = "LESSON-CONTROLLER")
@Tag(name = "lesson Controller")
@RequiredArgsConstructor
public class CourseLessonController {

    private final CourseLessonService lessonService;

    @GetMapping("/getAll")
    @Transactional
    ResponseEntity<IResponseMessage> getList(){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(lessonService.getLessonAll()));
    }

    @GetMapping("/getLessonByModuleId/{moduleId}")
    @Transactional
    ResponseEntity<IResponseMessage> getLessonByModuleId(@PathVariable UUID moduleId){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(lessonService.getLessonByModuleId(moduleId)));
    }

    @GetMapping("/getLessonByModuleIDAndSlug/{slug}/{moduleId}")
    @Transactional
    ResponseEntity<IResponseMessage> getLessonByModuleId(@PathVariable UUID moduleId , @PathVariable String slug){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(lessonService.getLessonByModuleIDAndSlug(moduleId,slug)));
    }

    @GetMapping("/getLessonByModuleIDAndSlug/{moduleId}")
    @Transactional
    ResponseEntity<IResponseMessage> getCodingContest(@PathVariable UUID moduleId){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(lessonService.getCodingContest(moduleId)));
    }

    @GetMapping("/getMultipleChoiceContest/{moduleId}")
    @Transactional
    ResponseEntity<IResponseMessage> getMultipleChoiceContest(@PathVariable UUID moduleId){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(lessonService.getMultipleChoiceContest(moduleId)));
    }

    @PostMapping("/add")
    ResponseEntity<IResponseMessage> addLesson(@RequestBody LessonRequest request) {
        return ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(lessonService.addLesson(request)));
    }


    @DeleteMapping("/delete{id}")
    ResponseEntity<IResponseMessage> delete(@PathVariable UUID id) {
        return ResponseEntity.ok().body(SuccessResponseMessage.DeletedSuccess(lessonService.delete(id)));
    }

    @PutMapping("/update/{id}")
    ResponseEntity<IResponseMessage> update(@RequestBody LessonRequest request , @PathVariable UUID id){
        return ResponseEntity.ok().body(SuccessResponseMessage.UpdatedSuccess(lessonService.update(id,request)));
    }


}
