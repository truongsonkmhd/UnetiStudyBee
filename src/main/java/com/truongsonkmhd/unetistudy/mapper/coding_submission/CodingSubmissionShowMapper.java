package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionShowDTO;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.lesson.CodingSubmission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CodingSubmissionShowMapper extends EntityMapper<CodingSubmissionShowDTO, CodingSubmission> {

    @Override
    CodingSubmissionShowDTO toDto(CodingSubmission entity);

    @Override
    CodingSubmission toEntity(CodingSubmissionShowDTO dto);
}
