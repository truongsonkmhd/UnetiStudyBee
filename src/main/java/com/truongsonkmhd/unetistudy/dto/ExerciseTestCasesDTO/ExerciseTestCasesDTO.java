package com.truongsonkmhd.unetistudy.dto.ExerciseTestCasesDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExerciseTestCasesDTO {
    String input;
    String expectedOutput;
    Boolean isPublic;
    Integer score;
}
