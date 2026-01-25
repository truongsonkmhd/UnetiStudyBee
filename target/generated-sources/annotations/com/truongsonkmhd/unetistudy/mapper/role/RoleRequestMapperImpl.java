package com.truongsonkmhd.unetistudy.mapper.role;

import com.truongsonkmhd.unetistudy.dto.role_dto.RoleRequest;
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
    date = "2026-01-25T12:03:25+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
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
    public Set<Role> toEntity(Set<RoleRequest> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<Role> set = new LinkedHashSet<Role>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( RoleRequest roleRequest : dtoSet ) {
            set.add( toEntity( roleRequest ) );
        }

        return set;
    }

    @Override
    public Set<RoleRequest> toDto(Set<Role> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<RoleRequest> set = new LinkedHashSet<RoleRequest>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( Role role : entitySet ) {
            set.add( toDto( role ) );
        }

        return set;
    }

    @Override
    public Role toEntity(RoleRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.permissions( mapPermissions( dto.getPermissions() ) );
        role.description( dto.getDescription() );
        role.name( dto.getName() );

        return role.build();
    }

    @Override
    public RoleRequest toDto(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleRequest.RoleRequestBuilder roleRequest = RoleRequest.builder();

        roleRequest.permissions( mapPermissionNames( entity.getPermissions() ) );
        roleRequest.description( entity.getDescription() );
        roleRequest.name( entity.getName() );

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
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
    }
}
