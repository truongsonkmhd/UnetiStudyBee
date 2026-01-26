package com.truongsonkmhd.unetistudy.service.impl.coding;

import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.repository.coding.CodingExerciseRepository;
import com.truongsonkmhd.unetistudy.service.CodingExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CodingExerciseServiceImpl implements CodingExerciseService {

    private final CodingExerciseRepository codingExerciseRepository;

    @Override
    public CodingExercise getExerciseEntityByID(UUID exerciseId) {
        return codingExerciseRepository.getExerciseEntityById(exerciseId);
    }

    @Override
    public UUID getLessonIDByExerciseID(UUID exerciseId) {
        return codingExerciseRepository.getLessonIDByExerciseID(exerciseId);
    }

}
