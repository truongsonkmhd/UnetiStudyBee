package com.truongsonkmhd.unetistudy.dto.CodingSubmission;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodingSubmissionResponseDTO {
    UUID exerciseID;
    UUID userID;
    String code;
    String language;
    String status;
    Integer testCasesPassed;
    Integer totalTestCases;
    Integer score;
}
