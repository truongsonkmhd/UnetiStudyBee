package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.common.StatusContest;
import com.truongsonkmhd.unetistudy.dto.a_common.PageResponse;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonRequestDTO;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonResponseDTO;

public interface ContestLessonService {
    ContestLessonResponseDTO addContestLesson(ContestLessonRequestDTO request);

    PageResponse<ContestLessonResponseDTO> searchContestLessons(
            int page,
            int size,
            String q,
            StatusContest statusContest
    );
}
