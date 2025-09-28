package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonRequest;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonResponse;

import java.util.List;
import java.util.UUID;

public interface CourseLessonService {
    List<LessonResponse> getLessonByModuleId(UUID moduleId);

    List<LessonResponse> getLessonByModuleIDAndSlug(UUID moduleID, String slug);
    List<LessonResponse> getCodingContest(UUID moduleID);
    List<LessonResponse> getMultipleChoiceContest(UUID moduleID);
    List<LessonResponse> getLessonAll();
    LessonResponse addLesson(LessonRequest request);

    LessonResponse update(UUID theId, LessonRequest request);

    UUID delete(UUID theId);

}
