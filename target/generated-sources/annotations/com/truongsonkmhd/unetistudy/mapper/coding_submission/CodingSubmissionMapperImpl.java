package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionResponseDTO;
import com.truongsonkmhd.unetistudy.model.CodingExercise;
import com.truongsonkmhd.unetistudy.model.CodingSubmission;
import com.truongsonkmhd.unetistudy.model.User;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-24T23:01:42+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class CodingSubmissionMapperImpl implements CodingSubmissionMapper {

    @Override
    public List<CodingSubmission> toEntity(List<CodingSubmissionResponseDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CodingSubmission> list = new ArrayList<CodingSubmission>( dtoList.size() );
        for ( CodingSubmissionResponseDTO codingSubmissionResponseDTO : dtoList ) {
            list.add( toEntity( codingSubmissionResponseDTO ) );
        }

        return list;
    }

    @Override
    public List<CodingSubmissionResponseDTO> toDto(List<CodingSubmission> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CodingSubmissionResponseDTO> list = new ArrayList<CodingSubmissionResponseDTO>( entityList.size() );
        for ( CodingSubmission codingSubmission : entityList ) {
            list.add( toDto( codingSubmission ) );
        }

        return list;
    }

    @Override
    public Set<CodingSubmission> toEntity(Set<CodingSubmissionResponseDTO> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<CodingSubmission> set = new LinkedHashSet<CodingSubmission>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( CodingSubmissionResponseDTO codingSubmissionResponseDTO : dtoSet ) {
            set.add( toEntity( codingSubmissionResponseDTO ) );
        }

        return set;
    }

    @Override
    public Set<CodingSubmissionResponseDTO> toDto(Set<CodingSubmission> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CodingSubmissionResponseDTO> set = new LinkedHashSet<CodingSubmissionResponseDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CodingSubmission codingSubmission : entitySet ) {
            set.add( toDto( codingSubmission ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(CodingSubmission entity, CodingSubmissionResponseDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCode() != null ) {
            entity.setCode( dto.getCode() );
        }
        if ( dto.getLanguage() != null ) {
            entity.setLanguage( dto.getLanguage() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getTestCasesPassed() != null ) {
            entity.setTestCasesPassed( dto.getTestCasesPassed() );
        }
        if ( dto.getTotalTestCases() != null ) {
            entity.setTotalTestCases( dto.getTotalTestCases() );
        }
        if ( dto.getScore() != null ) {
            entity.setScore( dto.getScore() );
        }
    }

    @Override
    public CodingSubmissionResponseDTO toDto(CodingSubmission entity) {
        if ( entity == null ) {
            return null;
        }

        CodingSubmissionResponseDTO.CodingSubmissionResponseDTOBuilder codingSubmissionResponseDTO = CodingSubmissionResponseDTO.builder();

        codingSubmissionResponseDTO.exerciseID( entityExerciseExerciseId( entity ) );
        codingSubmissionResponseDTO.userID( entityUserId( entity ) );
        codingSubmissionResponseDTO.code( entity.getCode() );
        codingSubmissionResponseDTO.language( entity.getLanguage() );
        codingSubmissionResponseDTO.status( entity.getStatus() );
        codingSubmissionResponseDTO.testCasesPassed( entity.getTestCasesPassed() );
        codingSubmissionResponseDTO.totalTestCases( entity.getTotalTestCases() );
        codingSubmissionResponseDTO.score( entity.getScore() );

        return codingSubmissionResponseDTO.build();
    }

    @Override
    public CodingSubmission toEntity(CodingSubmissionResponseDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CodingSubmission.CodingSubmissionBuilder codingSubmission = CodingSubmission.builder();

        codingSubmission.exercise( codingSubmissionResponseDTOToCodingExercise( dto ) );
        codingSubmission.user( codingSubmissionResponseDTOToUser( dto ) );
        codingSubmission.code( dto.getCode() );
        codingSubmission.language( dto.getLanguage() );
        codingSubmission.status( dto.getStatus() );
        codingSubmission.testCasesPassed( dto.getTestCasesPassed() );
        codingSubmission.totalTestCases( dto.getTotalTestCases() );
        codingSubmission.score( dto.getScore() );

        return codingSubmission.build();
    }

    private UUID entityExerciseExerciseId(CodingSubmission codingSubmission) {
        if ( codingSubmission == null ) {
            return null;
        }
        CodingExercise exercise = codingSubmission.getExercise();
        if ( exercise == null ) {
            return null;
        }
        UUID exerciseId = exercise.getExerciseId();
        if ( exerciseId == null ) {
            return null;
        }
        return exerciseId;
    }

    private UUID entityUserId(CodingSubmission codingSubmission) {
        if ( codingSubmission == null ) {
            return null;
        }
        User user = codingSubmission.getUser();
        if ( user == null ) {
            return null;
        }
        UUID id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected CodingExercise codingSubmissionResponseDTOToCodingExercise(CodingSubmissionResponseDTO codingSubmissionResponseDTO) {
        if ( codingSubmissionResponseDTO == null ) {
            return null;
        }

        CodingExercise.CodingExerciseBuilder codingExercise = CodingExercise.builder();

        codingExercise.exerciseId( codingSubmissionResponseDTO.getExerciseID() );

        return codingExercise.build();
    }

    protected User codingSubmissionResponseDTOToUser(CodingSubmissionResponseDTO codingSubmissionResponseDTO) {
        if ( codingSubmissionResponseDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( codingSubmissionResponseDTO.getUserID() );

        return user.build();
    }
}
