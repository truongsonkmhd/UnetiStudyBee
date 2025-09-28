package com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JudgeRunResponseDTO {
    String output;
    String status;
    String message;
}
