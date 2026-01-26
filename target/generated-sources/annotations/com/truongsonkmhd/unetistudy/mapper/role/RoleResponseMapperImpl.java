package com.truongsonkmhd.unetistudy.mapper.role;

import com.truongsonkmhd.unetistudy.dto.permission_dto.PermissionResponse;
import com.truongsonkmhd.unetistudy.dto.role_dto.RoleResponse;
import com.truongsonkmhd.unetistudy.model.Permission;
import com.truongsonkmhd.unetistudy.model.Role;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-26T11:45:15+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class RoleResponseMapperImpl implements RoleResponseMapper {

    @Override
    public Role toEntity(RoleResponse dto) {
        if ( dto == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.description( dto.getDescription() );
        role.name( dto.getName() );
        role.permissions( permissionResponseSetToPermissionSet( dto.getPermissions() ) );

        return role.build();
    }

    @Override
    public RoleResponse toDto(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.description( entity.getDescription() );
        roleResponse.name( entity.getName() );
        roleResponse.permissions( permissionSetToPermissionResponseSet( entity.getPermissions() ) );

        return roleResponse.build();
    }

    @Override
    public List<Role> toEntity(List<RoleResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( dtoList.size() );
        for ( RoleResponse roleResponse : dtoList ) {
            list.add( toEntity( roleResponse ) );
        }

        return list;
    }

    @Override
    public List<RoleResponse> toDto(List<Role> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RoleResponse> list = new ArrayList<RoleResponse>( entityList.size() );
        for ( Role role : entityList ) {
            list.add( toDto( role ) );
        }

        return list;
    }

    @Override
    public Set<Role> toEntity(Set<RoleResponse> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<Role> set = new LinkedHashSet<Role>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( RoleResponse roleResponse : dtoSet ) {
            set.add( toEntity( roleResponse ) );
        }

        return set;
    }

    @Override
    public Set<RoleResponse> toDto(Set<Role> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<RoleResponse> set = new LinkedHashSet<RoleResponse>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( Role role : entitySet ) {
            set.add( toDto( role ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(Role entity, RoleResponse dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( entity.getPermissions() != null ) {
            Set<Permission> set = permissionResponseSetToPermissionSet( dto.getPermissions() );
            if ( set != null ) {
                entity.getPermissions().clear();
                entity.getPermissions().addAll( set );
            }
        }
        else {
            Set<Permission> set = permissionResponseSetToPermissionSet( dto.getPermissions() );
            if ( set != null ) {
                entity.setPermissions( set );
            }
        }
    }

    protected Permission permissionResponseToPermission(PermissionResponse permissionResponse) {
        if ( permissionResponse == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.description( permissionResponse.getDescription() );
        permission.name( permissionResponse.getName() );

        return permission.build();
    }

    protected Set<Permission> permissionResponseSetToPermissionSet(Set<PermissionResponse> set) {
        if ( set == null ) {
            return null;
        }

        Set<Permission> set1 = new LinkedHashSet<Permission>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PermissionResponse permissionResponse : set ) {
            set1.add( permissionResponseToPermission( permissionResponse ) );
        }

        return set1;
    }

    protected PermissionResponse permissionToPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse.PermissionResponseBuilder permissionResponse = PermissionResponse.builder();

        permissionResponse.description( permission.getDescription() );
        permissionResponse.name( permission.getName() );

        return permissionResponse.build();
    }

    protected Set<PermissionResponse> permissionSetToPermissionResponseSet(Set<Permission> set) {
        if ( set == null ) {
            return null;
        }

        Set<PermissionResponse> set1 = new LinkedHashSet<PermissionResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Permission permission : set ) {
            set1.add( permissionToPermissionResponse( permission ) );
        }

        return set1;
    }
}
