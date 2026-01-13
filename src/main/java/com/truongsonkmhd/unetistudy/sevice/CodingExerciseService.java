package com.truongsonkmhd.unetistudy.sevice;


import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDetailDTO;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;

import java.util.List;
import java.util.UUID;

public interface CodingExerciseService {

    List<CodingExerciseDTO> getCodingExerciseDTOByLessonSlug(String theSlug);

    // Lấy ra chi tiết bài tập dựa vào slug của bài tập
    CodingExerciseDetailDTO getCodingExerciseDetailDTOByExerciseSlug(String theSlug);

    CodingExercise getExerciseEntityByID(UUID exerciseID);

    boolean isExerciseInContestLesson(UUID exerciseID);

    UUID getLessonIDByExerciseID(UUID exerciseID);
}
