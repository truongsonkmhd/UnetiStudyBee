package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.class_dto.ClazzResponse;
import com.truongsonkmhd.unetistudy.dto.class_dto.CreateClazzRequest;

import java.util.List;

public interface ClassService {
    ClazzResponse saveClass(CreateClazzRequest createClazzRequest);

    List<ClazzResponse> getALlClass();

}
