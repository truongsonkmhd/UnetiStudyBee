package com.truongsonkmhd.unetistudy.mapper.lesson;

import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonResponse;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
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
    date = "2026-01-12T15:37:22+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Eclipse Adoptium)"
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
        courseLesson.image( dto.getImage() );
        courseLesson.duration( dto.getDuration() );
        courseLesson.isPreview( dto.getIsPreview() );
        courseLesson.slug( dto.getSlug() );
        if ( dto.getContestStartTime() != null ) {
            courseLesson.contestStartTime( Date.from( dto.getContestStartTime().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( dto.getContestEndTime() != null ) {
            courseLesson.contestEndTime( Date.from( dto.getContestEndTime().toInstant( ZoneOffset.UTC ) ) );
        }

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
        lessonResponse.image( entity.getImage() );
        lessonResponse.isPreview( entity.getIsPreview() );
        lessonResponse.slug( entity.getSlug() );
        if ( entity.getContestStartTime() != null ) {
            lessonResponse.contestStartTime( LocalDateTime.ofInstant( entity.getContestStartTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }
        if ( entity.getContestEndTime() != null ) {
            lessonResponse.contestEndTime( LocalDateTime.ofInstant( entity.getContestEndTime().toInstant(), ZoneId.of( "UTC" ) ) );
        }

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
    public Set<CourseLesson> toEntity(Set<LessonResponse> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<CourseLesson> set = new LinkedHashSet<CourseLesson>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( LessonResponse lessonResponse : dtoSet ) {
            set.add( toEntity( lessonResponse ) );
        }

        return set;
    }

    @Override
    public Set<LessonResponse> toDto(Set<CourseLesson> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<LessonResponse> set = new LinkedHashSet<LessonResponse>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CourseLesson courseLesson : entitySet ) {
            set.add( toDto( courseLesson ) );
        }

        return set;
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
        if ( dto.getImage() != null ) {
            entity.setImage( dto.getImage() );
        }
        if ( dto.getDuration() != null ) {
            entity.setDuration( dto.getDuration() );
        }
        if ( dto.getIsPreview() != null ) {
            entity.setIsPreview( dto.getIsPreview() );
        }
        if ( dto.getSlug() != null ) {
            entity.setSlug( dto.getSlug() );
        }
        if ( dto.getContestStartTime() != null ) {
            entity.setContestStartTime( Date.from( dto.getContestStartTime().toInstant( ZoneOffset.UTC ) ) );
        }
        if ( dto.getContestEndTime() != null ) {
            entity.setContestEndTime( Date.from( dto.getContestEndTime().toInstant( ZoneOffset.UTC ) ) );
        }
    }
}
