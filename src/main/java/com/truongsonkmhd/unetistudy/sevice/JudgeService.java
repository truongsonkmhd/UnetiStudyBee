package com.truongsonkmhd.unetistudy.sevice;


import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRequestDTO;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.JudgeRunResponseDTO;
import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionResponseDTO;
import com.truongsonkmhd.unetistudy.model.lesson.CodingSubmission;
import com.truongsonkmhd.unetistudy.model.mq.JudgeInternalResult;

public interface JudgeService {

    JudgeRunResponseDTO runUserCode(JudgeRequestDTO request);

    CodingSubmissionResponseDTO submitUserCode(JudgeRequestDTO request);

    void publishSubmitJob(CodingSubmission saved, JudgeRequestDTO request);

    JudgeInternalResult judgeCode(JudgeRequestDTO request);

    void createContestAttemptIfNeeded(CodingSubmission submission);

}
