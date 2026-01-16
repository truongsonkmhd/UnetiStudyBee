package com.truongsonkmhd.unetistudy.controller.course;

import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.sevice.CourseCatalogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course_catalog")
@Slf4j(topic = "COURSE-CONTROLLER")
@Tag(name = "course Controller")
@RequiredArgsConstructor
public class CourseCatalogController {

    private final CourseCatalogService courseCatalogService;


    @GetMapping("/published")
    @Transactional
    public ResponseEntity<IResponseMessage> getPublished(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "12") int size,
                                                          @RequestParam(required = false) String q){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(courseCatalogService.getPublishedCourses(page, size, q)));
    }

    @GetMapping("/published/scroll")
    @Transactional
    public ResponseEntity<IResponseMessage> scrollPublished(@RequestParam(required = false) String cursor,
                                                            @RequestParam(defaultValue = "12") int size,
                                                            @RequestParam(required = false) String q){
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(courseCatalogService.getPublishedCoursesCursor(cursor, size, q)));
    }

}
