package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionShowDTO;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.CodingSubmission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CodingSubmissionShowMapper extends EntityMapper<CodingSubmissionShowDTO, CodingSubmission> {

    @Override
    @Mapping(source = "exercise.title", target = "exerciseName")
    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "language", target = "language")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "testCasesPassed", target = "testCasesPassed")
    @Mapping(source = "totalTestCases", target = "totalTestCases")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "submittedAt", target = "submittedAt")
    CodingSubmissionShowDTO toDto(CodingSubmission entity);

    @Override
    @Mapping(source = "exerciseName", target = "exercise", ignore = true)
    @Mapping(source = "userName", target = "user", ignore = true)
    @Mapping(source = "code", target = "code")
    @Mapping(source = "language", target = "language")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "testCasesPassed", target = "testCasesPassed")
    @Mapping(source = "totalTestCases", target = "totalTestCases")
    @Mapping(source = "score", target = "score")
    @Mapping(source = "submittedAt", target = "submittedAt")
    // ignore những field không có trong DTO
    @Mapping(target = "submissionID", ignore = true)
    @Mapping(target = "executionTime", ignore = true)
    @Mapping(target = "memoryUsed", ignore = true)
    CodingSubmission toEntity(CodingSubmissionShowDTO dto);
}
