package com.truongsonkmhd.unetistudy.mapper.lesson;

import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonResponse;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseLessonResponseMapper extends EntityMapper<LessonResponse, CourseLesson> {
}
