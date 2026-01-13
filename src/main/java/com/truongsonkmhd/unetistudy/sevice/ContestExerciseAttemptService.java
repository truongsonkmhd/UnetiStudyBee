package com.truongsonkmhd.unetistudy.sevice;


import com.truongsonkmhd.unetistudy.dto.ContestExerciseAttempt.AttemptInfoDTO;
import com.truongsonkmhd.unetistudy.model.lesson.ContestExerciseAttempt;

import java.util.UUID;

public interface ContestExerciseAttemptService {
    AttemptInfoDTO getAttemptInfoDTOByUserIDAndExerciseID(UUID userID, UUID exerciseID, String exerciseType);
    ContestExerciseAttempt save(ContestExerciseAttempt contestExerciseAttempt);
}
