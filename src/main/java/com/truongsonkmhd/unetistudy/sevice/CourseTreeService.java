package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseModuleResponse;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseShowRequest;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseTreeResponse;

import java.util.List;
import java.util.UUID;

public interface CourseTreeService {
    CourseTreeResponse findById(UUID theId);
    CourseTreeResponse save(CourseShowRequest theCourseRequest);
    CourseTreeResponse update(UUID courseId, CourseShowRequest req);
    CourseTreeResponse getCourseTreeDetailPublished(String slug);

    UUID deleteById(UUID theId);
    List<CourseModuleResponse> getCourseModuleByCourseSlug(String theSlug);
}
