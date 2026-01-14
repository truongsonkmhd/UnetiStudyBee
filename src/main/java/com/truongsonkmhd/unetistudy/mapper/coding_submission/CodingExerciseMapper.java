package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CodingExerciseMapper extends EntityMapper<CodingExerciseDTO, CodingExercise> {

    @Override
    CodingExerciseDTO toDto(CodingExercise entity);

    @Override
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "exerciseTestCases", ignore = true)
    @Mapping(target = "solutionCode", ignore = true)
    @Mapping(target = "initialCode", ignore = true)
    @Mapping(target = "timeLimitMs", ignore = true)
    @Mapping(target = "memoryLimitMb", ignore = true)
    @Mapping(target = "inputFormat", ignore = true)
    @Mapping(target = "outputFormat", ignore = true)
    @Mapping(target = "constraintName", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CodingExercise toEntity(CodingExerciseDTO dto);
}
