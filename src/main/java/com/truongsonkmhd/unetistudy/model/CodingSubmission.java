package com.truongsonkmhd.unetistudy.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_coding_submission")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodingSubmission {

    @Id
    @UuidGenerator
    @Column(name = "submission_id", nullable = false, updatable = false)
    UUID submissionID;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG CODINGEXERCISES
    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    CodingExercise exercise;

    // KHÓA NGOẠI THAM CHIẾU ĐẾN BẢNG USERS
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    // CODE ĐÃ NỘP
    @Column(name = "code", columnDefinition = "text")
    String code;

    // NGÔN NGỮ LẬP TRÌNH SỬ DỤNG
    @Column(name = "language", length = 50)
    String language;

    // TRẠNG THÁI CHẤM BÀI (CHỈ NHẬN GIÁ TRỊ HỢP LỆ)
    @Column(name = "status", length = 20)
    String status;

    // THỜI GIAN CHẠY (MILI GIÂY)
    @Column(name = "execution_time")
    Integer executionTime;

    // BỘ NHỚ SỬ DỤNG (KB)
    @Column(name = "memory_used")
    Integer memoryUsed;

    // SỐ TEST CASE ĐÃ PASS
    @Column(name = "test_cases_passed", nullable = false, columnDefinition = "INT DEFAULT 0")
    Integer testCasesPassed;

    // TỔNG SỐ TEST CASE
    @Column(name = "total_test_cases", nullable = false, columnDefinition = "INT DEFAULT 0")
    Integer totalTestCases;

    // ĐIỂM ĐẠT ĐƯỢC
    @Column(name = "score", nullable = false, columnDefinition = "INT DEFAULT 0")
    Integer score;

    // THỜI ĐIỂM NỘP BÀI
    @Column(name = "submitted_at", nullable = true, columnDefinition = "DATETIME DEFAULT GETDATE()")
    LocalDateTime submittedAt;
}
