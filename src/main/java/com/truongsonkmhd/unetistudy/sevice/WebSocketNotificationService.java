package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionResponseDTO;

import java.util.UUID;

public interface WebSocketNotificationService {
    void notifySubmissionResult(UUID userId, CodingSubmissionResponseDTO submission);
    void notifySubmissionStatus(UUID userId, UUID submissionId, String status);
    void broadcastSubmission(CodingSubmissionResponseDTO submission);
}
