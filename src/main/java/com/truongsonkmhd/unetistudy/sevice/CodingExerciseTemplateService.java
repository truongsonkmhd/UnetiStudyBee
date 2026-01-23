package com.truongsonkmhd.unetistudy.sevice;


import com.truongsonkmhd.unetistudy.common.Difficulty;
import com.truongsonkmhd.unetistudy.dto.coding_exercise_dto.CodingExerciseTemplateCardResponse;
import com.truongsonkmhd.unetistudy.dto.coding_exercise_dto.CodingExerciseTemplateDTO;
import com.truongsonkmhd.unetistudy.dto.a_common.CursorResponse;
import com.truongsonkmhd.unetistudy.dto.a_common.PageResponse;
import com.truongsonkmhd.unetistudy.model.lesson.template.CodingExerciseTemplate;

public interface CodingExerciseTemplateService {
    CodingExerciseTemplate save(CodingExerciseTemplateDTO codingExercise);

    PageResponse<CodingExerciseTemplateCardResponse> getPublishedTemplates(
            int page, int size);

    PageResponse<CodingExerciseTemplateCardResponse> searchTemplates(
            int page, int size, String q, Difficulty difficulty,
            String category, String language);

    PageResponse<CodingExerciseTemplateCardResponse> searchAllTemplates(
            int page, int size, String q, Difficulty difficulty,
            String category, String language, Boolean published);

    CursorResponse<CodingExerciseTemplateCardResponse> getPublishedTemplatesCursor(
            String cursor, int size);

    CursorResponse<CodingExerciseTemplateCardResponse> searchTemplatesCursor(
            String cursor, int size, String q, Difficulty difficulty,
            String category, String language);
}
