package com.truongsonkmhd.unetistudy.repository.course;

import com.truongsonkmhd.unetistudy.model.lesson.CodingSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CodingSubmissionRepository extends JpaRepository<CodingSubmission, UUID> {

}
