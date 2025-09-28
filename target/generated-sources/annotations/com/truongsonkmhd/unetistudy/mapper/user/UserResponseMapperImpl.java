package com.truongsonkmhd.unetistudy.mapper.user;

import com.truongsonkmhd.unetistudy.dto.AddressDTO.AddressDTO;
import com.truongsonkmhd.unetistudy.dto.PermissionDTO.PermissionResponse;
import com.truongsonkmhd.unetistudy.dto.RoleDTO.RoleResponse;
import com.truongsonkmhd.unetistudy.dto.UserDTO.UserResponse;
import com.truongsonkmhd.unetistudy.model.Address;
import com.truongsonkmhd.unetistudy.model.Permission;
import com.truongsonkmhd.unetistudy.model.Role;
import com.truongsonkmhd.unetistudy.model.User;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-28T15:37:54+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class UserResponseMapperImpl implements UserResponseMapper {

    @Override
    public List<User> toEntity(List<UserResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserResponse userResponse : dtoList ) {
            list.add( toEntity( userResponse ) );
        }

        return list;
    }

    @Override
    public List<UserResponse> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserResponse> list = new ArrayList<UserResponse>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public Set<User> toEntity(Set<UserResponse> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<User> set = new LinkedHashSet<User>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( UserResponse userResponse : dtoSet ) {
            set.add( toEntity( userResponse ) );
        }

        return set;
    }

    @Override
    public Set<UserResponse> toDto(Set<User> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<UserResponse> set = new LinkedHashSet<UserResponse>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( User user : entitySet ) {
            set.add( toDto( user ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(User entity, UserResponse dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getFullName() != null ) {
            entity.setFullName( dto.getFullName() );
        }
        if ( dto.getGender() != null ) {
            entity.setGender( dto.getGender() );
        }
        if ( dto.getBirthday() != null ) {
            entity.setBirthday( dto.getBirthday() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getPhone() != null ) {
            entity.setPhone( dto.getPhone() );
        }
        if ( dto.getUsername() != null ) {
            entity.setUsername( dto.getUsername() );
        }
        if ( entity.getAddresses() != null ) {
            Set<Address> set = addressDTOSetToAddressSet( dto.getAddresses() );
            if ( set != null ) {
                entity.getAddresses().clear();
                entity.getAddresses().addAll( set );
            }
        }
        else {
            Set<Address> set = addressDTOSetToAddressSet( dto.getAddresses() );
            if ( set != null ) {
                entity.setAddresses( set );
            }
        }
        if ( entity.getRoles() != null ) {
            Set<Role> set1 = roleResponseSetToRoleSet( dto.getRoles() );
            if ( set1 != null ) {
                entity.getRoles().clear();
                entity.getRoles().addAll( set1 );
            }
        }
        else {
            Set<Role> set1 = roleResponseSetToRoleSet( dto.getRoles() );
            if ( set1 != null ) {
                entity.setRoles( set1 );
            }
        }
    }

    @Override
    public UserResponse toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.addresses( addressSetToAddressDTOSet( entity.getAddresses() ) );
        userResponse.id( entity.getId() );
        userResponse.fullName( entity.getFullName() );
        userResponse.gender( entity.getGender() );
        userResponse.birthday( entity.getBirthday() );
        userResponse.username( entity.getUsername() );
        userResponse.email( entity.getEmail() );
        userResponse.phone( entity.getPhone() );
        userResponse.roles( roleSetToRoleResponseSet( entity.getRoles() ) );

        return userResponse.build();
    }

    @Override
    public User toEntity(UserResponse dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.addresses( addressDTOSetToAddressSet1( dto.getAddresses() ) );
        user.id( dto.getId() );
        user.fullName( dto.getFullName() );
        user.gender( dto.getGender() );
        user.birthday( dto.getBirthday() );
        user.email( dto.getEmail() );
        user.phone( dto.getPhone() );
        user.username( dto.getUsername() );
        user.roles( roleResponseSetToRoleSet( dto.getRoles() ) );

        return user.build();
    }

    protected Address addressDTOToAddress(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.apartmentNumber( addressDTO.getApartmentNumber() );
        address.floor( addressDTO.getFloor() );
        address.building( addressDTO.getBuilding() );
        address.streetNumber( addressDTO.getStreetNumber() );
        address.street( addressDTO.getStreet() );
        address.city( addressDTO.getCity() );
        address.country( addressDTO.getCountry() );

        return address.build();
    }

    protected Set<Address> addressDTOSetToAddressSet(Set<AddressDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Address> set1 = new LinkedHashSet<Address>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AddressDTO addressDTO : set ) {
            set1.add( addressDTOToAddress( addressDTO ) );
        }

        return set1;
    }

    protected Permission permissionResponseToPermission(PermissionResponse permissionResponse) {
        if ( permissionResponse == null ) {
            return null;
        }

        Permission.PermissionBuilder permission = Permission.builder();

        permission.name( permissionResponse.getName() );
        permission.description( permissionResponse.getDescription() );

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

    protected Role roleResponseToRole(RoleResponse roleResponse) {
        if ( roleResponse == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.name( roleResponse.getName() );
        role.description( roleResponse.getDescription() );
        role.permissions( permissionResponseSetToPermissionSet( roleResponse.getPermissions() ) );

        return role.build();
    }

    protected Set<Role> roleResponseSetToRoleSet(Set<RoleResponse> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new LinkedHashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleResponse roleResponse : set ) {
            set1.add( roleResponseToRole( roleResponse ) );
        }

        return set1;
    }

    protected AddressDTO addressToAddressDTO(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDTO.AddressDTOBuilder addressDTO = AddressDTO.builder();

        addressDTO.apartmentNumber( address.getApartmentNumber() );
        addressDTO.floor( address.getFloor() );
        addressDTO.building( address.getBuilding() );
        addressDTO.streetNumber( address.getStreetNumber() );
        addressDTO.street( address.getStreet() );
        addressDTO.city( address.getCity() );
        addressDTO.country( address.getCountry() );

        return addressDTO.build();
    }

    protected Set<AddressDTO> addressSetToAddressDTOSet(Set<Address> set) {
        if ( set == null ) {
            return null;
        }

        Set<AddressDTO> set1 = new LinkedHashSet<AddressDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Address address : set ) {
            set1.add( addressToAddressDTO( address ) );
        }

        return set1;
    }

    protected PermissionResponse permissionToPermissionResponse(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionResponse.PermissionResponseBuilder permissionResponse = PermissionResponse.builder();

        permissionResponse.name( permission.getName() );
        permissionResponse.description( permission.getDescription() );

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

    protected RoleResponse roleToRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.name( role.getName() );
        roleResponse.description( role.getDescription() );
        roleResponse.permissions( permissionSetToPermissionResponseSet( role.getPermissions() ) );

        return roleResponse.build();
    }

    protected Set<RoleResponse> roleSetToRoleResponseSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleResponse> set1 = new LinkedHashSet<RoleResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleResponse( role ) );
        }

        return set1;
    }

    protected Set<Address> addressDTOSetToAddressSet1(Set<AddressDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Address> set1 = new LinkedHashSet<Address>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AddressDTO addressDTO : set ) {
            set1.add( addressDTOToAddress( addressDTO ) );
        }

        return set1;
    }
}
