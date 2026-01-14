package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseLessonResponse;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseModuleResponse;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.QuizDTO;
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
public class CourseModuleResponseMapperImpl implements CourseModuleResponseMapper {

    @Override
    public List<CourseModule> toEntity(List<CourseModuleResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CourseModule> list = new ArrayList<CourseModule>( dtoList.size() );
        for ( CourseModuleResponse courseModuleResponse : dtoList ) {
            list.add( toEntity( courseModuleResponse ) );
        }

        return list;
    }

    @Override
    public List<CourseModuleResponse> toDto(List<CourseModule> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CourseModuleResponse> list = new ArrayList<CourseModuleResponse>( entityList.size() );
        for ( CourseModule courseModule : entityList ) {
            list.add( toDto( courseModule ) );
        }

        return list;
    }

    @Override
    public Set<CourseModule> toEntity(Set<CourseModuleResponse> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<CourseModule> set = new LinkedHashSet<CourseModule>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( CourseModuleResponse courseModuleResponse : dtoSet ) {
            set.add( toEntity( courseModuleResponse ) );
        }

        return set;
    }

    @Override
    public Set<CourseModuleResponse> toDto(Set<CourseModule> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CourseModuleResponse> set = new LinkedHashSet<CourseModuleResponse>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CourseModule courseModule : entitySet ) {
            set.add( toDto( courseModule ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(CourseModule entity, CourseModuleResponse dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getModuleId() != null ) {
            entity.setModuleId( dto.getModuleId() );
        }
        if ( entity.getLessons() != null ) {
            List<CourseLesson> list = courseLessonResponseListToCourseLessonList( dto.getLessons() );
            if ( list != null ) {
                entity.getLessons().clear();
                entity.getLessons().addAll( list );
            }
        }
        else {
            List<CourseLesson> list = courseLessonResponseListToCourseLessonList( dto.getLessons() );
            if ( list != null ) {
                entity.setLessons( list );
            }
        }
        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getOrderIndex() != null ) {
            entity.setOrderIndex( dto.getOrderIndex() );
        }
        if ( dto.getIsPublished() != null ) {
            entity.setIsPublished( dto.getIsPublished() );
        }
    }

    @Override
    public CourseModuleResponse toDto(CourseModule entity) {
        if ( entity == null ) {
            return null;
        }

        CourseModuleResponse.CourseModuleResponseBuilder courseModuleResponse = CourseModuleResponse.builder();

        courseModuleResponse.moduleId( entity.getModuleId() );
        courseModuleResponse.title( entity.getTitle() );
        courseModuleResponse.orderIndex( entity.getOrderIndex() );
        courseModuleResponse.isPublished( entity.getIsPublished() );
        courseModuleResponse.lessons( courseLessonListToCourseLessonResponseList( entity.getLessons() ) );

        return courseModuleResponse.build();
    }

    @Override
    public CourseModule toEntity(CourseModuleResponse dto) {
        if ( dto == null ) {
            return null;
        }

        CourseModule.CourseModuleBuilder courseModule = CourseModule.builder();

        courseModule.moduleId( dto.getModuleId() );
        courseModule.lessons( courseLessonResponseListToCourseLessonList( dto.getLessons() ) );
        courseModule.title( dto.getTitle() );
        courseModule.orderIndex( dto.getOrderIndex() );
        courseModule.isPublished( dto.getIsPublished() );

        return courseModule.build();
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

    protected CourseLesson courseLessonResponseToCourseLesson(CourseLessonResponse courseLessonResponse) {
        if ( courseLessonResponse == null ) {
            return null;
        }

        CourseLesson.CourseLessonBuilder courseLesson = CourseLesson.builder();

        courseLesson.lessonId( courseLessonResponse.getLessonId() );
        courseLesson.title( courseLessonResponse.getTitle() );
        courseLesson.lessonType( courseLessonResponse.getLessonType() );
        courseLesson.orderIndex( courseLessonResponse.getOrderIndex() );
        courseLesson.isPreview( courseLessonResponse.getIsPreview() );
        courseLesson.isPublished( courseLessonResponse.getIsPublished() );
        courseLesson.codingExercises( codingExerciseDTOListToCodingExerciseList( courseLessonResponse.getCodingExercises() ) );
        courseLesson.quizzes( quizDTOListToQuizList( courseLessonResponse.getQuizzes() ) );

        return courseLesson.build();
    }

    protected List<CourseLesson> courseLessonResponseListToCourseLessonList(List<CourseLessonResponse> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseLesson> list1 = new ArrayList<CourseLesson>( list.size() );
        for ( CourseLessonResponse courseLessonResponse : list ) {
            list1.add( courseLessonResponseToCourseLesson( courseLessonResponse ) );
        }

        return list1;
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

    protected CourseLessonResponse courseLessonToCourseLessonResponse(CourseLesson courseLesson) {
        if ( courseLesson == null ) {
            return null;
        }

        CourseLessonResponse.CourseLessonResponseBuilder courseLessonResponse = CourseLessonResponse.builder();

        courseLessonResponse.lessonId( courseLesson.getLessonId() );
        courseLessonResponse.title( courseLesson.getTitle() );
        courseLessonResponse.orderIndex( courseLesson.getOrderIndex() );
        courseLessonResponse.lessonType( courseLesson.getLessonType() );
        courseLessonResponse.isPreview( courseLesson.getIsPreview() );
        courseLessonResponse.isPublished( courseLesson.getIsPublished() );
        courseLessonResponse.codingExercises( codingExerciseListToCodingExerciseDTOList( courseLesson.getCodingExercises() ) );
        courseLessonResponse.quizzes( quizListToQuizDTOList( courseLesson.getQuizzes() ) );

        return courseLessonResponse.build();
    }

    protected List<CourseLessonResponse> courseLessonListToCourseLessonResponseList(List<CourseLesson> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseLessonResponse> list1 = new ArrayList<CourseLessonResponse>( list.size() );
        for ( CourseLesson courseLesson : list ) {
            list1.add( courseLessonToCourseLessonResponse( courseLesson ) );
        }

        return list1;
    }
}
