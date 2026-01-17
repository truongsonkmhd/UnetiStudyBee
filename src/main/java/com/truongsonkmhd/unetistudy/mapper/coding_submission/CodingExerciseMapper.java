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
    @Mapping(target = "lesson", ignore = true) // set trong service bằng lessonId
    @Mapping(target = "solutionCode", ignore = true) // không nhận từ client
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "exerciseTestCases", ignore = true) // QUAN TRỌNG
    CodingExercise toEntity(CodingExerciseDTO dto);
}
