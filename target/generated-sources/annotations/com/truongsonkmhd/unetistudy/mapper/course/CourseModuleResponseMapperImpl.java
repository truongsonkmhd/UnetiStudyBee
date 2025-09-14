package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.custom.response.course.CourseModuleResponse;
import com.truongsonkmhd.unetistudy.dto.custom.response.lesson.LessonResponse;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import com.truongsonkmhd.unetistudy.model.CourseModule;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-15T00:30:47+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class CourseModuleResponseMapperImpl implements CourseModuleResponseMapper {

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
        if ( dto.getCreatedAt() != null ) {
            courseModule.createdAt( Date.from( dto.getCreatedAt().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( dto.getUpdatedAt() != null ) {
            courseModule.updatedAt( Date.from( dto.getUpdatedAt().toInstant( ZoneOffset.UTC ) ) );
        }

        return courseModule.build();
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
        if ( entity.getCreatedAt() != null ) {
            courseModuleResponse.createdAt( LocalDateTime.ofInstant( entity.getCreatedAt().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( entity.getUpdatedAt() != null ) {
            courseModuleResponse.updatedAt( LocalDateTime.ofInstant( entity.getUpdatedAt().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        courseModuleResponse.lessons( courseLessonListToLessonResponseList( entity.getLessons() ) );

        return courseModuleResponse.build();
    }

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
            entity.setCreatedAt( Date.from( dto.getCreatedAt().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( dto.getUpdatedAt() != null ) {
            entity.setUpdatedAt( Date.from( dto.getUpdatedAt().toInstant( ZoneOffset.UTC ) ) );
        }
    }

    protected CourseLesson lessonResponseToCourseLesson(LessonResponse lessonResponse) {
        if ( lessonResponse == null ) {
            return null;
        }

        CourseLesson.CourseLessonBuilder courseLesson = CourseLesson.builder();

        courseLesson.title( lessonResponse.getTitle() );
        courseLesson.description( lessonResponse.getDescription() );
        courseLesson.type( lessonResponse.getType() );
        courseLesson.duration( lessonResponse.getDuration() );
        courseLesson.isPreview( lessonResponse.getIsPreview() );

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
        lessonResponse.isPreview( courseLesson.getIsPreview() );

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
