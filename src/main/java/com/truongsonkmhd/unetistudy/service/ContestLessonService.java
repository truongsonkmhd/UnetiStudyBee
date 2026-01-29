package com.truongsonkmhd.unetistudy.service;

import com.truongsonkmhd.unetistudy.common.StatusContest;
import com.truongsonkmhd.unetistudy.dto.a_common.PageResponse;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonRequestDTO;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonResponseDTO;

import java.util.UUID;

public interface ContestLessonService {
    ContestLessonResponseDTO addContestLesson(ContestLessonRequestDTO request);

    PageResponse<ContestLessonResponseDTO> searchContestLessons(
            int page,
            int size,
            String q,
            StatusContest statusContest);

    ContestLessonResponseDTO getById(UUID contestId);

    ContestLessonResponseDTO updateContestLesson(UUID contestId, ContestLessonRequestDTO request);

    void deleteContestLesson(UUID contestId);

    void publishContestLesson(UUID contestId);

    void archiveContestLesson(UUID contestId);
}
