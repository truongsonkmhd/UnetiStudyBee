package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonShowDTO;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import com.truongsonkmhd.unetistudy.model.CourseModule;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-12T15:37:22+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Eclipse Adoptium)"
)
@Component
public class LessonShowMapperImpl implements LessonShowMapper {

    @Override
    public List<CourseLesson> toEntity(List<LessonShowDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CourseLesson> list = new ArrayList<CourseLesson>( dtoList.size() );
        for ( LessonShowDTO lessonShowDTO : dtoList ) {
            list.add( toEntity( lessonShowDTO ) );
        }

        return list;
    }

    @Override
    public List<LessonShowDTO> toDto(List<CourseLesson> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<LessonShowDTO> list = new ArrayList<LessonShowDTO>( entityList.size() );
        for ( CourseLesson courseLesson : entityList ) {
            list.add( toDto( courseLesson ) );
        }

        return list;
    }

    @Override
    public Set<CourseLesson> toEntity(Set<LessonShowDTO> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<CourseLesson> set = new LinkedHashSet<CourseLesson>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( LessonShowDTO lessonShowDTO : dtoSet ) {
            set.add( toEntity( lessonShowDTO ) );
        }

        return set;
    }

    @Override
    public Set<LessonShowDTO> toDto(Set<CourseLesson> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<LessonShowDTO> set = new LinkedHashSet<LessonShowDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CourseLesson courseLesson : entitySet ) {
            set.add( toDto( courseLesson ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(CourseLesson entity, LessonShowDTO dto) {
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
        if ( dto.getImage() != null ) {
            entity.setImage( dto.getImage() );
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
        if ( dto.getSlug() != null ) {
            entity.setSlug( dto.getSlug() );
        }
    }

    @Override
    public LessonShowDTO toDto(CourseLesson entity) {
        if ( entity == null ) {
            return null;
        }

        LessonShowDTO.LessonShowDTOBuilder lessonShowDTO = LessonShowDTO.builder();

        lessonShowDTO.lessonID( entity.getLessonId() );
        lessonShowDTO.moduleID( entityModuleModuleId( entity ) );
        lessonShowDTO.title( entity.getTitle() );
        lessonShowDTO.description( entity.getDescription() );
        lessonShowDTO.type( entity.getType() );
        lessonShowDTO.content( entity.getContent() );
        lessonShowDTO.image( entity.getImage() );
        lessonShowDTO.duration( entity.getDuration() );
        lessonShowDTO.orderIndex( entity.getOrderIndex() );
        lessonShowDTO.isPreview( entity.getIsPreview() );
        lessonShowDTO.isPublished( entity.getIsPublished() );
        lessonShowDTO.slug( entity.getSlug() );

        return lessonShowDTO.build();
    }

    @Override
    public CourseLesson toEntity(LessonShowDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CourseLesson.CourseLessonBuilder courseLesson = CourseLesson.builder();

        courseLesson.module( lessonShowDTOToCourseModule( dto ) );
        courseLesson.lessonId( dto.getLessonID() );
        courseLesson.title( dto.getTitle() );
        courseLesson.description( dto.getDescription() );
        courseLesson.type( dto.getType() );
        courseLesson.content( dto.getContent() );
        courseLesson.image( dto.getImage() );
        courseLesson.duration( dto.getDuration() );
        courseLesson.orderIndex( dto.getOrderIndex() );
        courseLesson.isPreview( dto.getIsPreview() );
        courseLesson.isPublished( dto.getIsPublished() );
        courseLesson.slug( dto.getSlug() );

        return courseLesson.build();
    }

    private UUID entityModuleModuleId(CourseLesson courseLesson) {
        if ( courseLesson == null ) {
            return null;
        }
        CourseModule module = courseLesson.getModule();
        if ( module == null ) {
            return null;
        }
        UUID moduleId = module.getModuleId();
        if ( moduleId == null ) {
            return null;
        }
        return moduleId;
    }

    protected CourseModule lessonShowDTOToCourseModule(LessonShowDTO lessonShowDTO) {
        if ( lessonShowDTO == null ) {
            return null;
        }

        CourseModule.CourseModuleBuilder courseModule = CourseModule.builder();

        courseModule.moduleId( lessonShowDTO.getModuleID() );

        return courseModule.build();
    }
}
