package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.custom.request.course.CourseModuleRequest;
import com.truongsonkmhd.unetistudy.dto.custom.request.lesson.LessonRequest;
import com.truongsonkmhd.unetistudy.dto.custom.request.permission.PermissionRequest;
import com.truongsonkmhd.unetistudy.dto.custom.response.course.CourseModuleResponse;
import com.truongsonkmhd.unetistudy.dto.custom.response.lesson.LessonResponse;
import com.truongsonkmhd.unetistudy.dto.custom.response.permission.PermissionResponse;

import java.util.List;
import java.util.UUID;

public interface CourseModuleService {
    List<CourseModuleResponse> getAllModule();


    CourseModuleResponse update(UUID theId,CourseModuleRequest request);

    UUID delete(UUID theId);
}
