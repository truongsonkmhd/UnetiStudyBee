package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.model.CodingSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodingSubmissionRepository extends JpaRepository<CodingSubmission, Long> {
    @Query("""
            SELECT cb
            FROM CodingSubmission cb
            WHERE cb.user.username = :userName AND cb.exercise.slug = :theSlug
            ORDER BY cb.submittedAt DESC
            """)
    List<CodingSubmission> getCodingSubmissionShowByUserName(@Param("userName") String theUserName, @Param("theSlug") String theSlug);

    @Query("""
            SELECT cb
            FROM CodingSubmission cb
            WHERE cb.exercise.slug = :theSlug
            ORDER BY cb.submittedAt DESC
            """)
    List<CodingSubmission> getCodingSubmissionShowBySlugExercise(@Param("theSlug") String theSlug);
}
