package com.truongsonkmhd.unetistudy.controller.course;

import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.sevice.CourseTreeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/courses")
@Slf4j(topic = "COURSE-CONTROLLER")
@Tag(name = "course Controller")
@RequiredArgsConstructor
public class AdminCourseController {


    private final CourseTreeService courseTreeService;

    @GetMapping("/{slug}/tree")
    public ResponseEntity<IResponseMessage> getCourseTreeForAdmin(@PathVariable String slug) {
        return ResponseEntity.ok(
                SuccessResponseMessage.LoadedSuccess(
                        courseTreeService.getCourseTreeAdminDetail(slug)
                )
        );
    }
}
