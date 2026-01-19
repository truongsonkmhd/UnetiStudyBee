package com.truongsonkmhd.unetistudy.model.mq;

import lombok.AllArgsConstructor;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JudgeSubmitMessage {
    private UUID submissionId;
    private UUID exerciseId;
    private UUID userId;

    private String code;
    private String language;

    private Instant createdAt;
}

