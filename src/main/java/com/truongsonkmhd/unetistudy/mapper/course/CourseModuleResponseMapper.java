package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseModuleResponse;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.course.CourseModule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseModuleResponseMapper extends EntityMapper<CourseModuleResponse, CourseModule> {

    @Override
    @Mapping(source = "moduleId", target = "moduleId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "orderIndex", target = "orderIndex")
    @Mapping(source = "duration", target = "duration")
    @Mapping(source = "isPublished", target = "isPublished")
    @Mapping(source = "lessons", target = "lessons")
    CourseModuleResponse toDto(CourseModule entity);

    @Override
    @Mapping(target = "course", ignore = true)
    CourseModule toEntity(CourseModuleResponse dto);


}
