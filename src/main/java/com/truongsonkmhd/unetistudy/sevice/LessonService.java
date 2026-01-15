package com.truongsonkmhd.unetistudy.sevice;


import com.truongsonkmhd.unetistudy.dto.LessonDTO.*;
import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    List<LessonShowDTO> getLessonShowDTO(UUID theID);
    List<LessonShowDTO> getLessonShowDTOByModuleIDAndSlug(UUID moduleID, String search);
    List<ContestShowDTO> getContestShowDTOByIsContest(UUID moduleID);
    List<ContestShowDTO> getEssayContestShowDTOByIsContest(UUID moduleID);
    List<LessonShowDTOA> getLessonShowDTOA();
    CourseLesson addLesson(CourseCreateLessonsDTO dto);
    List<ContestManagementShowDTO> getContestManagementShowDTO(UUID moduleID, String userName);
    CourseLesson save(CourseLesson theLesson);
    EditLessonDTO getEditLessonDTO(UUID moduleID, String theSlug);
    Optional<CourseLesson> findById(UUID id);

}
