package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.lesson_dto.AiLessonSuggestionDTO;

import java.util.List;
import java.util.UUID;

public interface AiLearningPathService {
    List<AiLessonSuggestionDTO> suggestNextLessons(UUID userId, String courseSlug);
}
