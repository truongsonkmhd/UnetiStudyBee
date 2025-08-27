package com.truongsonkmhd.unetistudy.mapper.role;

import com.truongsonkmhd.unetistudy.dto.request.role.RoleRequest;
import com.truongsonkmhd.unetistudy.model.Permission;
import com.truongsonkmhd.unetistudy.model.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-27T21:56:37+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class RoleRequestMapperImpl implements RoleRequestMapper {

    @Override
    public List<Role> toEntity(List<RoleRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Role> list = new ArrayList<Role>( dtoList.size() );
        for ( RoleRequest roleRequest : dtoList ) {
            list.add( toEntity( roleRequest ) );
        }

        return list;
    }

    @Override
    public List<RoleRequest> toDto(List<Role> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RoleRequest> list = new ArrayList<RoleRequest>( entityList.size() );
        for ( Role role : entityList ) {
            list.add( toDto( role ) );
        }

        return list;
    }

    @Override
    public Role toEntity(RoleRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.permissions( mapPermissions( dto.getPermissions() ) );
        role.name( dto.getName() );
        role.description( dto.getDescription() );

        return role.build();
    }

    @Override
    public RoleRequest toDto(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleRequest.RoleRequestBuilder roleRequest = RoleRequest.builder();

        roleRequest.permissions( mapPermissionNames( entity.getPermissions() ) );
        roleRequest.name( entity.getName() );
        roleRequest.description( entity.getDescription() );

        return roleRequest.build();
    }

    @Override
    public void partialUpdate(Role entity, RoleRequest dto) {
        if ( dto == null ) {
            return;
        }

        if ( entity.getPermissions() != null ) {
            Set<Permission> set = mapPermissions( dto.getPermissions() );
            if ( set != null ) {
                entity.getPermissions().clear();
                entity.getPermissions().addAll( set );
            }
        }
        else {
            Set<Permission> set = mapPermissions( dto.getPermissions() );
            if ( set != null ) {
                entity.setPermissions( set );
            }
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
    }
}
