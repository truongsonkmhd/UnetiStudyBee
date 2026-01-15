package com.truongsonkmhd.unetistudy.sevice.impl;

import com.github.slugify.Slugify;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.CourseLessonResponse;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonRequest;
import com.truongsonkmhd.unetistudy.exception.ResourceNotFoundException;
import com.truongsonkmhd.unetistudy.exception.payload.DataNotFoundException;
import com.truongsonkmhd.unetistudy.mapper.lesson.CourseLessonRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.lesson.CourseLessonResponseMapper;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;
import com.truongsonkmhd.unetistudy.model.course.CourseModule;
import com.truongsonkmhd.unetistudy.repository.CourseLessonRepository;
import com.truongsonkmhd.unetistudy.repository.CourseModuleRepository;
import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.sevice.CourseLessonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseLessonServiceImpl implements CourseLessonService {

    private final CourseLessonRepository courseLessonRepository;

    private final CourseLessonResponseMapper courseLessonResponseMapper;

    private final CourseLessonRequestMapper courseLessonRequestMapper;

    private final CourseModuleRepository courseModuleRepository;

    private final UserRepository userRepository;

    @Override
    public List<CourseLessonResponse> getLessonByModuleId(UUID moduleId) {
        return courseLessonResponseMapper.toDto(courseLessonRepository.getLessonByModuleId(moduleId)) ;
    }

    @Override
    public List<CourseLessonResponse> getLessonByModuleIDAndSlug(UUID moduleID, String slug) {
        return courseLessonResponseMapper.toDto(courseLessonRepository.getLessonByModuleIdAndSlug(moduleID,slug));
    }

    @Override
    public List<CourseLessonResponse> getCodingContest(UUID moduleID) {
        return courseLessonResponseMapper.toDto(courseLessonRepository.getCodingContest(moduleID));
    }

    @Override
    public List<CourseLessonResponse> getMultipleChoiceContest(UUID moduleID) {
        return courseLessonResponseMapper.toDto(courseLessonRepository.getMultipleChoiceContest(moduleID));
    }

    @Override
    public List<CourseLessonResponse> getLessonAll() {
        var listCourseLesson = courseLessonRepository.findAll();
        return courseLessonResponseMapper.toDto(listCourseLesson);
    }

    @Override
    @Transactional
    public CourseLessonResponse addLesson(LessonRequest request) {
        CourseModule existsCourseModule = courseModuleRepository
                .findById(request.getModuleId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "existsCourseModule not found" + request.getModuleId()
                        )
                );

        User user = userRepository.findById(request.getCreatorId()) .orElseThrow(() ->
                new DataNotFoundException(
                        "User not found" + request.getModuleId()
                )
        );;

        CourseLesson courseLesson = courseLessonRequestMapper.toEntity(request);
        courseLesson.setCreator(user);
        courseLesson.setModule(existsCourseModule);

        String baseSlug = new Slugify().slugify(request.getTitle());
        String uniqueSlug = generateUniqueSlug(baseSlug);
        courseLesson.setSlug(uniqueSlug);
        courseLessonRepository.save(courseLesson);
        return courseLessonResponseMapper.toDto(courseLesson);
    }

    @Override
    @Transactional
    public CourseLessonResponse update(UUID theId, LessonRequest request) {
        CourseLesson existing = courseLessonRepository.findById(theId).orElseThrow(() -> new ResourceNotFoundException("CourseLesson not found with id = " + theId));
        courseLessonRequestMapper.partialUpdate(existing, request);
        CourseLesson updated = courseLessonRepository.save(existing);
        return courseLessonResponseMapper.toDto(updated);
    }

    @Override
    public UUID delete(UUID theId) {
        courseLessonRepository.deleteById(theId);
        return theId;
    }

    public String generateUniqueSlug(String baseSlug) {
        String slug= baseSlug;
        int counter =1;
        while (courseLessonRepository.existsBySlug(slug)){
            slug = baseSlug + "-" + counter;
            counter++;
        }
        return slug;
    }
}
