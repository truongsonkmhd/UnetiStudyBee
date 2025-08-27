package com.truongsonkmhd.unetistudy.mapper.permission;

import com.truongsonkmhd.unetistudy.dto.request.permission.PermissionRequest;
import com.truongsonkmhd.unetistudy.model.Permission;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-27T14:22:58+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class PermissionRequestMapperImpl implements PermissionRequestMapper {

    @Override
    public Permission toEntity(PermissionRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.name( dto.getName() );
        permission.description( dto.getDescription() );

        return permission.build();
    }

    @Override
    public PermissionRequest toDto(Permission entity) {
        if ( entity == null ) {
            return null;
        }

        PermissionRequest.PermissionRequestBuilder permissionRequest = PermissionRequest.builder();

        permissionRequest.name( entity.getName() );
        permissionRequest.description( entity.getDescription() );

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
    public void partialUpdate(Permission entity, PermissionRequest dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
    }
}
