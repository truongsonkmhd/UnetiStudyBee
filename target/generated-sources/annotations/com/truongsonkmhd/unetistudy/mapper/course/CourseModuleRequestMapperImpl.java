package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseModuleRequest;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.QuizDTO;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonRequest;
import com.truongsonkmhd.unetistudy.model.course.CourseModule;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;
import com.truongsonkmhd.unetistudy.model.lesson.Quiz;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-14T16:27:04+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Eclipse Adoptium)"
)
@Component
public class CourseModuleRequestMapperImpl implements CourseModuleRequestMapper {

    @Override
    public Set<CourseModule> toEntity(Set<CourseModuleRequest> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<CourseModule> set = new LinkedHashSet<CourseModule>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( CourseModuleRequest courseModuleRequest : dtoSet ) {
            set.add( toEntity( courseModuleRequest ) );
        }

        return set;
    }

    @Override
    public Set<CourseModuleRequest> toDto(Set<CourseModule> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CourseModuleRequest> set = new LinkedHashSet<CourseModuleRequest>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CourseModule courseModule : entitySet ) {
            set.add( toDto( courseModule ) );
        }

        return set;
    }

    @Override
    public CourseModule toEntity(CourseModuleRequest dto) {
        if ( dto == null ) {
            return null;
        }

        CourseModule.CourseModuleBuilder courseModule = CourseModule.builder();

        courseModule.title( dto.getTitle() );
        courseModule.description( dto.getDescription() );
        courseModule.orderIndex( dto.getOrderIndex() );
        courseModule.duration( dto.getDuration() );
        courseModule.isPublished( dto.getIsPublished() );
        courseModule.slug( dto.getSlug() );

        return courseModule.build();
    }

    @Override
    public CourseModuleRequest toDto(CourseModule entity) {
        if ( entity == null ) {
            return null;
        }

        CourseModuleRequest.CourseModuleRequestBuilder courseModuleRequest = CourseModuleRequest.builder();

        courseModuleRequest.moduleId( entity.getModuleId() );
        courseModuleRequest.title( entity.getTitle() );
        courseModuleRequest.description( entity.getDescription() );
        courseModuleRequest.orderIndex( entity.getOrderIndex() );
        courseModuleRequest.duration( entity.getDuration() );
        courseModuleRequest.isPublished( entity.getIsPublished() );
        courseModuleRequest.slug( entity.getSlug() );
        courseModuleRequest.lessons( courseLessonListToLessonRequestList( entity.getLessons() ) );

        return courseModuleRequest.build();
    }

    @Override
    public List<CourseModule> toEntity(List<CourseModuleRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CourseModule> list = new ArrayList<CourseModule>( dtoList.size() );
        for ( CourseModuleRequest courseModuleRequest : dtoList ) {
            list.add( toEntity( courseModuleRequest ) );
        }

        return list;
    }

    @Override
    public List<CourseModuleRequest> toDto(List<CourseModule> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CourseModuleRequest> list = new ArrayList<CourseModuleRequest>( entityList.size() );
        for ( CourseModule courseModule : entityList ) {
            list.add( toDto( courseModule ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(CourseModule entity, CourseModuleRequest dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getModuleId() != null ) {
            entity.setModuleId( dto.getModuleId() );
        }
        if ( entity.getLessons() != null ) {
            List<CourseLesson> list = lessonRequestListToCourseLessonList( dto.getLessons() );
            if ( list != null ) {
                entity.getLessons().clear();
                entity.getLessons().addAll( list );
            }
        }
        else {
            List<CourseLesson> list = lessonRequestListToCourseLessonList( dto.getLessons() );
            if ( list != null ) {
                entity.setLessons( list );
            }
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getOrderIndex() != null ) {
            entity.setOrderIndex( dto.getOrderIndex() );
        }
        if ( dto.getDuration() != null ) {
            entity.setDuration( dto.getDuration() );
        }
        if ( dto.getIsPublished() != null ) {
            entity.setIsPublished( dto.getIsPublished() );
        }
        if ( dto.getSlug() != null ) {
            entity.setSlug( dto.getSlug() );
        }
    }

    protected CodingExerciseDTO codingExerciseToCodingExerciseDTO(CodingExercise codingExercise) {
        if ( codingExercise == null ) {
            return null;
        }

        CodingExerciseDTO.CodingExerciseDTOBuilder codingExerciseDTO = CodingExerciseDTO.builder();

        codingExerciseDTO.exerciseId( codingExercise.getExerciseId() );
        codingExerciseDTO.title( codingExercise.getTitle() );
        codingExerciseDTO.description( codingExercise.getDescription() );
        codingExerciseDTO.programmingLanguage( codingExercise.getProgrammingLanguage() );
        codingExerciseDTO.difficulty( codingExercise.getDifficulty() );
        codingExerciseDTO.points( codingExercise.getPoints() );
        codingExerciseDTO.isPublished( codingExercise.getIsPublished() );
        codingExerciseDTO.timeLimitMs( codingExercise.getTimeLimitMs() );
        codingExerciseDTO.memoryLimitMb( codingExercise.getMemoryLimitMb() );
        codingExerciseDTO.slug( codingExercise.getSlug() );
        codingExerciseDTO.inputFormat( codingExercise.getInputFormat() );
        codingExerciseDTO.outputFormat( codingExercise.getOutputFormat() );
        codingExerciseDTO.constraintName( codingExercise.getConstraintName() );
        codingExerciseDTO.createdAt( codingExercise.getCreatedAt() );
        codingExerciseDTO.updatedAt( codingExercise.getUpdatedAt() );

        return codingExerciseDTO.build();
    }

    protected List<CodingExerciseDTO> codingExerciseListToCodingExerciseDTOList(List<CodingExercise> list) {
        if ( list == null ) {
            return null;
        }

        List<CodingExerciseDTO> list1 = new ArrayList<CodingExerciseDTO>( list.size() );
        for ( CodingExercise codingExercise : list ) {
            list1.add( codingExerciseToCodingExerciseDTO( codingExercise ) );
        }

        return list1;
    }

    protected QuizDTO quizToQuizDTO(Quiz quiz) {
        if ( quiz == null ) {
            return null;
        }

        QuizDTO.QuizDTOBuilder quizDTO = QuizDTO.builder();

        quizDTO.quizId( quiz.getQuizId() );
        quizDTO.title( quiz.getTitle() );
        quizDTO.totalQuestions( quiz.getTotalQuestions() );
        quizDTO.passScore( quiz.getPassScore() );
        quizDTO.isPublished( quiz.getIsPublished() );

        return quizDTO.build();
    }

    protected List<QuizDTO> quizListToQuizDTOList(List<Quiz> list) {
        if ( list == null ) {
            return null;
        }

        List<QuizDTO> list1 = new ArrayList<QuizDTO>( list.size() );
        for ( Quiz quiz : list ) {
            list1.add( quizToQuizDTO( quiz ) );
        }

        return list1;
    }

    protected LessonRequest courseLessonToLessonRequest(CourseLesson courseLesson) {
        if ( courseLesson == null ) {
            return null;
        }

        LessonRequest.LessonRequestBuilder lessonRequest = LessonRequest.builder();

        lessonRequest.lessonId( courseLesson.getLessonId() );
        lessonRequest.title( courseLesson.getTitle() );
        lessonRequest.description( courseLesson.getDescription() );
        lessonRequest.content( courseLesson.getContent() );
        lessonRequest.videoUrl( courseLesson.getVideoUrl() );
        lessonRequest.duration( courseLesson.getDuration() );
        lessonRequest.orderIndex( courseLesson.getOrderIndex() );
        lessonRequest.isPreview( courseLesson.getIsPreview() );
        lessonRequest.isPublished( courseLesson.getIsPublished() );
        lessonRequest.slug( courseLesson.getSlug() );
        lessonRequest.lessonType( courseLesson.getLessonType() );
        lessonRequest.isContest( courseLesson.getIsContest() );
        lessonRequest.totalPoints( courseLesson.getTotalPoints() );
        lessonRequest.contestStartTime( courseLesson.getContestStartTime() );
        lessonRequest.contestEndTime( courseLesson.getContestEndTime() );
        lessonRequest.codingExercises( codingExerciseListToCodingExerciseDTOList( courseLesson.getCodingExercises() ) );
        lessonRequest.quizzes( quizListToQuizDTOList( courseLesson.getQuizzes() ) );

        return lessonRequest.build();
    }

    protected List<LessonRequest> courseLessonListToLessonRequestList(List<CourseLesson> list) {
        if ( list == null ) {
            return null;
        }

        List<LessonRequest> list1 = new ArrayList<LessonRequest>( list.size() );
        for ( CourseLesson courseLesson : list ) {
            list1.add( courseLessonToLessonRequest( courseLesson ) );
        }

        return list1;
    }

    protected CodingExercise codingExerciseDTOToCodingExercise(CodingExerciseDTO codingExerciseDTO) {
        if ( codingExerciseDTO == null ) {
            return null;
        }

        CodingExercise.CodingExerciseBuilder codingExercise = CodingExercise.builder();

        codingExercise.exerciseId( codingExerciseDTO.getExerciseId() );
        codingExercise.title( codingExerciseDTO.getTitle() );
        codingExercise.description( codingExerciseDTO.getDescription() );
        codingExercise.programmingLanguage( codingExerciseDTO.getProgrammingLanguage() );
        codingExercise.timeLimitMs( codingExerciseDTO.getTimeLimitMs() );
        codingExercise.memoryLimitMb( codingExerciseDTO.getMemoryLimitMb() );
        codingExercise.difficulty( codingExerciseDTO.getDifficulty() );
        codingExercise.points( codingExerciseDTO.getPoints() );
        codingExercise.slug( codingExerciseDTO.getSlug() );
        codingExercise.inputFormat( codingExerciseDTO.getInputFormat() );
        codingExercise.outputFormat( codingExerciseDTO.getOutputFormat() );
        codingExercise.constraintName( codingExerciseDTO.getConstraintName() );
        codingExercise.isPublished( codingExerciseDTO.getIsPublished() );
        codingExercise.createdAt( codingExerciseDTO.getCreatedAt() );
        codingExercise.updatedAt( codingExerciseDTO.getUpdatedAt() );

        return codingExercise.build();
    }

    protected List<CodingExercise> codingExerciseDTOListToCodingExerciseList(List<CodingExerciseDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<CodingExercise> list1 = new ArrayList<CodingExercise>( list.size() );
        for ( CodingExerciseDTO codingExerciseDTO : list ) {
            list1.add( codingExerciseDTOToCodingExercise( codingExerciseDTO ) );
        }

        return list1;
    }

    protected Quiz quizDTOToQuiz(QuizDTO quizDTO) {
        if ( quizDTO == null ) {
            return null;
        }

        Quiz.QuizBuilder quiz = Quiz.builder();

        quiz.quizId( quizDTO.getQuizId() );
        quiz.title( quizDTO.getTitle() );
        quiz.totalQuestions( quizDTO.getTotalQuestions() );
        quiz.passScore( quizDTO.getPassScore() );
        quiz.isPublished( quizDTO.getIsPublished() );

        return quiz.build();
    }

    protected List<Quiz> quizDTOListToQuizList(List<QuizDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Quiz> list1 = new ArrayList<Quiz>( list.size() );
        for ( QuizDTO quizDTO : list ) {
            list1.add( quizDTOToQuiz( quizDTO ) );
        }

        return list1;
    }

    protected CourseLesson lessonRequestToCourseLesson(LessonRequest lessonRequest) {
        if ( lessonRequest == null ) {
            return null;
        }

        CourseLesson.CourseLessonBuilder courseLesson = CourseLesson.builder();

        courseLesson.lessonId( lessonRequest.getLessonId() );
        courseLesson.title( lessonRequest.getTitle() );
        courseLesson.description( lessonRequest.getDescription() );
        courseLesson.lessonType( lessonRequest.getLessonType() );
        courseLesson.content( lessonRequest.getContent() );
        courseLesson.videoUrl( lessonRequest.getVideoUrl() );
        courseLesson.duration( lessonRequest.getDuration() );
        courseLesson.orderIndex( lessonRequest.getOrderIndex() );
        courseLesson.isPreview( lessonRequest.getIsPreview() );
        courseLesson.isPublished( lessonRequest.getIsPublished() );
        courseLesson.codingExercises( codingExerciseDTOListToCodingExerciseList( lessonRequest.getCodingExercises() ) );
        courseLesson.quizzes( quizDTOListToQuizList( lessonRequest.getQuizzes() ) );
        courseLesson.slug( lessonRequest.getSlug() );
        courseLesson.isContest( lessonRequest.getIsContest() );
        courseLesson.totalPoints( lessonRequest.getTotalPoints() );
        courseLesson.contestStartTime( lessonRequest.getContestStartTime() );
        courseLesson.contestEndTime( lessonRequest.getContestEndTime() );

        return courseLesson.build();
    }

    protected List<CourseLesson> lessonRequestListToCourseLessonList(List<LessonRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseLesson> list1 = new ArrayList<CourseLesson>( list.size() );
        for ( LessonRequest lessonRequest : list ) {
            list1.add( lessonRequestToCourseLesson( lessonRequest ) );
        }

        return list1;
    }
}
