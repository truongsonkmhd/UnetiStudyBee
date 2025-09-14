package com.truongsonkmhd.unetistudy.sevice.impl;

import com.truongsonkmhd.unetistudy.dto.custom.request.lesson.LessonRequest;
import com.truongsonkmhd.unetistudy.dto.custom.response.lesson.LessonResponse;
import com.truongsonkmhd.unetistudy.exception.ResourceNotFoundException;
import com.truongsonkmhd.unetistudy.exception.payload.DataNotFoundException;
import com.truongsonkmhd.unetistudy.mapper.lesson.CourseLessonRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.lesson.CourseLessonResponseMapper;
import com.truongsonkmhd.unetistudy.model.Course;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import com.truongsonkmhd.unetistudy.model.CourseModule;
import com.truongsonkmhd.unetistudy.repository.CourseLessonRepository;
import com.truongsonkmhd.unetistudy.repository.CourseModuleRepository;
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

    @Override
    public List<LessonResponse> getLessonById(Long theID) {
        return null;
    }

    @Override
    public List<LessonResponse> getLessonShowDTOByModuleIDAndSlug(Long moduleID, String search) {
        return null;
    }

    @Override
    public List<LessonResponse> getContestShowDTOByIsContest(Long moduleID) {
        return null;
    }

    @Override
    public List<LessonResponse> getEssayContestShowDTOByIsContest(Long moduleID) {
        return null;
    }

    @Override
    public List<LessonResponse> getLessonAll() {
        var listCourseLesson = courseLessonRepository.findAll();
        return courseLessonResponseMapper.toDto(listCourseLesson);
    }

    @Override
    @Transactional
    public LessonResponse addLesson(LessonRequest request) {
        CourseModule existsCourseModule = courseModuleRepository
                .findById(request.getModuleId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "existsCourseModule not found" + request.getModuleId()
                        )
                );
        CourseLesson courseLesson = courseLessonRequestMapper.toEntity(request);
        courseLesson.setModule(existsCourseModule);
        courseLessonRepository.save(courseLesson);
        return courseLessonResponseMapper.toDto(courseLesson);
    }

    @Override
    @Transactional
    public LessonResponse update(UUID theId, LessonRequest request) {
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
}
