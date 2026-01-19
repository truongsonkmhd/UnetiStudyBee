package com.truongsonkmhd.unetistudy.sevice;


import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionShowDTO;
import com.truongsonkmhd.unetistudy.model.lesson.CodingSubmission;

import java.util.List;
import java.util.UUID;

public interface CodingSubmissionService {
    CodingSubmission save(CodingSubmission codingSubmission);
    List<CodingSubmissionShowDTO> getCodingSubmissionShowByUserName(String theUserName, String theSlug);
    List<CodingSubmissionShowDTO> getCodingSubmissionShowBySlugExercise(String theSlug);
    CodingSubmission getSubmissionById(UUID id);
}
