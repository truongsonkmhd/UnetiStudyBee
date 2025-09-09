package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.model.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseModuleRepository extends JpaRepository<CourseModule, UUID> {

    @Query("SELECT cm FROM CourseModule cm WHERE cm.course.slug = :slug")
    List<CourseModule> getCourseModuleByCourseSlug(@Param("slug") String slug);
}
