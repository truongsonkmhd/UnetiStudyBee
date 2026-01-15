package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.LessonDTO.CourseLessonResponse;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonRequest;

import java.util.List;
import java.util.UUID;

public interface CourseLessonService {
    List<CourseLessonResponse> getLessonByModuleId(UUID moduleId);

    List<CourseLessonResponse> getLessonByModuleIDAndSlug(UUID moduleID, String slug);
    List<CourseLessonResponse> getCodingContest(UUID moduleID);
    List<CourseLessonResponse> getMultipleChoiceContest(UUID moduleID);
    List<CourseLessonResponse> getLessonAll();
    CourseLessonResponse addLesson(LessonRequest request);

    CourseLessonResponse update(UUID theId, LessonRequest request);

    UUID delete(UUID theId);

}
