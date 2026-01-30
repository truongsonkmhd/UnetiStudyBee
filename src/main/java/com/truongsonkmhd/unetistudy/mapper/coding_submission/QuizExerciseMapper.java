package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.quiz_dto.QuizDTO;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.quiz.Quiz;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuizExerciseMapper extends EntityMapper<QuizDTO, Quiz> {

}
