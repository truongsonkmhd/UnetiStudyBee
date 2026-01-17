package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.model.lesson.ExerciseTestCase;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-17T22:23:58+0700",
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
        if ( entity.getExerciseTestCases() != null ) {
            List<ExerciseTestCase> list = exerciseTestCasesDTOListToExerciseTestCaseList( dto.getExerciseTestCases() );
            if ( list != null ) {
                entity.getExerciseTestCases().clear();
                entity.getExerciseTestCases().addAll( list );
            }
        }
        else {
            List<ExerciseTestCase> list = exerciseTestCasesDTOListToExerciseTestCaseList( dto.getExerciseTestCases() );
            if ( list != null ) {
                entity.setExerciseTestCases( list );
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
        codingExerciseDTO.exerciseTestCases( exerciseTestCaseListToExerciseTestCasesDTOList( entity.getExerciseTestCases() ) );
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
        codingExercise.timeLimitMs( dto.getTimeLimitMs() );
        codingExercise.memoryLimitMb( dto.getMemoryLimitMb() );
        codingExercise.difficulty( dto.getDifficulty() );
        codingExercise.points( dto.getPoints() );
        codingExercise.slug( dto.getSlug() );
        codingExercise.inputFormat( dto.getInputFormat() );
        codingExercise.outputFormat( dto.getOutputFormat() );
        codingExercise.constraintName( dto.getConstraintName() );
        codingExercise.isPublished( dto.getIsPublished() );

        return codingExercise.build();
    }

    protected ExerciseTestCase exerciseTestCasesDTOToExerciseTestCase(ExerciseTestCasesDTO exerciseTestCasesDTO) {
        if ( exerciseTestCasesDTO == null ) {
            return null;
        }

        ExerciseTestCase exerciseTestCase = new ExerciseTestCase();

        exerciseTestCase.setInput( exerciseTestCasesDTO.getInput() );
        exerciseTestCase.setExpectedOutput( exerciseTestCasesDTO.getExpectedOutput() );
        exerciseTestCase.setIsPublic( exerciseTestCasesDTO.getIsPublic() );
        exerciseTestCase.setScore( exerciseTestCasesDTO.getScore() );

        return exerciseTestCase;
    }

    protected List<ExerciseTestCase> exerciseTestCasesDTOListToExerciseTestCaseList(List<ExerciseTestCasesDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<ExerciseTestCase> list1 = new ArrayList<ExerciseTestCase>( list.size() );
        for ( ExerciseTestCasesDTO exerciseTestCasesDTO : list ) {
            list1.add( exerciseTestCasesDTOToExerciseTestCase( exerciseTestCasesDTO ) );
        }

        return list1;
    }

    protected ExerciseTestCasesDTO exerciseTestCaseToExerciseTestCasesDTO(ExerciseTestCase exerciseTestCase) {
        if ( exerciseTestCase == null ) {
            return null;
        }

        ExerciseTestCasesDTO.ExerciseTestCasesDTOBuilder exerciseTestCasesDTO = ExerciseTestCasesDTO.builder();

        exerciseTestCasesDTO.input( exerciseTestCase.getInput() );
        exerciseTestCasesDTO.expectedOutput( exerciseTestCase.getExpectedOutput() );
        exerciseTestCasesDTO.isPublic( exerciseTestCase.getIsPublic() );
        exerciseTestCasesDTO.score( exerciseTestCase.getScore() );

        return exerciseTestCasesDTO.build();
    }

    protected List<ExerciseTestCasesDTO> exerciseTestCaseListToExerciseTestCasesDTOList(List<ExerciseTestCase> list) {
        if ( list == null ) {
            return null;
        }

        List<ExerciseTestCasesDTO> list1 = new ArrayList<ExerciseTestCasesDTO>( list.size() );
        for ( ExerciseTestCase exerciseTestCase : list ) {
            list1.add( exerciseTestCaseToExerciseTestCasesDTO( exerciseTestCase ) );
        }

        return list1;
    }
}
