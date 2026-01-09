package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseModuleRequest;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseShowRequest;
import com.truongsonkmhd.unetistudy.model.Course;
import com.truongsonkmhd.unetistudy.model.CourseModule;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-09T15:05:04+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class CourseRequestMapperImpl implements CourseRequestMapper {

    @Override
    public CourseShowRequest toDto(Course entity) {
        if ( entity == null ) {
            return null;
        }

        CourseShowRequest.CourseShowRequestBuilder courseShowRequest = CourseShowRequest.builder();

        courseShowRequest.title( entity.getTitle() );
        courseShowRequest.description( entity.getDescription() );
        courseShowRequest.shortDescription( entity.getShortDescription() );
        courseShowRequest.level( entity.getLevel() );
        courseShowRequest.category( entity.getCategory() );
        courseShowRequest.subCategory( entity.getSubCategory() );
        courseShowRequest.language( entity.getLanguage() );
        courseShowRequest.duration( entity.getDuration() );
        courseShowRequest.capacity( entity.getCapacity() );
        courseShowRequest.price( entity.getPrice() );
        courseShowRequest.discountPrice( entity.getDiscountPrice() );
        courseShowRequest.imageUrl( entity.getImageUrl() );
        courseShowRequest.videoUrl( entity.getVideoUrl() );
        courseShowRequest.requirements( entity.getRequirements() );
        courseShowRequest.objectives( entity.getObjectives() );
        courseShowRequest.syllabus( entity.getSyllabus() );
        courseShowRequest.isPublished( entity.getIsPublished() );
        courseShowRequest.modules( courseModuleListToCourseModuleRequestList( entity.getModules() ) );

        return courseShowRequest.build();
    }

    @Override
    public List<Course> toEntity(List<CourseShowRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Course> list = new ArrayList<Course>( dtoList.size() );
        for ( CourseShowRequest courseShowRequest : dtoList ) {
            list.add( toEntity( courseShowRequest ) );
        }

        return list;
    }

    @Override
    public List<CourseShowRequest> toDto(List<Course> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CourseShowRequest> list = new ArrayList<CourseShowRequest>( entityList.size() );
        for ( Course course : entityList ) {
            list.add( toDto( course ) );
        }

        return list;
    }

    @Override
    public Set<Course> toEntity(Set<CourseShowRequest> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<Course> set = new LinkedHashSet<Course>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( CourseShowRequest courseShowRequest : dtoSet ) {
            set.add( toEntity( courseShowRequest ) );
        }

        return set;
    }

    @Override
    public Set<CourseShowRequest> toDto(Set<Course> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CourseShowRequest> set = new LinkedHashSet<CourseShowRequest>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( Course course : entitySet ) {
            set.add( toDto( course ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(Course entity, CourseShowRequest dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getShortDescription() != null ) {
            entity.setShortDescription( dto.getShortDescription() );
        }
        if ( entity.getModules() != null ) {
            List<CourseModule> list = courseModuleRequestListToCourseModuleList( dto.getModules() );
            if ( list != null ) {
                entity.getModules().clear();
                entity.getModules().addAll( list );
            }
        }
        else {
            List<CourseModule> list = courseModuleRequestListToCourseModuleList( dto.getModules() );
            if ( list != null ) {
                entity.setModules( list );
            }
        }
        if ( dto.getLevel() != null ) {
            entity.setLevel( dto.getLevel() );
        }
        if ( dto.getCategory() != null ) {
            entity.setCategory( dto.getCategory() );
        }
        if ( dto.getSubCategory() != null ) {
            entity.setSubCategory( dto.getSubCategory() );
        }
        if ( dto.getLanguage() != null ) {
            entity.setLanguage( dto.getLanguage() );
        }
        if ( dto.getDuration() != null ) {
            entity.setDuration( dto.getDuration() );
        }
        if ( dto.getCapacity() != null ) {
            entity.setCapacity( dto.getCapacity() );
        }
        if ( dto.getPrice() != null ) {
            entity.setPrice( dto.getPrice() );
        }
        if ( dto.getDiscountPrice() != null ) {
            entity.setDiscountPrice( dto.getDiscountPrice() );
        }
        if ( dto.getImageUrl() != null ) {
            entity.setImageUrl( dto.getImageUrl() );
        }
        if ( dto.getVideoUrl() != null ) {
            entity.setVideoUrl( dto.getVideoUrl() );
        }
        if ( dto.getRequirements() != null ) {
            entity.setRequirements( dto.getRequirements() );
        }
        if ( dto.getObjectives() != null ) {
            entity.setObjectives( dto.getObjectives() );
        }
        if ( dto.getSyllabus() != null ) {
            entity.setSyllabus( dto.getSyllabus() );
        }
        if ( dto.getIsPublished() != null ) {
            entity.setIsPublished( dto.getIsPublished() );
        }
    }

    @Override
    public Course toEntity(CourseShowRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.title( dto.getTitle() );
        course.description( dto.getDescription() );
        course.shortDescription( dto.getShortDescription() );
        course.modules( courseModuleRequestListToCourseModuleList( dto.getModules() ) );
        course.level( dto.getLevel() );
        course.category( dto.getCategory() );
        course.subCategory( dto.getSubCategory() );
        course.language( dto.getLanguage() );
        course.duration( dto.getDuration() );
        course.capacity( dto.getCapacity() );
        course.price( dto.getPrice() );
        course.discountPrice( dto.getDiscountPrice() );
        course.imageUrl( dto.getImageUrl() );
        course.videoUrl( dto.getVideoUrl() );
        course.requirements( dto.getRequirements() );
        course.objectives( dto.getObjectives() );
        course.syllabus( dto.getSyllabus() );
        course.isPublished( dto.getIsPublished() );

        return course.build();
    }

    protected CourseModuleRequest courseModuleToCourseModuleRequest(CourseModule courseModule) {
        if ( courseModule == null ) {
            return null;
        }

        CourseModuleRequest.CourseModuleRequestBuilder courseModuleRequest = CourseModuleRequest.builder();

        courseModuleRequest.title( courseModule.getTitle() );
        courseModuleRequest.description( courseModule.getDescription() );
        courseModuleRequest.orderIndex( courseModule.getOrderIndex() );
        courseModuleRequest.duration( courseModule.getDuration() );
        courseModuleRequest.isPublished( courseModule.getIsPublished() );

        return courseModuleRequest.build();
    }

    protected List<CourseModuleRequest> courseModuleListToCourseModuleRequestList(List<CourseModule> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseModuleRequest> list1 = new ArrayList<CourseModuleRequest>( list.size() );
        for ( CourseModule courseModule : list ) {
            list1.add( courseModuleToCourseModuleRequest( courseModule ) );
        }

        return list1;
    }

    protected CourseModule courseModuleRequestToCourseModule(CourseModuleRequest courseModuleRequest) {
        if ( courseModuleRequest == null ) {
            return null;
        }

        CourseModule.CourseModuleBuilder courseModule = CourseModule.builder();

        courseModule.title( courseModuleRequest.getTitle() );
        courseModule.description( courseModuleRequest.getDescription() );
        courseModule.orderIndex( courseModuleRequest.getOrderIndex() );
        courseModule.duration( courseModuleRequest.getDuration() );
        courseModule.isPublished( courseModuleRequest.getIsPublished() );

        return courseModule.build();
    }

    protected List<CourseModule> courseModuleRequestListToCourseModuleList(List<CourseModuleRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseModule> list1 = new ArrayList<CourseModule>( list.size() );
        for ( CourseModuleRequest courseModuleRequest : list ) {
            list1.add( courseModuleRequestToCourseModule( courseModuleRequest ) );
        }

        return list1;
    }
}
