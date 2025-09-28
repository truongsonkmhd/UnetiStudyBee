package com.truongsonkmhd.unetistudy.controller;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseShowRequest;
import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.sevice.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/course")
@Slf4j(topic = "COURSE-CONTROLLER")
@Tag(name = "course Controller")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<IResponseMessage> addCourse(@RequestBody CourseShowRequest theCourse){
        return ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(courseService.save(theCourse)));
    }

    @GetMapping("/getAllCourse")
    @Transactional
    public ResponseEntity<IResponseMessage> getList(){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(courseService.getCourseShowDTO()));
    }

    @DeleteMapping("/delete/{courseID}")
    @Transactional
    public ResponseEntity<IResponseMessage> deleteById(@PathVariable UUID courseID){
        return ResponseEntity.ok().body(SuccessResponseMessage.DeletedSuccess(courseService.deleteById(courseID)));
    }

    @GetMapping("/getCourseById/{courseID}")
    @Transactional
    public ResponseEntity<IResponseMessage> getCourseByID(@PathVariable UUID courseID){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(courseService.findById(courseID)));
    }

    @GetMapping("/getCourseBySlug/{theSlug}")
    @Transactional
    public ResponseEntity<IResponseMessage> getCourseByID(@PathVariable String theSlug){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(courseService.getCourseModuleByCourseSlug(theSlug)));
    }

}
