package com.truongsonkmhd.unetistudy.sevice.impl.coding;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDetailDTO;
import com.truongsonkmhd.unetistudy.dto.ExerciseTestCasesDTO.ExerciseTestCasesDTO;
import com.truongsonkmhd.unetistudy.mapper.coding_submission.CodingExerciseDetailMapper;
import com.truongsonkmhd.unetistudy.mapper.coding_submission.ExerciseTestCaseMapper;
import com.truongsonkmhd.unetistudy.model.lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.repository.coding.CodingExerciseRepository;
import com.truongsonkmhd.unetistudy.repository.coding.ExerciseTestCaseRepository;
import com.truongsonkmhd.unetistudy.sevice.CodingExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CodingExerciseServiceImpl implements CodingExerciseService {

    private final CodingExerciseRepository codingExerciseRepository;

    private final ExerciseTestCaseRepository exerciseTestCaseRepository;

    private final ExerciseTestCaseMapper exerciseTestCaseMapper;

    private final CodingExerciseDetailMapper codingExerciseDetailMapper;

    @Override
    public List<CodingExerciseDTO> getCodingExerciseDTOByLessonSlug(String theSlug) {
        return codingExerciseRepository.getCodingExerciseByLessonSlug(theSlug);
    }

    @Override
    public CodingExerciseDetailDTO getCodingExerciseDetailDTOByExerciseSlug(String theSlug) {
        CodingExerciseDetailDTO codingExerciseDetailDTO = codingExerciseDetailMapper.toDto(codingExerciseRepository.getCodingExerciseDetailDTOByExerciseSlug(theSlug));
        Set<ExerciseTestCasesDTO> exerciseTestCases = exerciseTestCaseMapper.toDto(exerciseTestCaseRepository.getExerciseTestCasesDTOByExerciseID(codingExerciseDetailDTO.getExerciseID()));
        codingExerciseDetailDTO.setExerciseTestCases(exerciseTestCases);
        return codingExerciseDetailDTO;
    }

    @Override
    public CodingExercise getExerciseEntityByID(UUID exerciseId) {
        return codingExerciseRepository.getExerciseEntityById(exerciseId);
    }

    @Override
    public boolean isExerciseInContestLesson(UUID exerciseId) {
        return codingExerciseRepository.isExerciseInContestLesson(exerciseId);
    }

    @Override
    public UUID getLessonIDByExerciseID(UUID exerciseId) {
        return codingExerciseRepository.getLessonIDByExerciseID(exerciseId);
    }

}
