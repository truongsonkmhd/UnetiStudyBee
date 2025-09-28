package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.RoleDTO.RoleRequest;
import com.truongsonkmhd.unetistudy.dto.RoleDTO.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    long delete(long roleId);
}
