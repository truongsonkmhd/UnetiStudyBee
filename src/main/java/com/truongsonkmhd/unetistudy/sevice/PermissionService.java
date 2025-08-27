package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.request.permission.PermissionRequest;
import com.truongsonkmhd.unetistudy.dto.response.permission.PermissionResponse;

import java.util.List;

public interface PermissionService{
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    PermissionResponse update(long id, PermissionRequest request);

    long delete(long permission);
}
