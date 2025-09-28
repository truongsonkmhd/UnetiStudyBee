package com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO;

import com.truongsonkmhd.unetistudy.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class CodingExerciseDetailDTO {
        UUID exerciseID;
        Set<ExerciseTestCasesDTO> exerciseTestCases;
        String title;
        String description;
        String programmingLanguage;
        String initialCode;
        Integer timeLimit;
        Integer memoryLimit;
        String difficulty;
        Integer points;
        String slug;
        String inputFormat;
        String outputFormat;
        String constraintName;

    }
