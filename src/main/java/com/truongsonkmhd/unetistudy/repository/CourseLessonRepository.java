package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.model.CourseLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseLessonRepository extends JpaRepository<CourseLesson, UUID> {
}
