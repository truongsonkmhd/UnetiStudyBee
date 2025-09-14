package com.truongsonkmhd.unetistudy.mapper.lesson;

import com.truongsonkmhd.unetistudy.dto.custom.response.lesson.LessonResponse;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-14T15:45:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class CourseLessonResponseMapperImpl implements CourseLessonResponseMapper {

    @Override
    public CourseLesson toEntity(LessonResponse dto) {
        if ( dto == null ) {
            return null;
        }

        CourseLesson.CourseLessonBuilder courseLesson = CourseLesson.builder();

        courseLesson.title( dto.getTitle() );
        courseLesson.description( dto.getDescription() );
        courseLesson.type( dto.getType() );
        courseLesson.duration( dto.getDuration() );
        courseLesson.isPreview( dto.getIsPreview() );

        return courseLesson.build();
    }

    @Override
    public LessonResponse toDto(CourseLesson entity) {
        if ( entity == null ) {
            return null;
        }

        LessonResponse.LessonResponseBuilder lessonResponse = LessonResponse.builder();

        lessonResponse.title( entity.getTitle() );
        lessonResponse.description( entity.getDescription() );
        lessonResponse.type( entity.getType() );
        lessonResponse.duration( entity.getDuration() );
        lessonResponse.isPreview( entity.getIsPreview() );

        return lessonResponse.build();
    }

    @Override
    public List<CourseLesson> toEntity(List<LessonResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CourseLesson> list = new ArrayList<CourseLesson>( dtoList.size() );
        for ( LessonResponse lessonResponse : dtoList ) {
            list.add( toEntity( lessonResponse ) );
        }

        return list;
    }

    @Override
    public List<LessonResponse> toDto(List<CourseLesson> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<LessonResponse> list = new ArrayList<LessonResponse>( entityList.size() );
        for ( CourseLesson courseLesson : entityList ) {
            list.add( toDto( courseLesson ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(CourseLesson entity, LessonResponse dto) {
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
        if ( dto.getDuration() != null ) {
            entity.setDuration( dto.getDuration() );
        }
        if ( dto.getIsPreview() != null ) {
            entity.setIsPreview( dto.getIsPreview() );
        }
    }
}
