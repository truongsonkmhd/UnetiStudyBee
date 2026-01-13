package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseModuleRequest;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.course.CourseModule;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseModuleRequestMapper extends EntityMapper<CourseModuleRequest, CourseModule> {

    @Override
    @Mapping(target = "moduleId", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CourseModule toEntity(CourseModuleRequest dto);

    @Override
    CourseModuleRequest toDto(CourseModule entity);

    @Override
    List<CourseModule> toEntity(List<CourseModuleRequest> dtoList);

    @Override
    List<CourseModuleRequest> toDto(List<CourseModule> entityList);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget CourseModule entity, CourseModuleRequest dto);
}
