package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    //Hàm này tương đương: SELECT COUNT(*) > 0 FROM courses WHERE slug = :slug
    boolean existsBySlug(String slug);


}
