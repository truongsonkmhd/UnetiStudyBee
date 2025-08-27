package com.truongsonkmhd.unetistudy.mapper.role;

import com.truongsonkmhd.unetistudy.dto.response.role.RoleResponse;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleResponseMapper extends EntityMapper<RoleResponse, Role> {}
