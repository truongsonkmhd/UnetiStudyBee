package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonRequestDTO;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonResponseDTO;
import com.truongsonkmhd.unetistudy.dto.lesson_dto.CourseLessonRequest;
import com.truongsonkmhd.unetistudy.dto.lesson_dto.CourseLessonResponse;
import com.truongsonkmhd.unetistudy.model.lesson.solid.course_lesson.ContestLesson;

import java.util.List;
import java.util.UUID;

public interface ContestLessonService {
    ContestLessonResponseDTO addContestLesson(ContestLessonRequestDTO request);

    void addCodingExercisesToContest(List<UUID> exerciseTemplateIds, ContestLesson contestLesson);

}
