package com.truongsonkmhd.unetistudy.controller.course;

import com.truongsonkmhd.unetistudy.dto.course_dto.CourseShowRequest;
import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.ResponseMessage;
import com.truongsonkmhd.unetistudy.service.CourseTreeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/courses")
@Slf4j(topic = "COURSE-CONTROLLER")
@Tag(name = "course Controller")
@RequiredArgsConstructor
public class CourseController {

    private final CourseTreeService courseTreeService;

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<IResponseMessage> addCourse(@RequestBody CourseShowRequest theCourse) {
        return ResponseEntity.ok().body(ResponseMessage.CreatedSuccess(courseTreeService.save(theCourse)));
    }

    @PutMapping("/upd/{courseId}")
    @Transactional
    ResponseEntity<IResponseMessage> updateUser(@PathVariable UUID courseId,
            @RequestBody CourseShowRequest theCourseRequest) {
        log.info("Updating user: {}", theCourseRequest);
        return ResponseEntity.ok()
                .body(ResponseMessage.UpdatedSuccess(courseTreeService.update(courseId, theCourseRequest)));
    }

    @DeleteMapping("/delete/{courseID}")
    @Transactional
    public ResponseEntity<IResponseMessage> deleteById(@PathVariable UUID courseID) {
        return ResponseEntity.ok().body(ResponseMessage.DeletedSuccess(courseTreeService.deleteById(courseID)));
    }

    @GetMapping("/getCourseById/{courseID}")
    @Transactional
    public ResponseEntity<IResponseMessage> getCourseByID(@PathVariable UUID courseID) {
        return ResponseEntity.ok().body(ResponseMessage.LoadedSuccess(courseTreeService.findById(courseID)));
    }

    @GetMapping("/{slug}/tree")
    public ResponseEntity<IResponseMessage> getCourseTreeForStudent(@PathVariable String slug) {
        return ResponseEntity.ok(
                ResponseMessage.LoadedSuccess(
                        courseTreeService.getCourseTreeDetailPublished(slug)));
    }

    @GetMapping("/getCourseBySlug/{theSlug}")
    @Transactional
    public ResponseEntity<IResponseMessage> getCourseByID(@PathVariable String theSlug) {
        return ResponseEntity.ok()
                .body(ResponseMessage.LoadedSuccess(courseTreeService.getCourseModuleByCourseSlug(theSlug)));
    }

}
