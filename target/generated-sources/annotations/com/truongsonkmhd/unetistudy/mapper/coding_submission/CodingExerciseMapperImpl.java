package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.model.CodingExercise;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-12T15:37:21+0700",
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

        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
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
    }

    @Override
    public CodingExerciseDTO toDto(CodingExercise entity) {
        if ( entity == null ) {
            return null;
        }

        CodingExerciseDTO.CodingExerciseDTOBuilder codingExerciseDTO = CodingExerciseDTO.builder();

        codingExerciseDTO.exerciseID( entity.getExerciseId() );
        codingExerciseDTO.lessonTitle( entityLessonTitle( entity ) );
        codingExerciseDTO.programLanguage( entity.getProgrammingLanguage() );
        codingExerciseDTO.description( entity.getDescription() );
        codingExerciseDTO.difficulty( entity.getDifficulty() );
        codingExerciseDTO.points( entity.getPoints() );
        codingExerciseDTO.slug( entity.getSlug() );
        codingExerciseDTO.title( entity.getTitle() );

        return codingExerciseDTO.build();
    }

    @Override
    public CodingExercise toEntity(CodingExerciseDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CodingExercise.CodingExerciseBuilder codingExercise = CodingExercise.builder();

        codingExercise.exerciseId( dto.getExerciseID() );
        codingExercise.programmingLanguage( dto.getProgramLanguage() );
        codingExercise.title( dto.getTitle() );
        codingExercise.description( dto.getDescription() );
        codingExercise.difficulty( dto.getDifficulty() );
        codingExercise.points( dto.getPoints() );
        codingExercise.slug( dto.getSlug() );

        return codingExercise.build();
    }

    private String entityLessonTitle(CodingExercise codingExercise) {
        if ( codingExercise == null ) {
            return null;
        }
        CourseLesson lesson = codingExercise.getLesson();
        if ( lesson == null ) {
            return null;
        }
        String title = lesson.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }
}
