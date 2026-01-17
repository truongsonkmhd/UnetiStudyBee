package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CodingExerciseRepository extends JpaRepository<CodingExercise, UUID> {

    @Query("""
        select e
        from CodingExercise e
        where e.lesson.lessonId in :lessonIds
        """)
    List<CodingExercise> findExercisesByLessonIds(List<UUID> lessonIds);


    @Query("""
       SELECT ce
       FROM CodingExercise ce
       LEFT JOIN FETCH ce.exerciseTestCases tc
       WHERE ce.lesson.slug = :theSlug
        """)
    List<CodingExerciseDTO> getCodingExerciseByLessonSlug(@Param("theSlug") String theSlug);

    @Query("""
       SELECT ce
       FROM CodingExercise ce
       WHERE ce.slug = :theSlug
       """)
    CodingExercise getCodingExerciseDetailDTOByExerciseSlug(@Param("theSlug") String theSlug);

    @Query("SELECT ce FROM CodingExercise ce WHERE ce.exerciseId = :exerciseID")
    CodingExercise getExerciseEntityByID(@Param("exerciseId") UUID exerciseID);

    // Kiểm tra xem CodingExercise có phải nằm trong contest không
    @Query("""
        SELECT ce.lesson.isContest 
        FROM CodingExercise ce 
        WHERE ce.exerciseId = :exerciseId
        """)
    boolean isExerciseInContestLesson(@Param("exerciseId") UUID exerciseId);

    // LẤY RA lessonID từ coding exerciseID
    @Query("""
        SELECT ce.lesson.lessonId 
        FROM CodingExercise ce 
        WHERE ce.exerciseId = :exerciseId
    """)
    UUID getLessonIDByExerciseID(@Param("exerciseId") UUID exerciseID);

}
