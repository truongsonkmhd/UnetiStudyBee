package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.common.CourseStatus;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseTreeResponse;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.course.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {CourseModuleResponseMapper.class} )
public interface CourseResponseMapper extends EntityMapper<CourseTreeResponse, Course> {

    @Override
    @Mapping(target = "modules", source = "modules")
    CourseTreeResponse toDto(Course entity);
    @Override
    @Mapping(target = "courseId", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "shortDescription", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "subCategory", ignore = true)
    @Mapping(target = "capacity", ignore = true)
    @Mapping(target = "enrolledCount", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "videoUrl", ignore = true)
    @Mapping(target = "requirements", ignore = true)
    @Mapping(target = "objectives", ignore = true)
    @Mapping(target = "syllabus", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Course toEntity(CourseTreeResponse dto);
}
