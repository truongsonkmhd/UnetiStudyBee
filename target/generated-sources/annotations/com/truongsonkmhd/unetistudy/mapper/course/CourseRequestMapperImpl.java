package com.truongsonkmhd.unetistudy.mapper.course;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseModuleRequest;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseShowRequest;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.QuizDTO;
import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonRequest;
import com.truongsonkmhd.unetistudy.model.course.Course;
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
    date = "2026-01-15T00:06:08+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Eclipse Adoptium)"
)
@Component
public class CourseRequestMapperImpl implements CourseRequestMapper {

    @Override
    public CourseShowRequest toDto(Course entity) {
        if ( entity == null ) {
            return null;
        }

        CourseShowRequest.CourseShowRequestBuilder courseShowRequest = CourseShowRequest.builder();

        courseShowRequest.title( entity.getTitle() );
        courseShowRequest.description( entity.getDescription() );
        courseShowRequest.shortDescription( entity.getShortDescription() );
        courseShowRequest.level( entity.getLevel() );
        courseShowRequest.category( entity.getCategory() );
        courseShowRequest.subCategory( entity.getSubCategory() );
        courseShowRequest.duration( entity.getDuration() );
        courseShowRequest.capacity( entity.getCapacity() );
        courseShowRequest.enrolledCount( entity.getEnrolledCount() );
        courseShowRequest.imageUrl( entity.getImageUrl() );
        courseShowRequest.videoUrl( entity.getVideoUrl() );
        courseShowRequest.requirements( entity.getRequirements() );
        courseShowRequest.objectives( entity.getObjectives() );
        courseShowRequest.status( entity.getStatus() );
        courseShowRequest.syllabus( entity.getSyllabus() );
        courseShowRequest.isPublished( entity.getIsPublished() );
        courseShowRequest.publishedAt( entity.getPublishedAt() );
        courseShowRequest.modules( courseModuleListToCourseModuleRequestList( entity.getModules() ) );

        return courseShowRequest.build();
    }

    @Override
    public List<Course> toEntity(List<CourseShowRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Course> list = new ArrayList<Course>( dtoList.size() );
        for ( CourseShowRequest courseShowRequest : dtoList ) {
            list.add( toEntity( courseShowRequest ) );
        }

        return list;
    }

    @Override
    public List<CourseShowRequest> toDto(List<Course> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CourseShowRequest> list = new ArrayList<CourseShowRequest>( entityList.size() );
        for ( Course course : entityList ) {
            list.add( toDto( course ) );
        }

        return list;
    }

    @Override
    public Set<Course> toEntity(Set<CourseShowRequest> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<Course> set = new LinkedHashSet<Course>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( CourseShowRequest courseShowRequest : dtoSet ) {
            set.add( toEntity( courseShowRequest ) );
        }

        return set;
    }

