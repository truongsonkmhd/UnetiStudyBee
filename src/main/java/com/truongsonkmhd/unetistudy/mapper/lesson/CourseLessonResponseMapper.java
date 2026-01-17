package com.truongsonkmhd.unetistudy.mapper.lesson;

import com.truongsonkmhd.unetistudy.dto.LessonDTO.CourseLessonResponse;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.truongsonkmhd.unetistudy.common.LessonType;

@Mapper(componentModel = "spring",
         imports = {LessonType.class}
)
public interface CourseLessonResponseMapper extends EntityMapper<CourseLessonResponse, CourseLesson> {

    @Mapping(target = "lessonId", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    @Mapping(target = "slug", ignore = true)
    CourseLesson toEntity(CourseLessonResponse dto);
}
