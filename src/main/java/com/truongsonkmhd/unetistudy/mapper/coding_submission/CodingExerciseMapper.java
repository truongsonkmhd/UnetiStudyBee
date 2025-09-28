package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.CodingExercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CodingExerciseMapper extends EntityMapper<CodingExerciseDTO, CodingExercise> {

    @Override
    @Mapping(source = "exerciseId", target = "exerciseID")
    @Mapping(source = "lesson.title", target = "lessonTitle")
    @Mapping(source = "programmingLanguage", target = "programLanguage")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "difficulty", source = "difficulty")
    @Mapping(target = "points", source = "points")
    @Mapping(target = "slug", source = "slug")
    CodingExerciseDTO toDto(CodingExercise entity);

    @Override
    @Mapping(source = "exerciseID", target = "exerciseId")
    @Mapping(source = "programLanguage", target = "programmingLanguage")
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
