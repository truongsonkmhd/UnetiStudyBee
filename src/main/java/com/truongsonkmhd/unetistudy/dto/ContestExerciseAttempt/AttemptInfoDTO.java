package com.truongsonkmhd.unetistudy.dto.ContestExerciseAttempt;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttemptInfoDTO {
     UUID lessonID;
     String exerciseType;
     Integer attemptNumber;
}
