package com.truongsonkmhd.unetistudy.mapper.lesson;

import com.truongsonkmhd.unetistudy.common.LessonType;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonRequest;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , imports = {LessonType.class})
public interface CourseLessonRequestMapper extends EntityMapper<LessonRequest, CourseLesson> {

    @Override
    @Mapping(target = "lessonId", ignore = true)
    @Mapping(target = "module", ignore = true)     // set ở service
    @Mapping(target = "creator", ignore = true)    // set ở service

    @Mapping(target = "codingExercises", ignore = true)
    @Mapping(target = "quizzes", ignore = true)

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    @Mapping(target = "slug", ignore = true)       // generate
    @Mapping(target = "lessonType",
            expression = "java(dto.getLessonType() != null ? dto.getLessonType() : LessonType.OTHER)")
    @Mapping(target = "isPreview",
            expression = "java(dto.getIsPreview() != null ? dto.getIsPreview() : false)")
    @Mapping(target = "isPublished",
            expression = "java(dto.getIsPublished() != null ? dto.getIsPublished() : false)")
    @Mapping(target = "isContest",
            expression = "java(dto.getIsContest() != null ? dto.getIsContest() : false)")
    @Mapping(target = "totalPoints",
            expression = "java(dto.getTotalPoints() != null ? dto.getTotalPoints() : 0)")
    CourseLesson toEntity(LessonRequest dto);
}
