package com.truongsonkmhd.unetistudy.repository.course;

import com.truongsonkmhd.unetistudy.common.StatusContest;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonResponseDTO;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.ContestLesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContestLessonRepository extends JpaRepository<ContestLesson, UUID> {

    @Query("""
select new com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonResponseDTO(
    ct.contestLessonId,
    ct.title,
    ct.description,
    ct.defaultDurationMinutes,
    ct.totalPoints,
    ct.defaultMaxAttempts,
    ct.passingScore,
    ct.showLeaderboardDefault,
    ct.instructions,
    ct.status
)
from ContestLesson ct
where
    (:q is null or :q = '' or lower(ct.title) like lower(concat('%', :q, '%')))
and
    (:statusContest is null or ct.status = :statusContest)
order by ct.createdAt desc
""")
    Page<ContestLessonResponseDTO> searchContestAdvance(
            @Param("q") String q,
            @Param("statusContest") StatusContest statusContest,
            Pageable pageable);


}
