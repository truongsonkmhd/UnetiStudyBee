package com.truongsonkmhd.unetistudy.sevice.impl;

import com.github.slugify.Slugify;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.*;
import com.truongsonkmhd.unetistudy.mapper.coding_submission.LessonShowMapper;
import com.truongsonkmhd.unetistudy.model.course.CourseModule;
import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;
import com.truongsonkmhd.unetistudy.repository.CourseModuleRepository;
import com.truongsonkmhd.unetistudy.repository.LessonRepository;
import com.truongsonkmhd.unetistudy.sevice.LessonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseModuleRepository courseModuleRepository;
    private final LessonShowMapper lessonShowMapper;
    private final Slugify slugify;

    @Override
    public List<LessonShowDTO> getLessonShowDTO(UUID theID) {
        return lessonShowMapper.toDto(lessonRepository.getLessonShowDTO(theID));
    }

    @Override
    public List<LessonShowDTO> getLessonShowDTOByModuleIDAndSlug(UUID moduleID, String search) {
        String newSlug = normalizeSlugInput(search);
        return lessonShowMapper.toDto(
                lessonRepository.getLessonShowDTOByModuleIDAndSlug(moduleID, newSlug)
        );
    }

    @Override
    public List<ContestShowDTO> getContestShowDTOByIsContest(UUID moduleID) {
        return lessonRepository.getContestShowDTOByIsContest(moduleID);
    }

    @Override
    public List<ContestShowDTO> getEssayContestShowDTOByIsContest(UUID moduleID) {
        // TODO: implement repository query tương ứng
        return List.of();
    }

    @Override
    @Transactional
    public CourseLesson save(CourseLesson theLesson) {
        // nếu update lesson cũ thì không nên đổi slug (tuỳ business)
        if (theLesson.getLessonId() == null) {
            String uniqueSlug = generateUniqueSlug(slugify.slugify(theLesson.getTitle()));
            theLesson.setSlug(uniqueSlug);
        }
        return lessonRepository.save(theLesson);
    }

    @Override
    public EditLessonDTO getEditLessonDTO(UUID moduleID, String theSlug) {
        return lessonRepository.getEditLessonDTO(moduleID, theSlug);
    }

    @Override
    public Optional<CourseLesson> findById(UUID id) {
        return lessonRepository.findById(id);
    }

    @Override
    public List<LessonShowDTOA> getLessonShowDTOA() {
        // TODO: implement theo quyền như comment (hoặc move sang lớp khác)
        return List.of();
    }

    @Override
    @Transactional
    public CourseLesson addLesson(CourseCreateLessonsDTO dto) {
        // Validate tối thiểu (tuỳ bạn có dùng validation annotations hay không)
        if (dto == null || dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title không được để trống");
        }
        if (dto.getCourseName() == null || dto.getCourseName().isBlank()) {
            throw new IllegalArgumentException("CourseName không được để trống");
        }

        CourseModule module = courseModuleRepository.findBySlug(dto.getCourseName())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy module"));

        CourseLesson courseLesson = new CourseLesson();
        courseLesson.setModule(module);
        courseLesson.setTitle(dto.getTitle());
        courseLesson.setDescription(dto.getDescription());
        courseLesson.setContent(dto.getContent());
        courseLesson.setDuration(dto.getDuration());
        courseLesson.setOrderIndex(dto.getOrderIndex());

        String uniqueSlug = generateUniqueSlug(slugify.slugify(dto.getTitle()));
        courseLesson.setSlug(uniqueSlug);

        return lessonRepository.save(courseLesson);
    }

    @Override
    public List<ContestManagementShowDTO> getContestManagementShowDTO(UUID moduleID, String userName) {
        return lessonRepository.getContestManagementShowDTO(moduleID, userName);
    }

    // =========================
    // Helpers
    // =========================

    /**
     * Normalize input slug, đặc biệt case C++ / ++
     */
    private String normalizeSlugInput(String raw) {
        if (raw == null) return null;
        String processed = raw.replace("++", "-plus-plus");
        return slugify.slugify(processed);
    }

    /**
     * Best-effort unique slug by checking DB.
     * Nên kết hợp thêm UNIQUE constraint ở DB để chống race-condition.
     */
    private String generateUniqueSlug(String baseSlug) {
        String slug = baseSlug;
        int counter = 1;

        while (lessonRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        return slug;
    }
}
