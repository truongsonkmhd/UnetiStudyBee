package com.truongsonkmhd.unetistudy.controller;

import com.truongsonkmhd.unetistudy.context.UserContext;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDetailDTO;
import com.truongsonkmhd.unetistudy.dto.CodingSubmission.CodingSubmissionShowDTO;
import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.sevice.CodingExerciseService;
import com.truongsonkmhd.unetistudy.sevice.CodingSubmissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/practice/lesson")
@Slf4j(topic = "PRACTICE-LESSON-CONTROLLER")
@Tag(name = "practice lesson controller")
@RequiredArgsConstructor
public class CodingExerciseController {

    private final CodingExerciseService codingExerciseService;

    private final CodingSubmissionService codingSubmissionService;


    @GetMapping("/{lesson-slug}")
    public ResponseEntity<IResponseMessage> showCodingExerciseByLessonSlug(@PathVariable("lesson-slug") String theSlug){
        List<CodingExerciseDTO> exercises = codingExerciseService.getCodingExerciseDTOByLessonSlug(theSlug);
        return  ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(exercises));
    }

    @GetMapping("/problem/{slug}")
    public  ResponseEntity<IResponseMessage> showExerciseDetailBySlug(@PathVariable("slug") String theSlug){
        CodingExerciseDetailDTO exercise = codingExerciseService.getCodingExerciseDetailDTOByExerciseSlug(theSlug);
        return  ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(exercise));
    }

    @GetMapping("/submissions/{slug}")
    public ResponseEntity<IResponseMessage> showExerciseSubmissions(@PathVariable("slug") String theSlug){
        List<CodingSubmissionShowDTO> submissions = codingSubmissionService.getCodingSubmissionShowByUserName(UserContext.getUsername(), theSlug);
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(submissions));
    }

    @GetMapping("/leaderboard/{slug}")
    public  ResponseEntity<IResponseMessage> showLeaderBoard(@PathVariable("slug") String theSlug){
        List<CodingSubmissionShowDTO> submissions = codingSubmissionService.getCodingSubmissionShowBySlugExercise(theSlug);
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(submissions));
    }

    @GetMapping("/tutorial/{slug}")
    public String showTutorial(@PathVariable("slug") String theSlug){
        return "ClientTemplates/coding-exercise/tutorial";
    }


}
