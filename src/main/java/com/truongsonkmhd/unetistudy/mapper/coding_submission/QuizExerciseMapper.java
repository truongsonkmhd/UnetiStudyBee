package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.QuizDTO;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.model.lesson.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuizExerciseMapper extends EntityMapper<QuizDTO, Quiz> {

}