    @Override
    public Set<CourseShowRequest> toDto(Set<Course> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CourseShowRequest> set = new LinkedHashSet<CourseShowRequest>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( Course course : entitySet ) {
            set.add( toDto( course ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(Course entity, CourseShowRequest dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getShortDescription() != null ) {
            entity.setShortDescription( dto.getShortDescription() );
        }
        if ( entity.getModules() != null ) {
            List<CourseModule> list = courseModuleRequestListToCourseModuleList( dto.getModules() );
            if ( list != null ) {
                entity.getModules().clear();
                entity.getModules().addAll( list );
            }
        }
        else {
            List<CourseModule> list = courseModuleRequestListToCourseModuleList( dto.getModules() );
            if ( list != null ) {
                entity.setModules( list );
            }
        }
        if ( dto.getLevel() != null ) {
            entity.setLevel( dto.getLevel() );
        }
        if ( dto.getCategory() != null ) {
            entity.setCategory( dto.getCategory() );
        }
        if ( dto.getSubCategory() != null ) {
            entity.setSubCategory( dto.getSubCategory() );
        }
        if ( dto.getDuration() != null ) {
            entity.setDuration( dto.getDuration() );
        }
        if ( dto.getCapacity() != null ) {
            entity.setCapacity( dto.getCapacity() );
        }
        if ( dto.getEnrolledCount() != null ) {
            entity.setEnrolledCount( dto.getEnrolledCount() );
        }
        if ( dto.getImageUrl() != null ) {
            entity.setImageUrl( dto.getImageUrl() );
        }
        if ( dto.getVideoUrl() != null ) {
            entity.setVideoUrl( dto.getVideoUrl() );
        }
        if ( dto.getRequirements() != null ) {
            entity.setRequirements( dto.getRequirements() );
        }
        if ( dto.getObjectives() != null ) {
            entity.setObjectives( dto.getObjectives() );
        }
        if ( dto.getSyllabus() != null ) {
            entity.setSyllabus( dto.getSyllabus() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getIsPublished() != null ) {
            entity.setIsPublished( dto.getIsPublished() );
        }
        if ( dto.getPublishedAt() != null ) {
            entity.setPublishedAt( dto.getPublishedAt() );
        }
    }

    @Override
    public Course toEntity(CourseShowRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Course.CourseBuilder course = Course.builder();

        course.title( dto.getTitle() );
        course.description( dto.getDescription() );
        course.shortDescription( dto.getShortDescription() );
        course.level( dto.getLevel() );
        course.category( dto.getCategory() );
        course.subCategory( dto.getSubCategory() );
        course.duration( dto.getDuration() );
        course.capacity( dto.getCapacity() );
        course.imageUrl( dto.getImageUrl() );
        course.videoUrl( dto.getVideoUrl() );
        course.requirements( dto.getRequirements() );
        course.objectives( dto.getObjectives() );
        course.syllabus( dto.getSyllabus() );

        course.enrolledCount( 0 );
        course.rating( java.math.BigDecimal.ZERO );
        course.ratingCount( 0 );
        course.status( dto.getStatus() != null ? dto.getStatus() : "draft" );
        course.isPublished( dto.getIsPublished() != null ? dto.getIsPublished() : false );
        course.publishedAt( dto.getIsPublished() != null && dto.getIsPublished() ? dto.getPublishedAt() : null );

        return course.build();
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

    protected List<LessonRequest> courseLessonSetToLessonRequestList(Set<CourseLesson> set) {
        if ( set == null ) {
            return null;
        }

        List<LessonRequest> list = new ArrayList<LessonRequest>( set.size() );
        for ( CourseLesson courseLesson : set ) {
            list.add( courseLessonToLessonRequest( courseLesson ) );
        }

        return list;
    }

    protected CourseModuleRequest courseModuleToCourseModuleRequest(CourseModule courseModule) {
        if ( courseModule == null ) {
            return null;
        }

        CourseModuleRequest.CourseModuleRequestBuilder courseModuleRequest = CourseModuleRequest.builder();

        courseModuleRequest.moduleId( courseModule.getModuleId() );
        courseModuleRequest.title( courseModule.getTitle() );
        courseModuleRequest.description( courseModule.getDescription() );
        courseModuleRequest.orderIndex( courseModule.getOrderIndex() );
        courseModuleRequest.duration( courseModule.getDuration() );
        courseModuleRequest.isPublished( courseModule.getIsPublished() );
        courseModuleRequest.slug( courseModule.getSlug() );
        courseModuleRequest.lessons( courseLessonSetToLessonRequestList( courseModule.getLessons() ) );

        return courseModuleRequest.build();
    }

    protected List<CourseModuleRequest> courseModuleListToCourseModuleRequestList(List<CourseModule> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseModuleRequest> list1 = new ArrayList<CourseModuleRequest>( list.size() );
        for ( CourseModule courseModule : list ) {
            list1.add( courseModuleToCourseModuleRequest( courseModule ) );
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

    protected Set<CourseLesson> lessonRequestListToCourseLessonSet(List<LessonRequest> list) {
        if ( list == null ) {
            return null;
        }

        Set<CourseLesson> set = new LinkedHashSet<CourseLesson>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( LessonRequest lessonRequest : list ) {
            set.add( lessonRequestToCourseLesson( lessonRequest ) );
        }

        return set;
    }

    protected CourseModule courseModuleRequestToCourseModule(CourseModuleRequest courseModuleRequest) {
        if ( courseModuleRequest == null ) {
            return null;
        }

        CourseModule.CourseModuleBuilder courseModule = CourseModule.builder();

        courseModule.moduleId( courseModuleRequest.getModuleId() );
        courseModule.lessons( lessonRequestListToCourseLessonSet( courseModuleRequest.getLessons() ) );
        courseModule.title( courseModuleRequest.getTitle() );
        courseModule.description( courseModuleRequest.getDescription() );
        courseModule.orderIndex( courseModuleRequest.getOrderIndex() );
        courseModule.duration( courseModuleRequest.getDuration() );
        courseModule.isPublished( courseModuleRequest.getIsPublished() );
        courseModule.slug( courseModuleRequest.getSlug() );

        return courseModule.build();
    }

    protected List<CourseModule> courseModuleRequestListToCourseModuleList(List<CourseModuleRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseModule> list1 = new ArrayList<CourseModule>( list.size() );
        for ( CourseModuleRequest courseModuleRequest : list ) {
            list1.add( courseModuleRequestToCourseModule( courseModuleRequest ) );
        }

        return list1;
    }
}
