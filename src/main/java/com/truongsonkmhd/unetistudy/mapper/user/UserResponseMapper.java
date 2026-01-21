package com.truongsonkmhd.unetistudy.mapper.user;

import com.truongsonkmhd.unetistudy.dto.user_dto.UserResponse;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserResponseMapper extends EntityMapper<UserResponse, User> {
    @Override
    UserResponse toDto(User entity);

    @Override
    User toEntity(UserResponse dto);
}
