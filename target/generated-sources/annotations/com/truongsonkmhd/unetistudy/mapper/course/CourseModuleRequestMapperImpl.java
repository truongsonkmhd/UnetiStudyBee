package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseModuleRequest;
import com.truongsonkmhd.unetistudy.model.CourseModule;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-24T23:01:43+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class CourseModuleRequestMapperImpl implements CourseModuleRequestMapper {

    @Override
    public Set<CourseModule> toEntity(Set<CourseModuleRequest> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<CourseModule> set = new LinkedHashSet<CourseModule>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( CourseModuleRequest courseModuleRequest : dtoSet ) {
            set.add( toEntity( courseModuleRequest ) );
        }

        return set;
    }

    @Override
    public Set<CourseModuleRequest> toDto(Set<CourseModule> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CourseModuleRequest> set = new LinkedHashSet<CourseModuleRequest>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CourseModule courseModule : entitySet ) {
            set.add( toDto( courseModule ) );
        }

        return set;
    }

    @Override
    public CourseModule toEntity(CourseModuleRequest dto) {
        if ( dto == null ) {
            return null;
        }

        CourseModule.CourseModuleBuilder courseModule = CourseModule.builder();

        courseModule.title( dto.getTitle() );
        courseModule.description( dto.getDescription() );
        courseModule.orderIndex( dto.getOrderIndex() );
        courseModule.duration( dto.getDuration() );
        courseModule.isPublished( dto.getIsPublished() );

        return courseModule.build();
    }

    @Override
    public CourseModuleRequest toDto(CourseModule entity) {
        if ( entity == null ) {
            return null;
        }

        CourseModuleRequest.CourseModuleRequestBuilder courseModuleRequest = CourseModuleRequest.builder();

        courseModuleRequest.title( entity.getTitle() );
        courseModuleRequest.description( entity.getDescription() );
        courseModuleRequest.orderIndex( entity.getOrderIndex() );
        courseModuleRequest.duration( entity.getDuration() );
        courseModuleRequest.isPublished( entity.getIsPublished() );

        return courseModuleRequest.build();
    }

    @Override
    public List<CourseModule> toEntity(List<CourseModuleRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CourseModule> list = new ArrayList<CourseModule>( dtoList.size() );
        for ( CourseModuleRequest courseModuleRequest : dtoList ) {
            list.add( toEntity( courseModuleRequest ) );
        }

        return list;
    }

    @Override
    public List<CourseModuleRequest> toDto(List<CourseModule> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CourseModuleRequest> list = new ArrayList<CourseModuleRequest>( entityList.size() );
        for ( CourseModule courseModule : entityList ) {
            list.add( toDto( courseModule ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(CourseModule entity, CourseModuleRequest dto) {
        if ( dto == null ) {
            return;
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
    }
}
