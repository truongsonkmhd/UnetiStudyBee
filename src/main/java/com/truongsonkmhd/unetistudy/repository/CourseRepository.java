package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    //Hàm này tương đương: SELECT COUNT(*) > 0 FROM courses WHERE slug = :slug
    boolean existsBySlug(String slug);


}
