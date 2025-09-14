package com.truongsonkmhd.unetistudy.mapper.lesson;

import com.truongsonkmhd.unetistudy.dto.custom.request.lesson.LessonRequest;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-14T20:55:41+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class CourseLessonRequestMapperImpl implements CourseLessonRequestMapper {

    @Override
    public CourseLesson toEntity(LessonRequest dto) {
        if ( dto == null ) {
            return null;
        }

        CourseLesson.CourseLessonBuilder courseLesson = CourseLesson.builder();

        courseLesson.title( dto.getTitle() );
        courseLesson.description( dto.getDescription() );
        courseLesson.type( dto.getType() );
        courseLesson.content( dto.getContent() );
        courseLesson.videoUrl( dto.getVideoUrl() );
        courseLesson.duration( dto.getDuration() );
        courseLesson.orderIndex( dto.getOrderIndex() );
        courseLesson.isPreview( dto.getIsPreview() );
        courseLesson.isPublished( dto.getIsPublished() );

        return courseLesson.build();
    }

    @Override
    public LessonRequest toDto(CourseLesson entity) {
        if ( entity == null ) {
            return null;
        }

        LessonRequest.LessonRequestBuilder lessonRequest = LessonRequest.builder();

        lessonRequest.title( entity.getTitle() );
        lessonRequest.description( entity.getDescription() );
        lessonRequest.type( entity.getType() );
        lessonRequest.content( entity.getContent() );
        lessonRequest.videoUrl( entity.getVideoUrl() );
        lessonRequest.duration( entity.getDuration() );
        lessonRequest.orderIndex( entity.getOrderIndex() );
        lessonRequest.isPreview( entity.getIsPreview() );
        lessonRequest.isPublished( entity.getIsPublished() );

        return lessonRequest.build();
    }

    @Override
    public List<CourseLesson> toEntity(List<LessonRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CourseLesson> list = new ArrayList<CourseLesson>( dtoList.size() );
        for ( LessonRequest lessonRequest : dtoList ) {
            list.add( toEntity( lessonRequest ) );
        }

        return list;
    }

    @Override
    public List<LessonRequest> toDto(List<CourseLesson> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<LessonRequest> list = new ArrayList<LessonRequest>( entityList.size() );
        for ( CourseLesson courseLesson : entityList ) {
            list.add( toDto( courseLesson ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(CourseLesson entity, LessonRequest dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getType() != null ) {
            entity.setType( dto.getType() );
        }
        if ( dto.getContent() != null ) {
            entity.setContent( dto.getContent() );
        }
        if ( dto.getVideoUrl() != null ) {
            entity.setVideoUrl( dto.getVideoUrl() );
        }
        if ( dto.getDuration() != null ) {
            entity.setDuration( dto.getDuration() );
        }
        if ( dto.getOrderIndex() != null ) {
            entity.setOrderIndex( dto.getOrderIndex() );
        }
        if ( dto.getIsPreview() != null ) {
            entity.setIsPreview( dto.getIsPreview() );
        }
        if ( dto.getIsPublished() != null ) {
            entity.setIsPublished( dto.getIsPublished() );
        }
    }
}
