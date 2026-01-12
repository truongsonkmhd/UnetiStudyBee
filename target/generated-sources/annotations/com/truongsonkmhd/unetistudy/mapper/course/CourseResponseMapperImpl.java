package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseShowResponse;
import com.truongsonkmhd.unetistudy.model.Course;
import com.truongsonkmhd.unetistudy.model.CourseModule;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-12T15:37:21+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Eclipse Adoptium)"
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
    public Set<Course> toEntity(Set<CourseShowResponse> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<Course> set = new LinkedHashSet<Course>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( CourseShowResponse courseShowResponse : dtoSet ) {
            set.add( toEntity( courseShowResponse ) );
        }

        return set;
    }

    @Override
    public Set<CourseShowResponse> toDto(Set<Course> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CourseShowResponse> set = new LinkedHashSet<CourseShowResponse>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( Course course : entitySet ) {
            set.add( toDto( course ) );
        }

        return set;
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
