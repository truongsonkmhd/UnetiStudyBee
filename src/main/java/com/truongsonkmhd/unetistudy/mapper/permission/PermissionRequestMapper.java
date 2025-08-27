package com.truongsonkmhd.unetistudy.mapper.permission;

import com.truongsonkmhd.unetistudy.dto.request.permission.PermissionRequest;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionRequestMapper extends EntityMapper<PermissionRequest,Permission> {}
