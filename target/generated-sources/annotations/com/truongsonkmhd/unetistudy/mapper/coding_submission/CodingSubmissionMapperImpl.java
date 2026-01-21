package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.coding_submission.CodingSubmissionResponseDTO;
import com.truongsonkmhd.unetistudy.model.lesson.CodingSubmission;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-18T13:14:08+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Eclipse Adoptium)"
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
        if ( dto.getVerdict() != null ) {
            entity.setVerdict( dto.getVerdict() );
        }
        if ( dto.getPassedTestcases() != null ) {
            entity.setPassedTestcases( dto.getPassedTestcases() );
        }
        if ( dto.getTotalTestcases() != null ) {
            entity.setTotalTestcases( dto.getTotalTestcases() );
        }
        if ( dto.getRuntimeMs() != null ) {
            entity.setRuntimeMs( dto.getRuntimeMs() );
        }
        if ( dto.getMemoryKb() != null ) {
            entity.setMemoryKb( dto.getMemoryKb() );
        }
        if ( dto.getScore() != null ) {
            entity.setScore( dto.getScore() );
        }
        if ( dto.getSubmittedAt() != null ) {
            entity.setSubmittedAt( dto.getSubmittedAt() );
        }
    }

    @Override
    public CodingSubmissionResponseDTO toDto(CodingSubmission entity) {
        if ( entity == null ) {
            return null;
        }

        CodingSubmissionResponseDTO.CodingSubmissionResponseDTOBuilder codingSubmissionResponseDTO = CodingSubmissionResponseDTO.builder();

        codingSubmissionResponseDTO.code( entity.getCode() );
        codingSubmissionResponseDTO.language( entity.getLanguage() );
        codingSubmissionResponseDTO.verdict( entity.getVerdict() );
        codingSubmissionResponseDTO.passedTestcases( entity.getPassedTestcases() );
        codingSubmissionResponseDTO.totalTestcases( entity.getTotalTestcases() );
        codingSubmissionResponseDTO.runtimeMs( entity.getRuntimeMs() );
        codingSubmissionResponseDTO.memoryKb( entity.getMemoryKb() );
        codingSubmissionResponseDTO.score( entity.getScore() );
        codingSubmissionResponseDTO.submittedAt( entity.getSubmittedAt() );

        return codingSubmissionResponseDTO.build();
    }

    @Override
    public CodingSubmission toEntity(CodingSubmissionResponseDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CodingSubmission.CodingSubmissionBuilder codingSubmission = CodingSubmission.builder();

        codingSubmission.code( dto.getCode() );
        codingSubmission.language( dto.getLanguage() );
        codingSubmission.verdict( dto.getVerdict() );
        codingSubmission.passedTestcases( dto.getPassedTestcases() );
        codingSubmission.totalTestcases( dto.getTotalTestcases() );
        codingSubmission.runtimeMs( dto.getRuntimeMs() );
        codingSubmission.memoryKb( dto.getMemoryKb() );
        codingSubmission.score( dto.getScore() );
        codingSubmission.submittedAt( dto.getSubmittedAt() );

        return codingSubmission.build();
    }
}
