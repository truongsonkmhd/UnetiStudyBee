package com.truongsonkmhd.unetistudy.service.impl.lesson;

import com.truongsonkmhd.unetistudy.cache.CacheConstants;
import com.truongsonkmhd.unetistudy.dto.class_dto.ClazzResponse;
import com.truongsonkmhd.unetistudy.dto.class_dto.CreateClazzRequest;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ClassContestResponse;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.ClassContest;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.Clazz;
import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.repository.clazz.ClassRepository;
import com.truongsonkmhd.unetistudy.service.ClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service quản lý Lớp học (Clazz) với tích hợp Caching
 * 
 * Cache Patterns áp dụng:
 * 1. Cache-Aside - @Cacheable cho getALlClass
 * 2. Cache Invalidation - @CacheEvict khi saveClass
 * 3. Time-based Expiration - TTL 15 phút
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClassServiceImpl implements ClassService {

        private final ClassRepository classRepository;
        private final UserRepository userRepository;

        /**
         * Cache Invalidation: Xóa cache danh sách lớp học khi tạo mới
         */
        @Override
        @CacheEvict(cacheNames = CacheConstants.CLASSES, allEntries = true)
        public ClazzResponse saveClass(CreateClazzRequest createClazzRequest) {
                log.info("Creating new class: {} - Evicting cache", createClazzRequest.getClassName());

                User user = userRepository.findById(createClazzRequest.getInstructorId())
                                .orElseThrow(() -> new RuntimeException("Instructor not found"));

                Clazz clazz = Clazz.builder()
                                .classCode(createClazzRequest.getClassCode())
                                .className(createClazzRequest.getClassName())
                                .instructor(user)
                                .startDate(createClazzRequest.getStartDate())
                                .endDate(createClazzRequest.getEndDate())
                                .maxStudents(createClazzRequest.getMaxStudents())
                                .build();

                Clazz clazzSaved = classRepository.save(clazz);

                return ClazzResponse.builder()
                                .classId(clazzSaved.getClassId())
                                .classCode(clazzSaved.getClassCode())
                                .className(clazzSaved.getClassName())
                                .instructorName(clazzSaved.getInstructor().getFullName())
                                .startDate(clazzSaved.getStartDate())
                                .endDate(clazzSaved.getEndDate())
                                .maxStudents(clazzSaved.getMaxStudents())
                                .isActive(clazzSaved.getIsActive())
                                .createdAt(clazzSaved.getCreatedAt())
                                .updatedAt(clazzSaved.getUpdatedAt())
                                .build();
        }

        /**
         * Cache-Aside: Lấy tất cả lớp học
         */
        @Override
        @Cacheable(cacheNames = CacheConstants.CLASSES, key = "'all'", unless = "#result.isEmpty()")
        public List<ClazzResponse> getALlClass() {
                log.debug("Cache MISS - Loading all classes from DB");
                List<Clazz> classes = classRepository.findAll();

                return classes.stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        private ClazzResponse mapToResponse(Clazz clazz) {
                User instructor = clazz.getInstructor();

                return ClazzResponse.builder()
                                .classId(clazz.getClassId())
                                .classCode(clazz.getClassCode())
                                .className(clazz.getClassName())
                                .instructorId(instructor.getId())
                                .instructorName(instructor.getFullName())
                                .startDate(clazz.getStartDate())
                                .endDate(clazz.getEndDate())
                                .maxStudents(clazz.getMaxStudents())
                                .isActive(clazz.getIsActive())
                                .createdAt(clazz.getCreatedAt())
                                .updatedAt(clazz.getUpdatedAt())
                                // contests
                                .contests(
                                                clazz.getActiveContests().stream()
                                                                .map(this::mapContestToResponse)
                                                                .toList())
                                .build();
        }

        private ClassContestResponse mapContestToResponse(ClassContest classContest) {
                return ClassContestResponse.builder()
                                .classContestId(classContest.getClassContestId())
                                .scheduledEndTime(classContest.getScheduledEndTime())
                                .scheduledStartTime(classContest.getScheduledStartTime())
                                .durationInMinutes(classContest.getDurationInMinutes())
                                .status(classContest.getStatus())
                                .isActive(classContest.getIsActive())
                                .weight(classContest.getWeight())
                                .createdAt(classContest.getCreatedAt())
                                .updatedAt(classContest.getUpdatedAt())
                                .build();
        }

}
