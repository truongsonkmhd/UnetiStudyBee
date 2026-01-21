package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.exercise_test_cases_dto.ExerciseTestCasesDTO;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.lesson.solid.course_lesson.ExerciseTestCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExerciseTestCaseMapper
        extends EntityMapper<ExerciseTestCasesDTO, ExerciseTestCase> {

    @Override
    @Mapping(target = "codingExercise", ignore = true)
    ExerciseTestCase toEntity(ExerciseTestCasesDTO dto);

    @Override
    ExerciseTestCasesDTO toDto(ExerciseTestCase entity);
}
