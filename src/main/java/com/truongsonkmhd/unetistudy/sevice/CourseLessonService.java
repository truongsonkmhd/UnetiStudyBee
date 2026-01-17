package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.LessonDTO.CourseLessonResponse;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.CourseLessonRequest;
import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseLessonService {
    List<CourseLessonResponse> getLessonByModuleId(UUID moduleId);

    List<CourseLessonResponse> getLessonByModuleIDAndSlug(UUID moduleID, String slug);
    List<CourseLessonResponse> getCodingContest(UUID moduleID);
    List<CourseLessonResponse> getMultipleChoiceContest(UUID moduleID);
    List<CourseLessonResponse> getLessonAll();
    CourseLessonResponse addLesson(CourseLessonRequest request);

    CourseLessonResponse update(UUID theId, CourseLessonRequest request);
    Optional<CourseLesson> findById(UUID id);
    UUID delete(UUID theId);

}
