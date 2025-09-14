package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.custom.response.course.CourseShowResponse;
import com.truongsonkmhd.unetistudy.model.Course;
import com.truongsonkmhd.unetistudy.model.CourseModule;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-15T00:19:24+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class CourseResponseMapperImpl implements CourseResponseMapper {

    @Autowired
    private CourseModuleResponseMapper courseModuleResponseMapper;

    @Override
    public List<Course> toEntity(List<CourseShowResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Course> list = new ArrayList<Course>( dtoList.size() );
        for ( CourseShowResponse courseShowResponse : dtoList ) {
            list.add( toEntity( courseShowResponse ) );
        }

        return list;
    }

    @Override
    public List<CourseShowResponse> toDto(List<Course> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CourseShowResponse> list = new ArrayList<CourseShowResponse>( entityList.size() );
        for ( Course course : entityList ) {
            list.add( toDto( course ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Course entity, CourseShowResponse dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( entity.getModules() != null ) {
            List<CourseModule> list = courseModuleResponseMapper.toEntity( dto.getModules() );
            if ( list != null ) {
                entity.getModules().clear();
                entity.getModules().addAll( list );
            }
        }
        else {
            List<CourseModule> list = courseModuleResponseMapper.toEntity( dto.getModules() );
            if ( list != null ) {
                entity.setModules( list );
            }
        }
        if ( dto.getDuration() != null ) {
            entity.setDuration( dto.getDuration() );
        }
        if ( dto.getIsPublished() != null ) {
            entity.setIsPublished( dto.getIsPublished() );
        }
    }

    @Override
    public CourseShowResponse toDto(Course entity) {
        if ( entity == null ) {
            return null;
        }

        CourseShowResponse.CourseShowResponseBuilder courseShowResponse = CourseShowResponse.builder();

        courseShowResponse.modules( courseModuleResponseMapper.toDto( entity.getModules() ) );
        courseShowResponse.title( entity.getTitle() );
        courseShowResponse.description( entity.getDescription() );
        courseShowResponse.duration( entity.getDuration() );
        courseShowResponse.isPublished( entity.getIsPublished() );

        return courseShowResponse.build();
    }

    @Override
    public Course toEntity(CourseShowResponse dto) {
        if ( dto == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.title( dto.getTitle() );
        course.description( dto.getDescription() );
        course.modules( courseModuleResponseMapper.toEntity( dto.getModules() ) );
        course.duration( dto.getDuration() );
        course.isPublished( dto.getIsPublished() );

        return course.build();
    }
}
