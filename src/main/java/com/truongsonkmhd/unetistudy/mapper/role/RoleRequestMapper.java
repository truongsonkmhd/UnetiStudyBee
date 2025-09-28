package com.truongsonkmhd.unetistudy.mapper.role;

import com.truongsonkmhd.unetistudy.dto.RoleDTO.RoleRequest;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.Permission;
import com.truongsonkmhd.unetistudy.model.Role;
import org.mapstruct.*;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleRequestMapper extends EntityMapper<RoleRequest, Role> {
    @Override
    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "mapPermissions")
    @Mapping(target = "users", ignore = true)
    Role toEntity(RoleRequest dto);

    @Override
    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "mapPermissionNames")
    RoleRequest toDto(Role entity);

    @Override
    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "permissions", source = "permissions", qualifiedByName = "mapPermissions")
    @Mapping(target = "users", ignore = true)
    void partialUpdate(@MappingTarget Role entity, RoleRequest dto);

    @Named("mapPermissions")
    default Set<Permission> mapPermissions(Set<String> permissionNames) {
        if (permissionNames == null) {
            return null;
        }
        return permissionNames.stream()
                .map(name -> {
                    Permission permission = new Permission();
                    permission.setName(name);
                    return permission;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapPermissionNames")
    default Set<String> mapPermissionNames(Set<Permission> permissions) {
        if (permissions == null) {
            return null;
        }
        return permissions.stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());
    }
}
