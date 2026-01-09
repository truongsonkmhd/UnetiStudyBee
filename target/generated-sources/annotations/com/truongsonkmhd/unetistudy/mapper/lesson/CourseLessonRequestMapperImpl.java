package com.truongsonkmhd.unetistudy.mapper.lesson;

import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonRequest;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
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
        courseLesson.isContest( dto.getIsContest() );
        courseLesson.totalPoints( dto.getTotalPoints() );
        courseLesson.contestStartTime( dto.getContestStartTime() );
        courseLesson.contestEndTime( dto.getContestEndTime() );

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
        lessonRequest.isContest( entity.getIsContest() );
        lessonRequest.totalPoints( entity.getTotalPoints() );
        lessonRequest.contestStartTime( entity.getContestStartTime() );
        lessonRequest.contestEndTime( entity.getContestEndTime() );

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
    public Set<CourseLesson> toEntity(Set<LessonRequest> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<CourseLesson> set = new LinkedHashSet<CourseLesson>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( LessonRequest lessonRequest : dtoSet ) {
            set.add( toEntity( lessonRequest ) );
        }

        return set;
    }

    @Override
    public Set<LessonRequest> toDto(Set<CourseLesson> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<LessonRequest> set = new LinkedHashSet<LessonRequest>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CourseLesson courseLesson : entitySet ) {
            set.add( toDto( courseLesson ) );
        }

        return set;
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
        if ( dto.getIsContest() != null ) {
            entity.setIsContest( dto.getIsContest() );
        }
        if ( dto.getTotalPoints() != null ) {
            entity.setTotalPoints( dto.getTotalPoints() );
        }
        if ( dto.getContestStartTime() != null ) {
            entity.setContestStartTime( dto.getContestStartTime() );
        }
        if ( dto.getContestEndTime() != null ) {
            entity.setContestEndTime( dto.getContestEndTime() );
        }
    }
}
