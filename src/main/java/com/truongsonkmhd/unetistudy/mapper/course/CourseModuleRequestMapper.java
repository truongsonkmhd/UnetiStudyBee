package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.custom.request.course.CourseModuleRequest;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.CourseModule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseModuleRequestMapper extends EntityMapper<CourseModuleRequest, CourseModule> {
}
