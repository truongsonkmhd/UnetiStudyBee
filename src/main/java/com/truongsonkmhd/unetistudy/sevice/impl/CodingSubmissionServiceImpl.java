package com.truongsonkmhd.unetistudy.sevice.impl;

import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionShowDTO;
import com.truongsonkmhd.unetistudy.mapper.coding_submission.CodingSubmissionShowMapper;
import com.truongsonkmhd.unetistudy.model.lesson.CodingSubmission;
import com.truongsonkmhd.unetistudy.repository.CodingSubmissionRepository;
import com.truongsonkmhd.unetistudy.sevice.CodingSubmissionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodingSubmissionServiceImpl implements CodingSubmissionService {
    private final CodingSubmissionRepository codingSubmissionRepository;

    private final CodingSubmissionShowMapper codingSubmissionShowMapper;

    @Override
    @Transactional
    public CodingSubmission save(CodingSubmission codingSubmission) {
        return codingSubmissionRepository.save(codingSubmission);
    }

    @Override
    public List<CodingSubmissionShowDTO> getCodingSubmissionShowByUserName(String theUserName, String theSlug) {
        return codingSubmissionShowMapper.toDto(codingSubmissionRepository.getCodingSubmissionShowByUserName(theUserName, theSlug));
    }

    @Override
    public List<CodingSubmissionShowDTO> getCodingSubmissionShowBySlugExercise(String theSlug) {
        return codingSubmissionShowMapper.toDto(codingSubmissionRepository.getCodingSubmissionShowBySlugExercise(theSlug)) ;
    }
}
