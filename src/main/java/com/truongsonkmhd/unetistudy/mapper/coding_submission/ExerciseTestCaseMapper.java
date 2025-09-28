package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.ExerciseTestCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExerciseTestCaseMapper extends EntityMapper<ExerciseTestCasesDTO, ExerciseTestCase> {
    @Override
    // bỏ qua các trường không có trong DTO
    @Mapping(target = "testCaseID", ignore = true)
    @Mapping(target = "codingExercise", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ExerciseTestCase toEntity(ExerciseTestCasesDTO dto);

    @Override
    ExerciseTestCasesDTO toDto(ExerciseTestCase entity);




}
