package com.truongsonkmhd.unetistudy.mapper.address;

import com.truongsonkmhd.unetistudy.dto.AddressDTO;
import com.truongsonkmhd.unetistudy.dto.custom.response.lesson.LessonResponse;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.Address;
import com.truongsonkmhd.unetistudy.model.CourseLesson;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressDTOMapper extends EntityMapper<AddressDTO, Address> {
}
