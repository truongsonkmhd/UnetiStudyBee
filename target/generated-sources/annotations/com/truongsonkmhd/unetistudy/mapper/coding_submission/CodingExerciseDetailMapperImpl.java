package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDetailDTO;
import com.truongsonkmhd.unetistudy.model.CodingExercise;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import com.truongsonkmhd.unetistudy.model.ExerciseTestCase;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-09T00:04:21+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class CodingExerciseDetailMapperImpl implements CodingExerciseDetailMapper {

    @Autowired
    private ExerciseTestCaseMapper exerciseTestCaseMapper;

    @Override
    public List<CodingExercise> toEntity(List<CodingExerciseDetailDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CodingExercise> list = new ArrayList<CodingExercise>( dtoList.size() );
        for ( CodingExerciseDetailDTO codingExerciseDetailDTO : dtoList ) {
            list.add( toEntity( codingExerciseDetailDTO ) );
        }

        return list;
    }

    @Override
    public List<CodingExerciseDetailDTO> toDto(List<CodingExercise> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CodingExerciseDetailDTO> list = new ArrayList<CodingExerciseDetailDTO>( entityList.size() );
        for ( CodingExercise codingExercise : entityList ) {
            list.add( toDto( codingExercise ) );
        }

        return list;
    }

    @Override
    public Set<CodingExercise> toEntity(Set<CodingExerciseDetailDTO> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<CodingExercise> set = new LinkedHashSet<CodingExercise>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( CodingExerciseDetailDTO codingExerciseDetailDTO : dtoSet ) {
            set.add( toEntity( codingExerciseDetailDTO ) );
        }

        return set;
    }

    @Override
    public Set<CodingExerciseDetailDTO> toDto(Set<CodingExercise> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CodingExerciseDetailDTO> set = new LinkedHashSet<CodingExerciseDetailDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CodingExercise codingExercise : entitySet ) {
            set.add( toDto( codingExercise ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(CodingExercise entity, CodingExerciseDetailDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( entity.getExerciseTestCases() != null ) {
            Set<ExerciseTestCase> set = exerciseTestCaseMapper.toEntity( dto.getExerciseTestCases() );
            if ( set != null ) {
                entity.getExerciseTestCases().clear();
                entity.getExerciseTestCases().addAll( set );
            }
        }
        else {
            Set<ExerciseTestCase> set = exerciseTestCaseMapper.toEntity( dto.getExerciseTestCases() );
            if ( set != null ) {
                entity.setExerciseTestCases( set );
            }
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
        if ( dto.getInitialCode() != null ) {
            entity.setInitialCode( dto.getInitialCode() );
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
    }

    @Override
    public CodingExerciseDetailDTO toDto(CodingExercise entity) {
        if ( entity == null ) {
            return null;
        }

        CodingExerciseDetailDTO.CodingExerciseDetailDTOBuilder codingExerciseDetailDTO = CodingExerciseDetailDTO.builder();

        codingExerciseDetailDTO.exerciseID( entity.getExerciseId() );
        UUID lessonId = entityLessonLessonId( entity );
        if ( lessonId != null ) {
            codingExerciseDetailDTO.lessonId( lessonId.toString() );
        }
        codingExerciseDetailDTO.timeLimit( entity.getTimeLimitMs() );
        codingExerciseDetailDTO.memoryLimit( entity.getMemoryLimitMb() );
        codingExerciseDetailDTO.exerciseTestCases( exerciseTestCaseMapper.toDto( entity.getExerciseTestCases() ) );
        codingExerciseDetailDTO.title( entity.getTitle() );
        codingExerciseDetailDTO.description( entity.getDescription() );
        codingExerciseDetailDTO.programmingLanguage( entity.getProgrammingLanguage() );
        codingExerciseDetailDTO.initialCode( entity.getInitialCode() );
        codingExerciseDetailDTO.difficulty( entity.getDifficulty() );
        codingExerciseDetailDTO.points( entity.getPoints() );
        codingExerciseDetailDTO.slug( entity.getSlug() );
        codingExerciseDetailDTO.inputFormat( entity.getInputFormat() );
        codingExerciseDetailDTO.outputFormat( entity.getOutputFormat() );
        codingExerciseDetailDTO.constraintName( entity.getConstraintName() );

        return codingExerciseDetailDTO.build();
    }

    @Override
    public CodingExercise toEntity(CodingExerciseDetailDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CodingExercise.CodingExerciseBuilder codingExercise = CodingExercise.builder();

        codingExercise.exerciseId( dto.getExerciseID() );
        codingExercise.timeLimitMs( dto.getTimeLimit() );
        codingExercise.memoryLimitMb( dto.getMemoryLimit() );
        codingExercise.exerciseTestCases( exerciseTestCaseMapper.toEntity( dto.getExerciseTestCases() ) );
        codingExercise.title( dto.getTitle() );
        codingExercise.description( dto.getDescription() );
        codingExercise.programmingLanguage( dto.getProgrammingLanguage() );
        codingExercise.initialCode( dto.getInitialCode() );
        codingExercise.difficulty( dto.getDifficulty() );
        codingExercise.points( dto.getPoints() );
        codingExercise.slug( dto.getSlug() );
        codingExercise.inputFormat( dto.getInputFormat() );
        codingExercise.outputFormat( dto.getOutputFormat() );
        codingExercise.constraintName( dto.getConstraintName() );

        return codingExercise.build();
    }

    private UUID entityLessonLessonId(CodingExercise codingExercise) {
        if ( codingExercise == null ) {
            return null;
        }
        CourseLesson lesson = codingExercise.getLesson();
        if ( lesson == null ) {
            return null;
        }
        UUID lessonId = lesson.getLessonId();
        if ( lessonId == null ) {
            return null;
        }
        return lessonId;
    }
}
