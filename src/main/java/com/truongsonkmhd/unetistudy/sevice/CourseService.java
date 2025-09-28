package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseModuleResponse;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseShowRequest;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseShowResponse;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    CourseShowResponse findById(UUID theId);
    CourseShowResponse save(CourseShowRequest theCourseRequest);
    UUID deleteById(UUID theId);
    List<CourseShowResponse> getCourseShowDTO();
    List<CourseModuleResponse> getCourseModuleByCourseSlug(String theSlug);
}
