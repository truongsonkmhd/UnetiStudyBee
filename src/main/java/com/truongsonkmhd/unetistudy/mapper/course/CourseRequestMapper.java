package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseShowRequest;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.course.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseRequestMapper extends EntityMapper<CourseShowRequest, Course> {

    @Override
    @Mapping(target = "courseId", ignore = true)             // id sinh ra trong DB
    @Mapping(target = "slug", ignore = true)           // slug tự generate từ title
    @Mapping(target = "instructor", ignore = true)     // từ instructorId sẽ set ở service
    @Mapping(target = "enrolledCount", ignore = true)  // hệ thống tự tăng
    @Mapping(target = "rating", ignore = true)         // tính toán từ review
    @Mapping(target = "ratingCount", ignore = true)    // tính toán từ review
    @Mapping(target = "status", ignore = true)         // backend set mặc định ("draft"/"published")
    @Mapping(target = "publishedAt", ignore = true)    // chỉ set khi publish
    @Mapping(target = "createdAt", ignore = true)      // hệ thống tự set
    @Mapping(target = "updatedAt", ignore = true)      // hệ thống tự set
    Course toEntity(CourseShowRequest dto);
}
