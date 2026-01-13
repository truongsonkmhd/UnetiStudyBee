package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionResponseDTO;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.lesson.CodingSubmission;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CodingSubmissionMapper extends EntityMapper<CodingSubmissionResponseDTO, CodingSubmission> {


    // ENTITY -> DTO
    @Override
    @Mapping(source = "exercise.exerciseId", target = "exerciseID")
    @Mapping(source = "user.id", target = "userID")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "testCasesPassed", source = "testCasesPassed")
    @Mapping(target = "totalTestCases", source = "totalTestCases")
    @Mapping(target = "score", source = "score")
    CodingSubmissionResponseDTO toDto(CodingSubmission entity);

    // DTO -> ENTITY
    @Override
    @Mapping(source = "exerciseID", target = "exercise.exerciseId")
    @Mapping(source = "userID", target = "user.id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "language", source = "language")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "testCasesPassed", source = "testCasesPassed")
    @Mapping(target = "totalTestCases", source = "totalTestCases")
    @Mapping(target = "score", source = "score")
    @Mapping(target = "submissionID", ignore = true)
    @Mapping(target = "executionTime", ignore = true)
    @Mapping(target = "memoryUsed", ignore = true)
    @Mapping(target = "submittedAt", ignore = true)
    CodingSubmission toEntity(CodingSubmissionResponseDTO dto);

    // partialUpdate
//    @Override
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(source = "exerciseID", target = "exercise.exerciseId")
//    @Mapping(source = "userID", target = "user")
//    @Mapping(target = "submissionID", ignore = true)
//    @Mapping(target = "executionTime", ignore = true)
//    @Mapping(target = "memoryUsed", ignore = true)
//    @Mapping(target = "submittedAt", ignore = true)
//    void partialUpdate(@MappingTarget CodingSubmission entity, CodingSubmissionResponseDTO dto);
}

