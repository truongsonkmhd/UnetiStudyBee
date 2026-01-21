package com.truongsonkmhd.unetistudy.sevice.impl.auth;

import com.truongsonkmhd.unetistudy.dto.permission_dto.PermissionRequest;
import com.truongsonkmhd.unetistudy.dto.permission_dto.PermissionResponse;
import com.truongsonkmhd.unetistudy.exception.ResourceNotFoundException;
import com.truongsonkmhd.unetistudy.mapper.permission.PermissionRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.permission.PermissionResponseMapper;
import com.truongsonkmhd.unetistudy.model.Permission;
import com.truongsonkmhd.unetistudy.repository.auth.PermissionRepository;
import com.truongsonkmhd.unetistudy.sevice.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRequestMapper permissionRequestMapper;
    private final PermissionResponseMapper permissionResponseMapper;
    private final PermissionRepository permissionRepository;

    @Override
    public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionRequestMapper.toEntity(request);
        permission = permissionRepository.save(permission);
        return permissionResponseMapper.toDto(permission);
    }


    public PermissionResponse update(long id, PermissionRequest request){
        // 1. Tìm entity hiện có
        Permission existing = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id = " + id));
        // 2. Áp dụng update (chỉ field khác null sẽ override)
        permissionRequestMapper.partialUpdate(existing, request);
        // 3. Lưu lại
        Permission updated = permissionRepository.save(existing);
        // 4. Trả về response
        return permissionResponseMapper.toDto(updated);
    }
    @Override
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissionResponseMapper.toDto(permissions);
    }

    public long delete(long permissionId){
        permissionRepository.deleteById(permissionId);
        return permissionId;
    }

}
