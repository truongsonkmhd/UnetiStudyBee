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
    date = "2026-01-26T11:50:00+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
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
    public void partialUpdate(CodingSubmission arg0, CodingSubmissionResponseDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getCode() != null ) {
            arg0.setCode( arg1.getCode() );
        }
        if ( arg1.getLanguage() != null ) {
            arg0.setLanguage( arg1.getLanguage() );
        }
        if ( arg1.getMemoryKb() != null ) {
            arg0.setMemoryKb( arg1.getMemoryKb() );
        }
        if ( arg1.getPassedTestcases() != null ) {
            arg0.setPassedTestcases( arg1.getPassedTestcases() );
        }
        if ( arg1.getRuntimeMs() != null ) {
            arg0.setRuntimeMs( arg1.getRuntimeMs() );
        }
        if ( arg1.getScore() != null ) {
            arg0.setScore( arg1.getScore() );
        }
        if ( arg1.getSubmissionId() != null ) {
            arg0.setSubmissionId( arg1.getSubmissionId() );
        }
        if ( arg1.getSubmittedAt() != null ) {
            arg0.setSubmittedAt( arg1.getSubmittedAt() );
        }
        if ( arg1.getTotalTestcases() != null ) {
            arg0.setTotalTestcases( arg1.getTotalTestcases() );
        }
        if ( arg1.getVerdict() != null ) {
            arg0.setVerdict( arg1.getVerdict() );
        }
    }

    @Override
    public CodingSubmissionResponseDTO toDto(CodingSubmission entity) {
        if ( entity == null ) {
            return null;
        }

        CodingSubmissionResponseDTO.CodingSubmissionResponseDTOBuilder codingSubmissionResponseDTO = CodingSubmissionResponseDTO.builder();

        codingSubmissionResponseDTO.submissionId( entity.getSubmissionId() );
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
        codingSubmission.memoryKb( dto.getMemoryKb() );
        codingSubmission.passedTestcases( dto.getPassedTestcases() );
        codingSubmission.runtimeMs( dto.getRuntimeMs() );
        codingSubmission.score( dto.getScore() );
        codingSubmission.submissionId( dto.getSubmissionId() );
        codingSubmission.submittedAt( dto.getSubmittedAt() );
        codingSubmission.totalTestcases( dto.getTotalTestcases() );
        codingSubmission.verdict( dto.getVerdict() );

        return codingSubmission.build();
    }
}
