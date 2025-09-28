package com.truongsonkmhd.unetistudy.sevice.impl;

import com.github.slugify.Slugify;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.*;
import com.truongsonkmhd.unetistudy.mapper.coding_submission.LessonShowMapper;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import com.truongsonkmhd.unetistudy.model.CourseModule;
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

    @Override
    public List<LessonShowDTO> getLessonShowDTO(UUID theID) {
        return lessonShowMapper.toDto(lessonRepository.getLessonShowDTO(theID));
    }

    @Override
    public List<LessonShowDTO> getLessonShowDTOByModuleIDAndSlug(UUID moduleID, String search) {
        String processed = search.replace("++", "-plus-plus");
        String newSlug = new Slugify().slugify(processed);
        return lessonShowMapper.toDto(lessonRepository.getLessonShowDTOByModuleIDAndSlug(moduleID, newSlug));
    }

    @Override
    public List<ContestShowDTO> getContestShowDTOByIsContest(UUID moduleID) {
        return lessonRepository.getContestShowDTOByIsContest(moduleID);
    }

    @Override
    public List<ContestShowDTO> getEssayContestShowDTOByIsContest(UUID moduleID) {
        return null;
    }

    @Override
    @Transactional
    public CourseLesson save(CourseLesson theLesson) {
        String baseSlug = new Slugify().slugify(theLesson.getTitle());
        String uniqueSlug = generateUniqueSlug(baseSlug);
        theLesson.setSlug(uniqueSlug);
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

    public String generateUniqueSlug(String baseSlug) {
        String slug = baseSlug;
        int counter = 1;
        while (lessonRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }
        return slug;
    }

    @Override
    public List<LessonShowDTOA> getLessonShowDTOA() {
//        String userName = UserContext.getUsername();
//        List<String> roleName = lessonRepository.findRoleNameByUserName(userName);
//        UUID UserID = lessonRepository.findUserIdByUsername(userName);
//        if (roleName.contains("ADMIN")){
//            return lessonRepository.findLessonByRoleName("ADMIN");
//
//        }else {
//            return lessonRepository.findLessonByUserID(UserID);
//        }

        return null;

    }

    @Override
    public CourseLesson addLesson(CreateLessonsDTO dto) {
//        System.out.println(dto);
        Slugify slugify = new Slugify();
        String Slug = slugify.slugify(dto.getTitle());
//        System.out.println(dto.getCourseName());
        String courseName = dto.getCourseName();
        CourseModule module = courseModuleRepository.findBySlug(courseName)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy module"));
//        System.out.println(module);
//        khởi tạo
        CourseLesson courseLesson = new CourseLesson();
        courseLesson.setModule(module);
        courseLesson.setTitle(dto.getTitle());
        courseLesson.setDescription(dto.getDescription());
        courseLesson.setType(dto.getType());
        courseLesson.setContent(dto.getContent());
        courseLesson.setImage(dto.getImage());
        courseLesson.setDuration(dto.getDuration());
        courseLesson.setSlug(Slug);
        courseLesson.setOrderIndex(dto.getOrderIndex());

        lessonRepository.save(courseLesson);

        return courseLesson;
    }

    @Override
    public List<ContestManagementShowDTO> getContestManagementShowDTO(UUID moduleID, String userName) {
        return lessonRepository.getContestManagementShowDTO(moduleID, userName);
    }

}
