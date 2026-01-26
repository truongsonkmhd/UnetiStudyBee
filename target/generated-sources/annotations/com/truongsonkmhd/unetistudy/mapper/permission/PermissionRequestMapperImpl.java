package com.truongsonkmhd.unetistudy.mapper.permission;

import com.truongsonkmhd.unetistudy.dto.permission_dto.PermissionRequest;
import com.truongsonkmhd.unetistudy.model.Permission;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-26T11:45:14+0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260101-2150, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class PermissionRequestMapperImpl implements PermissionRequestMapper {

    @Override
    public Permission toEntity(PermissionRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.description( dto.getDescription() );
        permission.name( dto.getName() );

        return permission.build();
    }

    @Override
    public PermissionRequest toDto(Permission entity) {
        if ( entity == null ) {
            return null;
        }

        PermissionRequest.PermissionRequestBuilder permissionRequest = PermissionRequest.builder();

        permissionRequest.description( entity.getDescription() );
        permissionRequest.name( entity.getName() );

        return permissionRequest.build();
    }

    @Override
    public List<Permission> toEntity(List<PermissionRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Permission> list = new ArrayList<Permission>( dtoList.size() );
        for ( PermissionRequest permissionRequest : dtoList ) {
            list.add( toEntity( permissionRequest ) );
        }

        return list;
    }

    @Override
    public List<PermissionRequest> toDto(List<Permission> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PermissionRequest> list = new ArrayList<PermissionRequest>( entityList.size() );
        for ( Permission permission : entityList ) {
            list.add( toDto( permission ) );
        }

        return list;
    }

    @Override
    public Set<Permission> toEntity(Set<PermissionRequest> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<Permission> set = new LinkedHashSet<Permission>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( PermissionRequest permissionRequest : dtoSet ) {
            set.add( toEntity( permissionRequest ) );
        }

        return set;
    }

    @Override
    public Set<PermissionRequest> toDto(Set<Permission> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<PermissionRequest> set = new LinkedHashSet<PermissionRequest>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( Permission permission : entitySet ) {
            set.add( toDto( permission ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(Permission entity, PermissionRequest dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
    }
}
