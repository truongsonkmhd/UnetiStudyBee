package com.truongsonkmhd.unetistudy.mapper.permission;

import com.truongsonkmhd.unetistudy.dto.response.permission.PermissionResponse;
import com.truongsonkmhd.unetistudy.model.Permission;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-27T21:56:38+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class PermissionResponseMapperImpl implements PermissionResponseMapper {

    @Override
    public Permission toEntity(PermissionResponse dto) {
        if ( dto == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.name( dto.getName() );
        permission.description( dto.getDescription() );

        return permission.build();
    }

    @Override
    public PermissionResponse toDto(Permission entity) {
        if ( entity == null ) {
            return null;
        }

        PermissionResponse.PermissionResponseBuilder permissionResponse = PermissionResponse.builder();

        permissionResponse.name( entity.getName() );
        permissionResponse.description( entity.getDescription() );

        return permissionResponse.build();
    }

    @Override
    public List<Permission> toEntity(List<PermissionResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Permission> list = new ArrayList<Permission>( dtoList.size() );
        for ( PermissionResponse permissionResponse : dtoList ) {
            list.add( toEntity( permissionResponse ) );
        }

        return list;
    }

    @Override
    public List<PermissionResponse> toDto(List<Permission> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PermissionResponse> list = new ArrayList<PermissionResponse>( entityList.size() );
        for ( Permission permission : entityList ) {
            list.add( toDto( permission ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Permission entity, PermissionResponse dto) {
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
