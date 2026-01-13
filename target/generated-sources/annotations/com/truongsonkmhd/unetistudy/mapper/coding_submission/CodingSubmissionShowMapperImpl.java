package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionShowDTO;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.model.lesson.CodingSubmission;
import com.truongsonkmhd.unetistudy.model.User;
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
    date = "2026-01-13T00:25:40+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Eclipse Adoptium)"
)
@Component
public class CodingSubmissionShowMapperImpl implements CodingSubmissionShowMapper {

    @Override
    public List<CodingSubmission> toEntity(List<CodingSubmissionShowDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CodingSubmission> list = new ArrayList<CodingSubmission>( dtoList.size() );
        for ( CodingSubmissionShowDTO codingSubmissionShowDTO : dtoList ) {
            list.add( toEntity( codingSubmissionShowDTO ) );
        }

        return list;
    }

    @Override
    public List<CodingSubmissionShowDTO> toDto(List<CodingSubmission> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CodingSubmissionShowDTO> list = new ArrayList<CodingSubmissionShowDTO>( entityList.size() );
        for ( CodingSubmission codingSubmission : entityList ) {
            list.add( toDto( codingSubmission ) );
        }

        return list;
    }

    @Override
    public Set<CodingSubmission> toEntity(Set<CodingSubmissionShowDTO> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<CodingSubmission> set = new LinkedHashSet<CodingSubmission>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( CodingSubmissionShowDTO codingSubmissionShowDTO : dtoSet ) {
            set.add( toEntity( codingSubmissionShowDTO ) );
        }

        return set;
    }

    @Override
    public Set<CodingSubmissionShowDTO> toDto(Set<CodingSubmission> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CodingSubmissionShowDTO> set = new LinkedHashSet<CodingSubmissionShowDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CodingSubmission codingSubmission : entitySet ) {
            set.add( toDto( codingSubmission ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(CodingSubmission entity, CodingSubmissionShowDTO dto) {
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
        if ( dto.getSubmittedAt() != null ) {
            entity.setSubmittedAt( LocalDateTime.ofInstant( dto.getSubmittedAt().toInstant(), ZoneId.of( "UTC" ) ) );
        }
    }

    @Override
    public CodingSubmissionShowDTO toDto(CodingSubmission entity) {
        if ( entity == null ) {
            return null;
        }

        CodingSubmissionShowDTO.CodingSubmissionShowDTOBuilder codingSubmissionShowDTO = CodingSubmissionShowDTO.builder();

        codingSubmissionShowDTO.exerciseName( entityExerciseTitle( entity ) );
        codingSubmissionShowDTO.userName( entityUserUsername( entity ) );
        codingSubmissionShowDTO.code( entity.getCode() );
        codingSubmissionShowDTO.language( entity.getLanguage() );
        codingSubmissionShowDTO.status( entity.getStatus() );
        codingSubmissionShowDTO.testCasesPassed( entity.getTestCasesPassed() );
        codingSubmissionShowDTO.totalTestCases( entity.getTotalTestCases() );
        codingSubmissionShowDTO.score( entity.getScore() );
        if ( entity.getSubmittedAt() != null ) {
            codingSubmissionShowDTO.submittedAt( Date.from( entity.getSubmittedAt().toInstant( ZoneOffset.UTC ) ) );
        }

        return codingSubmissionShowDTO.build();
    }

    @Override
    public CodingSubmission toEntity(CodingSubmissionShowDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CodingSubmission.CodingSubmissionBuilder codingSubmission = CodingSubmission.builder();

        codingSubmission.code( dto.getCode() );
        codingSubmission.language( dto.getLanguage() );
        codingSubmission.status( dto.getStatus() );
        codingSubmission.testCasesPassed( dto.getTestCasesPassed() );
        codingSubmission.totalTestCases( dto.getTotalTestCases() );
        codingSubmission.score( dto.getScore() );
        if ( dto.getSubmittedAt() != null ) {
            codingSubmission.submittedAt( LocalDateTime.ofInstant( dto.getSubmittedAt().toInstant(), ZoneId.of( "UTC" ) ) );
        }

        return codingSubmission.build();
    }

    private String entityExerciseTitle(CodingSubmission codingSubmission) {
        if ( codingSubmission == null ) {
            return null;
        }
        CodingExercise exercise = codingSubmission.getExercise();
        if ( exercise == null ) {
            return null;
        }
        String title = exercise.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }

    private String entityUserUsername(CodingSubmission codingSubmission) {
        if ( codingSubmission == null ) {
            return null;
        }
        User user = codingSubmission.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }
}
