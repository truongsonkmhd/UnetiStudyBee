package com.truongsonkmhd.unetistudy.repository.clazz;


import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClassRepository extends JpaRepository<Clazz, UUID> {

}