package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.coding_submission.CodingSubmissionShowDTO;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.lesson.CodingSubmission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CodingSubmissionShowMapper extends EntityMapper<CodingSubmissionShowDTO, CodingSubmission> {

    @Override
    CodingSubmissionShowDTO toDto(CodingSubmission entity);

    @Override
    CodingSubmission toEntity(CodingSubmissionShowDTO dto);
}
