package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseModuleResponse;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonResponse;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import com.truongsonkmhd.unetistudy.model.CourseModule;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
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
public class CourseModuleResponseMapperImpl implements CourseModuleResponseMapper {

    @Override
    public List<CourseModule> toEntity(List<CourseModuleResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CourseModule> list = new ArrayList<CourseModule>( dtoList.size() );
        for ( CourseModuleResponse courseModuleResponse : dtoList ) {
            list.add( toEntity( courseModuleResponse ) );
        }

        return list;
    }

    @Override
    public List<CourseModuleResponse> toDto(List<CourseModule> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CourseModuleResponse> list = new ArrayList<CourseModuleResponse>( entityList.size() );
        for ( CourseModule courseModule : entityList ) {
            list.add( toDto( courseModule ) );
        }

        return list;
    }

    @Override
    public Set<CourseModule> toEntity(Set<CourseModuleResponse> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<CourseModule> set = new LinkedHashSet<CourseModule>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( CourseModuleResponse courseModuleResponse : dtoSet ) {
            set.add( toEntity( courseModuleResponse ) );
        }

        return set;
    }

    @Override
    public Set<CourseModuleResponse> toDto(Set<CourseModule> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CourseModuleResponse> set = new LinkedHashSet<CourseModuleResponse>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CourseModule courseModule : entitySet ) {
            set.add( toDto( courseModule ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(CourseModule entity, CourseModuleResponse dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getModuleId() != null ) {
            entity.setModuleId( dto.getModuleId() );
        }
        if ( entity.getLessons() != null ) {
            List<CourseLesson> list = lessonResponseListToCourseLessonList( dto.getLessons() );
            if ( list != null ) {
                entity.getLessons().clear();
                entity.getLessons().addAll( list );
            }
        }
        else {
            List<CourseLesson> list = lessonResponseListToCourseLessonList( dto.getLessons() );
            if ( list != null ) {
                entity.setLessons( list );
            }
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getOrderIndex() != null ) {
            entity.setOrderIndex( dto.getOrderIndex() );
        }
        if ( dto.getDuration() != null ) {
            entity.setDuration( dto.getDuration() );
        }
        if ( dto.getIsPublished() != null ) {
            entity.setIsPublished( dto.getIsPublished() );
        }
        if ( dto.getCreatedAt() != null ) {
            entity.setCreatedAt( dto.getCreatedAt() );
        }
        if ( dto.getUpdatedAt() != null ) {
            entity.setUpdatedAt( dto.getUpdatedAt() );
        }
    }

    @Override
    public CourseModuleResponse toDto(CourseModule entity) {
        if ( entity == null ) {
            return null;
        }

        CourseModuleResponse.CourseModuleResponseBuilder courseModuleResponse = CourseModuleResponse.builder();

        courseModuleResponse.moduleId( entity.getModuleId() );
        courseModuleResponse.title( entity.getTitle() );
        courseModuleResponse.description( entity.getDescription() );
        courseModuleResponse.orderIndex( entity.getOrderIndex() );
        courseModuleResponse.duration( entity.getDuration() );
        courseModuleResponse.isPublished( entity.getIsPublished() );
        courseModuleResponse.lessons( courseLessonListToLessonResponseList( entity.getLessons() ) );
        courseModuleResponse.createdAt( entity.getCreatedAt() );
        courseModuleResponse.updatedAt( entity.getUpdatedAt() );

        return courseModuleResponse.build();
    }

    @Override
    public CourseModule toEntity(CourseModuleResponse dto) {
        if ( dto == null ) {
            return null;
        }

        CourseModule.CourseModuleBuilder courseModule = CourseModule.builder();

        courseModule.moduleId( dto.getModuleId() );
        courseModule.lessons( lessonResponseListToCourseLessonList( dto.getLessons() ) );
        courseModule.title( dto.getTitle() );
        courseModule.description( dto.getDescription() );
        courseModule.orderIndex( dto.getOrderIndex() );
        courseModule.duration( dto.getDuration() );
        courseModule.isPublished( dto.getIsPublished() );
        courseModule.createdAt( dto.getCreatedAt() );
        courseModule.updatedAt( dto.getUpdatedAt() );

        return courseModule.build();
    }

    protected CourseLesson lessonResponseToCourseLesson(LessonResponse lessonResponse) {
        if ( lessonResponse == null ) {
            return null;
        }

        CourseLesson.CourseLessonBuilder courseLesson = CourseLesson.builder();

        courseLesson.title( lessonResponse.getTitle() );
        courseLesson.description( lessonResponse.getDescription() );
        courseLesson.type( lessonResponse.getType() );
        courseLesson.image( lessonResponse.getImage() );
        courseLesson.duration( lessonResponse.getDuration() );
        courseLesson.isPreview( lessonResponse.getIsPreview() );
        courseLesson.slug( lessonResponse.getSlug() );
        if ( lessonResponse.getContestStartTime() != null ) {
            courseLesson.contestStartTime( Date.from( lessonResponse.getContestStartTime().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( lessonResponse.getContestEndTime() != null ) {
            courseLesson.contestEndTime( Date.from( lessonResponse.getContestEndTime().toInstant( ZoneOffset.UTC ) ) );
        }

        return courseLesson.build();
    }

    protected List<CourseLesson> lessonResponseListToCourseLessonList(List<LessonResponse> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseLesson> list1 = new ArrayList<CourseLesson>( list.size() );
        for ( LessonResponse lessonResponse : list ) {
            list1.add( lessonResponseToCourseLesson( lessonResponse ) );
        }

        return list1;
    }

    protected LessonResponse courseLessonToLessonResponse(CourseLesson courseLesson) {
        if ( courseLesson == null ) {
            return null;
        }

        LessonResponse.LessonResponseBuilder lessonResponse = LessonResponse.builder();

        lessonResponse.title( courseLesson.getTitle() );
        lessonResponse.description( courseLesson.getDescription() );
        lessonResponse.type( courseLesson.getType() );
        lessonResponse.duration( courseLesson.getDuration() );
        lessonResponse.image( courseLesson.getImage() );
        lessonResponse.isPreview( courseLesson.getIsPreview() );
        lessonResponse.slug( courseLesson.getSlug() );
        if ( courseLesson.getContestStartTime() != null ) {
            lessonResponse.contestStartTime( LocalDateTime.ofInstant( courseLesson.getContestStartTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( courseLesson.getContestEndTime() != null ) {
            lessonResponse.contestEndTime( LocalDateTime.ofInstant( courseLesson.getContestEndTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }

        return lessonResponse.build();
    }

    protected List<LessonResponse> courseLessonListToLessonResponseList(List<CourseLesson> list) {
        if ( list == null ) {
            return null;
        }

        List<LessonResponse> list1 = new ArrayList<LessonResponse>( list.size() );
        for ( CourseLesson courseLesson : list ) {
            list1.add( courseLessonToLessonResponse( courseLesson ) );
        }

        return list1;
    }
}
