package com.truongsonkmhd.unetistudy.sevice.impl;

import com.github.slugify.Slugify;
import com.truongsonkmhd.unetistudy.dto.custom.request.course.CourseModuleRequest;
import com.truongsonkmhd.unetistudy.dto.custom.response.course.CourseModuleResponse;
import com.truongsonkmhd.unetistudy.dto.custom.request.course.CourseShowRequest;
import com.truongsonkmhd.unetistudy.dto.custom.response.course.CourseShowResponse;
import com.truongsonkmhd.unetistudy.exception.payload.DataNotFoundException;
import com.truongsonkmhd.unetistudy.mapper.course.CourseModuleRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.course.CourseModuleResponseMapper;
import com.truongsonkmhd.unetistudy.mapper.course.CourseRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.course.CourseResponseMapper;
import com.truongsonkmhd.unetistudy.model.Course;
import com.truongsonkmhd.unetistudy.model.CourseModule;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.repository.CourseModuleRepository;
import com.truongsonkmhd.unetistudy.repository.CourseRepository;
import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.sevice.CourseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final CourseResponseMapper courseResponseMapper;
    private final CourseRequestMapper courseRequestMapper;

    private final CourseModuleRequestMapper courseModuleRequestMapper;

    private final CourseModuleResponseMapper courseModuleResponseMapper;


    private final CourseModuleRepository courseModuleRepository;

    @Override
    public CourseShowResponse findById(UUID theId) {
        Course course = courseRepository.findById(theId).orElseThrow(() -> new RuntimeException("Course not found with id =" + theId));
        return courseResponseMapper.toDto(course);
    }

    @Override
    @Transactional
    public CourseShowResponse save(CourseShowRequest req) {
        User existUser = userRepository
                .findById(req.getInstructorId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "existsCategory not found" + req.getInstructorId()
                        )
                );
        var course = courseRequestMapper.toEntity(req);
        course.setInstructor(existUser);


        String baseSlug = new Slugify().slugify(req.getTitle());
        String uniqueSlug = generateUniqueSlug(baseSlug);
        course.setSlug(uniqueSlug);

        course.setModules(convertToCourseModule(req.getModules(),course));

        courseRepository.save(course);
        return courseResponseMapper.toDto(course);
    }

    private List<CourseModule> convertToCourseModule(List<CourseModuleRequest> courseModules, Course course) {
        List<CourseModule> result = new ArrayList<>();
        courseModules.forEach(a -> {
            var courseModule= courseModuleRequestMapper.toEntity(a);
            courseModule.setCourse(course);
            result.add(courseModule);
        });
        return result;
    }

    @Override
    @Transactional
    public UUID deleteById(UUID theId) {
        courseRepository.deleteById(theId);
        return theId;
    }

    @Override
    public List<CourseShowResponse> getCourseShowDTO() {
        return courseRepository.findAll()
                .stream()
                .map(courseResponseMapper::toDto)
                .toList();
    }

    @Override
    public List<CourseModuleResponse> getCourseModuleByCourseSlug(String theSlug) {
        var test = courseModuleRepository.getCourseModuleByCourseSlug(theSlug);
        System.out.println("LISTCOUNT" + test);
        return courseModuleResponseMapper.toDto(courseModuleRepository.getCourseModuleByCourseSlug(theSlug));
    }

    @Override
    public String generateUniqueSlug(String baseSlug) {
        String slug= baseSlug;
        int counter =1;
        while (courseRepository.existsBySlug(slug)){
            slug = baseSlug + "-" + counter;
            counter++;
        }
        return slug;
    }
}
