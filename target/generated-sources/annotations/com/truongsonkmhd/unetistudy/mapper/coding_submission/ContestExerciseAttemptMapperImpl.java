package com.truongsonkmhd.unetistudy.mapper.coding_submission;

import com.truongsonkmhd.unetistudy.dto.ContestExerciseAttempt.AttemptInfoDTO;
import com.truongsonkmhd.unetistudy.model.ContestExerciseAttempt;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
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
public class ContestExerciseAttemptMapperImpl implements ContestExerciseAttemptMapper {

    @Override
    public List<ContestExerciseAttempt> toEntity(List<AttemptInfoDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ContestExerciseAttempt> list = new ArrayList<ContestExerciseAttempt>( dtoList.size() );
        for ( AttemptInfoDTO attemptInfoDTO : dtoList ) {
            list.add( toEntity( attemptInfoDTO ) );
        }

        return list;
    }

    @Override
    public List<AttemptInfoDTO> toDto(List<ContestExerciseAttempt> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AttemptInfoDTO> list = new ArrayList<AttemptInfoDTO>( entityList.size() );
        for ( ContestExerciseAttempt contestExerciseAttempt : entityList ) {
            list.add( toDto( contestExerciseAttempt ) );
        }

        return list;
    }

    @Override
    public Set<ContestExerciseAttempt> toEntity(Set<AttemptInfoDTO> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<ContestExerciseAttempt> set = new LinkedHashSet<ContestExerciseAttempt>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( AttemptInfoDTO attemptInfoDTO : dtoSet ) {
            set.add( toEntity( attemptInfoDTO ) );
        }

        return set;
    }

    @Override
    public Set<AttemptInfoDTO> toDto(Set<ContestExerciseAttempt> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<AttemptInfoDTO> set = new LinkedHashSet<AttemptInfoDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( ContestExerciseAttempt contestExerciseAttempt : entitySet ) {
            set.add( toDto( contestExerciseAttempt ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(ContestExerciseAttempt entity, AttemptInfoDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getExerciseType() != null ) {
            entity.setExerciseType( dto.getExerciseType() );
        }
        if ( dto.getAttemptNumber() != null ) {
            entity.setAttemptNumber( dto.getAttemptNumber() );
        }
    }

    @Override
    public AttemptInfoDTO toDto(ContestExerciseAttempt entity) {
        if ( entity == null ) {
            return null;
        }

        AttemptInfoDTO.AttemptInfoDTOBuilder attemptInfoDTO = AttemptInfoDTO.builder();

        attemptInfoDTO.lessonID( entityLessonLessonId( entity ) );
        attemptInfoDTO.exerciseType( entity.getExerciseType() );
        attemptInfoDTO.attemptNumber( entity.getAttemptNumber() );

        return attemptInfoDTO.build();
    }

    @Override
    public ContestExerciseAttempt toEntity(AttemptInfoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ContestExerciseAttempt.ContestExerciseAttemptBuilder contestExerciseAttempt = ContestExerciseAttempt.builder();

        contestExerciseAttempt.lesson( attemptInfoDTOToCourseLesson( dto ) );
        contestExerciseAttempt.exerciseType( dto.getExerciseType() );
        contestExerciseAttempt.attemptNumber( dto.getAttemptNumber() );

        return contestExerciseAttempt.build();
    }

    private UUID entityLessonLessonId(ContestExerciseAttempt contestExerciseAttempt) {
        if ( contestExerciseAttempt == null ) {
            return null;
        }
        CourseLesson lesson = contestExerciseAttempt.getLesson();
        if ( lesson == null ) {
            return null;
        }
        UUID lessonId = lesson.getLessonId();
        if ( lessonId == null ) {
            return null;
        }
        return lessonId;
    }

    protected CourseLesson attemptInfoDTOToCourseLesson(AttemptInfoDTO attemptInfoDTO) {
        if ( attemptInfoDTO == null ) {
            return null;
        }

        CourseLesson.CourseLessonBuilder courseLesson = CourseLesson.builder();

        courseLesson.lessonId( attemptInfoDTO.getLessonID() );

        return courseLesson.build();
    }
}
