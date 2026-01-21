package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonRequestDTO;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonResponseDTO;

public interface ContestLessonService {
    ContestLessonResponseDTO addContestLesson(ContestLessonRequestDTO request);

}
