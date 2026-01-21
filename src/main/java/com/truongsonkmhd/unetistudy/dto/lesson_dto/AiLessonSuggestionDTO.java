package com.truongsonkmhd.unetistudy.dto.lesson_dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AiLessonSuggestionDTO {
    UUID lessonId;
    String lessonTitle;
    String reason;
    Integer priority; // càng nhỏ càng nên học trước
}
