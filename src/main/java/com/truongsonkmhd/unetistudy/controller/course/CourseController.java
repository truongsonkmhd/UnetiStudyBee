package com.truongsonkmhd.unetistudy.controller.course;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseShowRequest;
import com.truongsonkmhd.unetistudy.dto.UserDTO.UserUpdateRequest;
import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.sevice.CourseTreeService;
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
    public ResponseEntity<IResponseMessage> addCourse(@RequestBody CourseShowRequest theCourse){
        return ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(courseTreeService.save(theCourse)));
    }

    @PutMapping("/upd/{courseId}")
    @Transactional
    ResponseEntity<IResponseMessage> updateUser(@PathVariable UUID courseId, @RequestBody CourseShowRequest theCourseRequest) {
        log.info("Updating user: {}", theCourseRequest);
        return ResponseEntity.ok().body(SuccessResponseMessage.UpdatedSuccess(courseTreeService.update(courseId,theCourseRequest)));
    }

    @DeleteMapping("/delete/{courseID}")
    @Transactional
    public ResponseEntity<IResponseMessage> deleteById(@PathVariable UUID courseID){
        return ResponseEntity.ok().body(SuccessResponseMessage.DeletedSuccess(courseTreeService.deleteById(courseID)));
    }

    @GetMapping("/getCourseById/{courseID}")
    @Transactional
    public ResponseEntity<IResponseMessage> getCourseByID(@PathVariable UUID courseID){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(courseTreeService.findById(courseID)));
    }
    @GetMapping("/{slug}/tree")
    public ResponseEntity<IResponseMessage> getCourseTreeForStudent(@PathVariable String slug) {
        return ResponseEntity.ok(
                SuccessResponseMessage.LoadedSuccess(
                        courseTreeService.getCourseTreeStudentDetailPublished(slug)
                )
        );
    }
    @GetMapping("/getCourseBySlug/{theSlug}")
    @Transactional
    public ResponseEntity<IResponseMessage> getCourseByID(@PathVariable String theSlug){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(courseTreeService.getCourseModuleByCourseSlug(theSlug)));
    }

}
