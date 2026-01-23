package com.truongsonkmhd.unetistudy.model.lesson.course_lesson;

import com.truongsonkmhd.unetistudy.common.StatusContest;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * ContestLesson: Định nghĩa Template/Mẫu của một Contest
 * - Chứa CÂU HỎI, BÀI TẬP, CẤU HÌNH CHUNG
 * - KHÔNG chứa thời gian cụ thể cho từng lớp
 * - Giống như "đề thi gốc" có thể dùng lại nhiều lần
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_contest_lesson",
        indexes = {
                @Index(name = "idx_contest_active", columnList = "is_active"),
                @Index(name = "idx_contest_status", columnList = "status")
        })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContestLesson {

    @Id
    @UuidGenerator
    @Column(name = "contest_lesson_id", nullable = false, updatable = false)
    UUID contestLessonId;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "description", columnDefinition = "text")
    String description;

    // Quan hệ với các lớp sử dụng contest này
    @OneToMany(mappedBy = "contestLesson", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    List<ClassContest> classContests = new ArrayList<>();

    // Câu hỏi và bài tập (NỘI DUNG của contest)
    @OneToMany(mappedBy = "contestLesson", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    List<CodingExercise> codingExercises = new ArrayList<>();

    @OneToMany(mappedBy = "contestLesson", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    List<Quiz> quizzes = new ArrayList<>();

    // ======= CẤU HÌNH MẶC ĐỊNH (Template) =======
    // Các lớp có thể ghi đè (override) những giá trị này

    @Column(name = "default_duration_minutes")
    Integer defaultDurationMinutes; // Thời lượng mặc định (phút)

    @Column(name = "total_points", nullable = false, columnDefinition = "INT DEFAULT 0")
    @Builder.Default
    Integer totalPoints = 0;

    @Column(name = "default_max_attempts")
    Integer defaultMaxAttempts; // Số lần thử mặc định

    @Column(name = "passing_score")
    Integer passingScore; // Điểm đạt

    @Column(name = "show_leaderboard_default", nullable = false)
    @Builder.Default
    Boolean showLeaderboardDefault = true;

    // ======= TRẠNG THÁI CỦA CONTEST TEMPLATE =======
    // DRAFT: Đang soạn thảo, chưa sẵn sàng
    // READY: Sẵn sàng để gán vào lớp
    // ARCHIVED: Đã lưu trữ, không dùng nữa
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    @Builder.Default
    StatusContest status = StatusContest.DRAFT;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    Boolean isActive = true;

    @Column(name = "instructions", columnDefinition = "text")
    String instructions; // Hướng dẫn chung

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    Instant updatedAt;

    // ======= HELPER METHODS =======

    public void addCodingExercise(CodingExercise exercise) {
        codingExercises.add(exercise);
        exercise.setContestLesson(this);
        recalculateTotalPoints();
    }

    public void removeCodingExercise(CodingExercise exercise) {
        codingExercises.remove(exercise);
        exercise.setContestLesson(null);
        recalculateTotalPoints();
    }

    public void addQuizQuestion(Quiz quiz) {
        quizzes.add(quiz);
        quiz.setContestLesson(this);
        recalculateTotalPoints();
    }

    public void removeQuizQuestion(Quiz quiz) {
        quizzes.remove(quiz);
        quiz.setContestLesson(null);
        recalculateTotalPoints();
    }

    public void addClassContest(ClassContest classContest) {
        classContests.add(classContest);
        classContest.setContestLesson(this);
    }

    public void removeClassContest(ClassContest classContest) {
        classContests.remove(classContest);
        classContest.setContestLesson(null);
    }

    // Tự động tính tổng điểm
    private void recalculateTotalPoints() {
        int codingPoints = codingExercises.stream()
                .mapToInt(CodingExercise::getPoints)
                .sum();
//        int quizPoints = quizzes.stream()
//                .mapToInt(Quiz::getPoints)
//                .sum();
        int quizPoints = 0;
        this.totalPoints = codingPoints + quizPoints;
    }

    // ======= BUSINESS LOGIC =======

    public boolean isReadyToUse() {
        return isActive &&
                status == StatusContest.READY &&
                (!codingExercises.isEmpty() || !quizzes.isEmpty());
    }

    public boolean canBeAssignedToClass() {
        return isActive &&
                (status == StatusContest.READY);
    }

    // Kiểm tra xem contest có đang được dùng trong lớp nào không
    public boolean isUsedInClasses() {
        return !classContests.isEmpty();
    }

    // Lấy số lớp đang dùng contest này
    public long getActiveClassCount() {
        return classContests.stream()
                .filter(ClassContest::getIsActive)
                .count();
    }

    // QUAN TRỌNG: Cập nhật status
    // Nếu contest đang được dùng trong lớp, cần cảnh báo
    public void updateStatus(StatusContest newStatus) {
        if (isUsedInClasses() && newStatus == StatusContest.ARCHIVED) {
            throw new IllegalStateException(
                    "Cannot archive contest that is currently used in " + getActiveClassCount() + " class(es). " +
                            "Please remove it from all classes first or mark class contests as inactive."
            );
        }
        this.status = newStatus;
    }
}