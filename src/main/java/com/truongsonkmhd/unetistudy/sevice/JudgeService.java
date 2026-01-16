package com.truongsonkmhd.unetistudy.sevice;


import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionResponseDTO;

public interface JudgeService {
    JudgeRunResponseDTO runUserCode(JudgeRequestDTO request);
    CodingSubmissionResponseDTO submitUserCode(JudgeRequestDTO request);
}
