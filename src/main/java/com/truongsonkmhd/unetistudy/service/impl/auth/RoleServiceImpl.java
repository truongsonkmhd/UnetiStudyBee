package com.truongsonkmhd.unetistudy.service.impl.auth;

import com.truongsonkmhd.unetistudy.dto.role_dto.RoleRequest;
import com.truongsonkmhd.unetistudy.dto.role_dto.RoleResponse;
import com.truongsonkmhd.unetistudy.mapper.role.RoleRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.role.RoleResponseMapper;
import com.truongsonkmhd.unetistudy.model.Role;
import com.truongsonkmhd.unetistudy.repository.auth.PermissionRepository;
import com.truongsonkmhd.unetistudy.repository.auth.RoleRepository;
import com.truongsonkmhd.unetistudy.service.RoleService;
import com.truongsonkmhd.unetistudy.service.impl.BaseCrudService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl extends BaseCrudService<Role, Long, RoleRepository> implements RoleService {

    private final PermissionRepository permissionRepository;
    private final RoleRequestMapper roleRequestMapper;
    private final RoleResponseMapper roleResponseMapper;

    public RoleServiceImpl(RoleRepository repository,
            PermissionRepository permissionRepository,
            RoleRequestMapper roleRequestMapper,
            RoleResponseMapper roleResponseMapper) {
        super(repository, "Role");
        this.permissionRepository = permissionRepository;
        this.roleRequestMapper = roleRequestMapper;
        this.roleResponseMapper = roleResponseMapper;
    }

    @Transactional
    @Override
    public RoleResponse create(RoleRequest request) {
        var role = roleRequestMapper.toEntity(request);

        var permissions = permissionRepository.findByNames(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = repository.save(role);
        return roleResponseMapper.toDto(role);
    }

    @Override
    public List<RoleResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(roleResponseMapper::toDto)
                .toList();
    }

    @Override
    public long delete(long roleId) {
        deleteById(roleId);
        return roleId;
    }
}
