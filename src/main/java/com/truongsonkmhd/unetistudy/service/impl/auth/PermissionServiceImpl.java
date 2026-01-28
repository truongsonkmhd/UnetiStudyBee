package com.truongsonkmhd.unetistudy.service.impl.auth;

import com.truongsonkmhd.unetistudy.cache.CacheConstants;
import com.truongsonkmhd.unetistudy.dto.permission_dto.PermissionRequest;
import com.truongsonkmhd.unetistudy.dto.permission_dto.PermissionResponse;
import com.truongsonkmhd.unetistudy.mapper.permission.PermissionRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.permission.PermissionResponseMapper;
import com.truongsonkmhd.unetistudy.model.Permission;
import com.truongsonkmhd.unetistudy.repository.auth.PermissionRepository;
import com.truongsonkmhd.unetistudy.service.PermissionService;
import com.truongsonkmhd.unetistudy.service.impl.BaseCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service quản lý Permission với tích hợp Caching
 * 
 * Cache Patterns áp dụng:
 * 1. Cache-Aside - @Cacheable cho getAll
 * 2. Cache Invalidation - @CacheEvict cho create, update, delete
 * 3. Long-lived Caching - Dữ liệu ít thay đổi, TTL dài (6 giờ)
 */
@Service
@Slf4j
public class PermissionServiceImpl extends BaseCrudService<Permission, Long, PermissionRepository>
        implements PermissionService {

    private final PermissionRequestMapper permissionRequestMapper;
    private final PermissionResponseMapper permissionResponseMapper;

    public PermissionServiceImpl(PermissionRepository repository,
            PermissionRequestMapper permissionRequestMapper,
            PermissionResponseMapper permissionResponseMapper) {
        super(repository, "Permission");
        this.permissionRequestMapper = permissionRequestMapper;
        this.permissionResponseMapper = permissionResponseMapper;
    }

    /**
     * Cache Invalidation: Xóa cache permissions khi tạo mới
     */
    @Override
    @CacheEvict(cacheNames = CacheConstants.PERMISSIONS, allEntries = true)
    public PermissionResponse create(PermissionRequest request) {
        log.info("Creating permission: {} - Evicting cache", request.getName());
        Permission permission = permissionRequestMapper.toEntity(request);
        permission = repository.save(permission);
        return permissionResponseMapper.toDto(permission);
    }

    /**
     * Cache Invalidation: Xóa cache permissions khi cập nhật
     */
    @CacheEvict(cacheNames = CacheConstants.PERMISSIONS, allEntries = true)
    public PermissionResponse update(long id, PermissionRequest request) {
        log.info("Updating permission: {} - Evicting cache", id);
        Permission existing = findByIdOrThrow(id);
        permissionRequestMapper.partialUpdate(existing, request);
        Permission updated = repository.save(existing);
        return permissionResponseMapper.toDto(updated);
    }

    /**
     * Cache-Aside: Lấy tất cả permissions
     * TTL: 6 giờ (cấu hình trong CacheConfiguration cho PERMISSIONS)
     */
    @Override
    @Cacheable(cacheNames = CacheConstants.PERMISSIONS, key = "'all'")
    public List<PermissionResponse> getAll() {
        log.debug("Cache MISS - Loading all permissions from DB");
        return permissionResponseMapper.toDto(repository.findAll());
    }

    /**
     * Cache Invalidation: Xóa cache permissions khi xóa
     */
    @Override
    @CacheEvict(cacheNames = CacheConstants.PERMISSIONS, allEntries = true)
    public long delete(long permissionId) {
        log.info("Deleting permission: {} - Evicting cache", permissionId);
        deleteById(permissionId);
        return permissionId;
    }
}
