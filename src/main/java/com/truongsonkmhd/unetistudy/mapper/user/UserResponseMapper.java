package com.truongsonkmhd.unetistudy.mapper.user;

import com.truongsonkmhd.unetistudy.dto.response.user.UserResponse;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserResponseMapper extends EntityMapper<UserResponse, User> {
}
