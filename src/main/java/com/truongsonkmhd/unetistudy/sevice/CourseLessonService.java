package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.custom.request.lesson.LessonRequest;
import com.truongsonkmhd.unetistudy.dto.custom.response.lesson.LessonResponse;

import java.util.List;
import java.util.UUID;

public interface CourseLessonService {
    List<LessonResponse> getLessonById(Long theID);

    List<LessonResponse> getLessonShowDTOByModuleIDAndSlug(Long moduleID, String search);
    List<LessonResponse> getContestShowDTOByIsContest(Long moduleID);
    List<LessonResponse> getEssayContestShowDTOByIsContest(Long moduleID);
    List<LessonResponse> getLessonAll();
    LessonResponse addLesson(LessonRequest request);

    LessonResponse update(UUID theId, LessonRequest request);

    UUID delete(UUID theId);

}
