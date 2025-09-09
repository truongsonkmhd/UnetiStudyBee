package com.truongsonkmhd.unetistudy.mapper.lesson;

import com.truongsonkmhd.unetistudy.dto.custom.request.lesson.LessonRequest;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseLessonRequestMapper extends EntityMapper<LessonRequest, CourseLesson> {

}
