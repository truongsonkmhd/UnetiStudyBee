package com.truongsonkmhd.unetistudy.mapper.lesson;

import com.truongsonkmhd.unetistudy.dto.custom.response.lesson.LessonResponse;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseLessonResponseMapper extends EntityMapper<LessonResponse, CourseLesson> {
}
