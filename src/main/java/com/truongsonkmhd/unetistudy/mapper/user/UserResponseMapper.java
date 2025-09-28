package com.truongsonkmhd.unetistudy.mapper.user;

import com.truongsonkmhd.unetistudy.dto.UserDTO.UserResponse;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserResponseMapper extends EntityMapper<UserResponse, User> {
    @Override
    @Mapping(target = "addresses", source = "addresses")
    UserResponse toDto(User entity);

    @Override
    @Mapping(target = "addresses", source = "addresses")
    User toEntity(UserResponse dto);
}
