package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDetailDTO;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.CodingExercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ExerciseTestCaseMapper.class})
public interface CodingExerciseDetailMapper extends EntityMapper<CodingExerciseDetailDTO, CodingExercise> {

    @Override
    @Mapping(source = "exerciseId", target = "exerciseID")
    @Mapping(source = "timeLimitMs", target = "timeLimit")
    @Mapping(source = "memoryLimitMb", target = "memoryLimit")
    CodingExerciseDetailDTO toDto(CodingExercise entity);

    @Override
    @Mapping(source = "exerciseID", target = "exerciseId")
    @Mapping(source = "timeLimit", target = "timeLimitMs")
    @Mapping(source = "memoryLimit", target = "memoryLimitMb")
    // ignore những trường không có trong DTO
    @Mapping(target = "lesson", ignore = true)
    @Mapping(target = "solutionCode", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CodingExercise toEntity(CodingExerciseDetailDTO dto);
}
