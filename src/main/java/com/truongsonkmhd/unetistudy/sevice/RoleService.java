package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.request.role.RoleRequest;
import com.truongsonkmhd.unetistudy.dto.response.role.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    long delete(long roleId);
}
