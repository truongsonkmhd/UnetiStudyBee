package com.truongsonkmhd.unetistudy.sevice.impl;

import com.truongsonkmhd.unetistudy.dto.custom.request.role.RoleRequest;
import com.truongsonkmhd.unetistudy.dto.custom.response.role.RoleResponse;
import com.truongsonkmhd.unetistudy.mapper.role.RoleRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.role.RoleResponseMapper;
import com.truongsonkmhd.unetistudy.repository.PermissionRepository;
import com.truongsonkmhd.unetistudy.repository.RoleRepository;
import com.truongsonkmhd.unetistudy.sevice.RoleService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;

    PermissionRepository permissionRepository;

    RoleRequestMapper roleRequestMapper;

    RoleResponseMapper roleResponseMapper;

    @Transactional
    @Override
    public RoleResponse create(RoleRequest request){
        var role = roleRequestMapper.toEntity(request);

        var permissions = permissionRepository.findByNames(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleResponseMapper.toDto(role);
    }

    @Override
    public List<RoleResponse> getAll(){
        return roleRepository.findAll()
                .stream()
                .map(roleResponseMapper::toDto)
                .toList();
    }
    @Override
    public long delete(long roleId){
        roleRepository.deleteById(roleId);
        return roleId;
    }


}
