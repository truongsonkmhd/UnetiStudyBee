package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import com.truongsonkmhd.unetistudy.model.lesson.ExerciseTestCase;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-17T22:23:59+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Eclipse Adoptium)"
)
@Component
public class ExerciseTestCaseMapperImpl implements ExerciseTestCaseMapper {

    @Override
    public List<ExerciseTestCase> toEntity(List<ExerciseTestCasesDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ExerciseTestCase> list = new ArrayList<ExerciseTestCase>( dtoList.size() );
        for ( ExerciseTestCasesDTO exerciseTestCasesDTO : dtoList ) {
            list.add( toEntity( exerciseTestCasesDTO ) );
        }

        return list;
    }

    @Override
    public List<ExerciseTestCasesDTO> toDto(List<ExerciseTestCase> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ExerciseTestCasesDTO> list = new ArrayList<ExerciseTestCasesDTO>( entityList.size() );
        for ( ExerciseTestCase exerciseTestCase : entityList ) {
            list.add( toDto( exerciseTestCase ) );
        }

        return list;
    }

    @Override
    public Set<ExerciseTestCase> toEntity(Set<ExerciseTestCasesDTO> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<ExerciseTestCase> set = new LinkedHashSet<ExerciseTestCase>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( ExerciseTestCasesDTO exerciseTestCasesDTO : dtoSet ) {
            set.add( toEntity( exerciseTestCasesDTO ) );
        }

        return set;
    }

    @Override
    public Set<ExerciseTestCasesDTO> toDto(Set<ExerciseTestCase> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<ExerciseTestCasesDTO> set = new LinkedHashSet<ExerciseTestCasesDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( ExerciseTestCase exerciseTestCase : entitySet ) {
            set.add( toDto( exerciseTestCase ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(ExerciseTestCase entity, ExerciseTestCasesDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getInput() != null ) {
            entity.setInput( dto.getInput() );
        }
        if ( dto.getExpectedOutput() != null ) {
            entity.setExpectedOutput( dto.getExpectedOutput() );
        }
        if ( dto.getIsPublic() != null ) {
            entity.setIsPublic( dto.getIsPublic() );
        }
        if ( dto.getScore() != null ) {
            entity.setScore( dto.getScore() );
        }
    }

    @Override
    public ExerciseTestCase toEntity(ExerciseTestCasesDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ExerciseTestCase exerciseTestCase = new ExerciseTestCase();

        exerciseTestCase.setInput( dto.getInput() );
        exerciseTestCase.setExpectedOutput( dto.getExpectedOutput() );
        exerciseTestCase.setIsPublic( dto.getIsPublic() );
        exerciseTestCase.setScore( dto.getScore() );

        return exerciseTestCase;
    }

    @Override
    public ExerciseTestCasesDTO toDto(ExerciseTestCase entity) {
        if ( entity == null ) {
            return null;
        }

        ExerciseTestCasesDTO.ExerciseTestCasesDTOBuilder exerciseTestCasesDTO = ExerciseTestCasesDTO.builder();

        exerciseTestCasesDTO.input( entity.getInput() );
        exerciseTestCasesDTO.expectedOutput( entity.getExpectedOutput() );
        exerciseTestCasesDTO.isPublic( entity.getIsPublic() );
        exerciseTestCasesDTO.score( entity.getScore() );

        return exerciseTestCasesDTO.build();
    }
}
