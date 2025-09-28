package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseModuleRequest;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseModuleResponse;

import java.util.List;
import java.util.UUID;

public interface CourseModuleService {
    List<CourseModuleResponse> getAllModule();


    CourseModuleResponse update(UUID theId,CourseModuleRequest request);

    UUID delete(UUID theId);
}
