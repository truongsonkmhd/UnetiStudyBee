package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.custom.response.course.CourseModuleResponse;
import com.truongsonkmhd.unetistudy.dto.custom.request.course.CourseShowRequest;
import com.truongsonkmhd.unetistudy.dto.custom.response.course.CourseShowResponse;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    CourseShowResponse findById(UUID theId);
    CourseShowResponse save(CourseShowRequest theCourseRequest);
    UUID deleteById(UUID theId);
    List<CourseShowResponse> getCourseShowDTO();
    List<CourseModuleResponse> getCourseModuleByCourseSlug(String theSlug);
    String generateUniqueSlug(String baseSlug);
}
