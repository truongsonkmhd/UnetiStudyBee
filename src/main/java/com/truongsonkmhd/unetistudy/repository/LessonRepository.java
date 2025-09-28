package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.dto.CourseModule.CourseModuleFILLDTO;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.*;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<CourseLesson, UUID> {
    // lấy ra lesson luyện tập
    @Query("""
        SELECT cl
        FROM CourseLesson cl
        WHERE cl.module.moduleId = :moduleID AND cl.isContest = false
    """)
    List<CourseLesson> getLessonShowDTO(@Param("moduleID") UUID moduleID);

    // tìm kiếm lesson theo slug
    @Query("""
        SELECT cl
        FROM CourseLesson cl
        WHERE cl.module.moduleId = :moduleID AND cl.slug LIKE %:slug% AND cl.isContest = false
    """)
    List<CourseLesson> getLessonShowDTOByModuleIDAndSlug(@Param("moduleID") UUID moduleID, @Param("slug") String theSlug);

    //Lấy ra những lesson là contest và có type = coding
    @Query("""
        SELECT cl
        FROM CourseLesson cl
        WHERE cl.module.moduleId = :moduleID AND cl.isContest = true AND CURRENT_TIMESTAMP < cl.contestEndTime AND cl.type = "coding"
    """)
    List<ContestShowDTO> getContestShowDTOByIsContest(@Param("moduleID") UUID moduleID);

    //Lấy ra những lesson là contest và có type = essay
    @Query("""
        SELECT cl
        FROM CourseLesson cl
        WHERE cl.module.moduleId = :moduleID AND cl.isContest = true AND CURRENT_TIMESTAMP < cl.contestEndTime AND cl.type = "essay"
    """)
    List<ContestShowDTO> getEssayContestShowDTOByIsContest(@Param("moduleID") UUID moduleID);

    //Lấy ra những lesson/contest với username
    @Query("""
        SELECT cl
        FROM CourseLesson cl
        WHERE cl.module.moduleId = :moduleID AND cl.creator.username = :userName
    """)
    List<ContestManagementShowDTO> getContestManagementShowDTO(@Param("moduleID") UUID moduleID, @Param("userName") String userName);

    // lấy ra các trường có thể chỉnh sửa của lesson theo slug
    @Query("""
        SELECT cl
        FROM CourseLesson cl
        WHERE cl.module.moduleId = :moduleID AND cl.slug = :theSlug
    """)
    EditLessonDTO getEditLessonDTO(@Param("moduleID") UUID moduleID, @Param("slug") String slug);

    // Hàm này tương đương: SELECT COUNT(*) > 0 FROM courselesson WHERE slug = :slug
    boolean existsBySlug(String slug);


    @Query("select cm " +
            "from CourseModule cm " +
            "join cm.course c " +
            "join c.instructor u " +
            "where u.username = :userName")
    List<CourseModuleFILLDTO> findModulesByInstructorUserName(@Param("username") String userName);


//    @Query("SELECT cm " +
//            "FROM CourseLesson cl " +
//            "JOIN cl.module cm " +
//            "JOIN cm.course c " +
//            "JOIN c.instructor u " +
//            "JOIN u.userRoles ur " +
//            "JOIN ur.role r " +
//            "WHERE r.roleName = :roleName")
//    List<LessonShowDTOA> findLessonByRoleName(@Param("roleName") String roleName);
//
//    @Query("select new com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.LessonShowDTOA(" +
//            "cl.lessonID , cm.title , cl.title , cl.description , cl.type ,cl.content , cl.duration , r.roleName , u.userName )" +
//            "FROM CourseLesson cl " +
//            "join cl.module cm " +
//            "join cm.course c " +
//            "join c.instructor u " +
//            "join u.userRoles ur " +
//            "join ur.role r " +
//            "where u.userID = :userID")
//    List<LessonShowDTOA> findLessonByUserID(@Param("userID") Long userID);



}
