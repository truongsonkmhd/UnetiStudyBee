package com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JudgeRequestDTO {
    String sourceCode;
    String language;
    UUID exerciseID;
}
