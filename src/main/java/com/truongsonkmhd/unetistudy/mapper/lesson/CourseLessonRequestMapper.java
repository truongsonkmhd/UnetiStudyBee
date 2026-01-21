package com.truongsonkmhd.unetistudy.mapper.lesson;

import com.truongsonkmhd.unetistudy.common.LessonType;
import com.truongsonkmhd.unetistudy.dto.lesson_dto.CourseLessonRequest;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.lesson.solid.course_lesson.CourseLesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {LessonType.class})
public interface CourseLessonRequestMapper extends EntityMapper<CourseLessonRequest, CourseLesson> {

    @Override
    @Mapping(target = "lessonId", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "creator", ignore = true)

    @Mapping(target = "contestLesson", ignore = true) // contest xử lý ở service

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    @Mapping(target = "slug", ignore = true) // slug set ở service (hoặc generate)

    // defaults
    @Mapping(target = "lessonType",
            expression = "java(dto.getLessonType() != null ? dto.getLessonType() : LessonType.OTHER)")
    @Mapping(target = "isPreview",
            expression = "java(dto.getIsPreview() != null ? dto.getIsPreview() : false)")
    @Mapping(target = "isPublished",
            expression = "java(dto.getIsPublished() != null ? dto.getIsPublished() : false)")
    @Mapping(target = "orderIndex",
            expression = "java(dto.getOrderIndex() != null ? dto.getOrderIndex() : 0)")
    CourseLesson toEntity(CourseLessonRequest dto);
}
