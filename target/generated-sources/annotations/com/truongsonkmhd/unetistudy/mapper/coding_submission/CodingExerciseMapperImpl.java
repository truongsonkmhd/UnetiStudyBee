package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-15T00:06:08+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Eclipse Adoptium)"
)
@Component
public class CodingExerciseMapperImpl implements CodingExerciseMapper {

    @Override
    public List<CodingExercise> toEntity(List<CodingExerciseDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CodingExercise> list = new ArrayList<CodingExercise>( dtoList.size() );
        for ( CodingExerciseDTO codingExerciseDTO : dtoList ) {
            list.add( toEntity( codingExerciseDTO ) );
        }

        return list;
    }

    @Override
    public List<CodingExerciseDTO> toDto(List<CodingExercise> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CodingExerciseDTO> list = new ArrayList<CodingExerciseDTO>( entityList.size() );
        for ( CodingExercise codingExercise : entityList ) {
            list.add( toDto( codingExercise ) );
        }

        return list;
    }

    @Override
    public Set<CodingExercise> toEntity(Set<CodingExerciseDTO> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<CodingExercise> set = new LinkedHashSet<CodingExercise>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( CodingExerciseDTO codingExerciseDTO : dtoSet ) {
            set.add( toEntity( codingExerciseDTO ) );
        }

        return set;
    }

    @Override
    public Set<CodingExerciseDTO> toDto(Set<CodingExercise> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CodingExerciseDTO> set = new LinkedHashSet<CodingExerciseDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CodingExercise codingExercise : entitySet ) {
            set.add( toDto( codingExercise ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(CodingExercise entity, CodingExerciseDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getExerciseId() != null ) {
            entity.setExerciseId( dto.getExerciseId() );
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getProgrammingLanguage() != null ) {
            entity.setProgrammingLanguage( dto.getProgrammingLanguage() );
        }
        if ( dto.getTimeLimitMs() != null ) {
            entity.setTimeLimitMs( dto.getTimeLimitMs() );
        }
        if ( dto.getMemoryLimitMb() != null ) {
            entity.setMemoryLimitMb( dto.getMemoryLimitMb() );
        }
        if ( dto.getDifficulty() != null ) {
            entity.setDifficulty( dto.getDifficulty() );
        }
        if ( dto.getPoints() != null ) {
            entity.setPoints( dto.getPoints() );
        }
        if ( dto.getSlug() != null ) {
            entity.setSlug( dto.getSlug() );
        }
        if ( dto.getInputFormat() != null ) {
            entity.setInputFormat( dto.getInputFormat() );
        }
        if ( dto.getOutputFormat() != null ) {
            entity.setOutputFormat( dto.getOutputFormat() );
        }
        if ( dto.getConstraintName() != null ) {
            entity.setConstraintName( dto.getConstraintName() );
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
    public CodingExerciseDTO toDto(CodingExercise entity) {
        if ( entity == null ) {
            return null;
        }

        CodingExerciseDTO.CodingExerciseDTOBuilder codingExerciseDTO = CodingExerciseDTO.builder();

        codingExerciseDTO.exerciseId( entity.getExerciseId() );
        codingExerciseDTO.title( entity.getTitle() );
        codingExerciseDTO.description( entity.getDescription() );
        codingExerciseDTO.programmingLanguage( entity.getProgrammingLanguage() );
        codingExerciseDTO.difficulty( entity.getDifficulty() );
        codingExerciseDTO.points( entity.getPoints() );
        codingExerciseDTO.isPublished( entity.getIsPublished() );
        codingExerciseDTO.timeLimitMs( entity.getTimeLimitMs() );
        codingExerciseDTO.memoryLimitMb( entity.getMemoryLimitMb() );
        codingExerciseDTO.slug( entity.getSlug() );
        codingExerciseDTO.inputFormat( entity.getInputFormat() );
        codingExerciseDTO.outputFormat( entity.getOutputFormat() );
        codingExerciseDTO.constraintName( entity.getConstraintName() );
        codingExerciseDTO.createdAt( entity.getCreatedAt() );
        codingExerciseDTO.updatedAt( entity.getUpdatedAt() );

        return codingExerciseDTO.build();
    }

    @Override
    public CodingExercise toEntity(CodingExerciseDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CodingExercise.CodingExerciseBuilder codingExercise = CodingExercise.builder();

        codingExercise.exerciseId( dto.getExerciseId() );
        codingExercise.title( dto.getTitle() );
        codingExercise.description( dto.getDescription() );
        codingExercise.programmingLanguage( dto.getProgrammingLanguage() );
        codingExercise.difficulty( dto.getDifficulty() );
        codingExercise.points( dto.getPoints() );
        codingExercise.slug( dto.getSlug() );
        codingExercise.isPublished( dto.getIsPublished() );

        return codingExercise.build();
    }
}
