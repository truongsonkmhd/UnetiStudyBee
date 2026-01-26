package com.truongsonkmhd.unetistudy.service;

import com.truongsonkmhd.unetistudy.dto.permission_dto.PermissionRequest;
import com.truongsonkmhd.unetistudy.dto.permission_dto.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    PermissionResponse update(long id, PermissionRequest request);

    long delete(long permission);
}
