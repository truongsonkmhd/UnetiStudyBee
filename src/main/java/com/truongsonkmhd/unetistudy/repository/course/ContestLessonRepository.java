package com.truongsonkmhd.unetistudy.repository.course;

import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.ContestLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContestLessonRepository extends JpaRepository<ContestLesson, UUID> {

}
