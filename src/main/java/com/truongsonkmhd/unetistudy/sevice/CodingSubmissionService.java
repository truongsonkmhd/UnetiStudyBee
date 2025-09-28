package com.truongsonkmhd.unetistudy.sevice;


import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionShowDTO;
import com.truongsonkmhd.unetistudy.model.CodingSubmission;

import java.util.List;

public interface CodingSubmissionService {
    CodingSubmission save(CodingSubmission codingSubmission);
    List<CodingSubmissionShowDTO> getCodingSubmissionShowByUserName(String theUserName, String theSlug);
    List<CodingSubmissionShowDTO> getCodingSubmissionShowBySlugExercise(String theSlug);

}
