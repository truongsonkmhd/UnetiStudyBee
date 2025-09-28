package com.truongsonkmhd.unetistudy.sevice;


import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionResponseDTO;

public interface JudgeService {
    JudgeRunResponseDTO runUserCode(JudgeRequestDTO request, String language);
    CodingSubmissionResponseDTO submitUserCode(JudgeRequestDTO request, String language);
}
