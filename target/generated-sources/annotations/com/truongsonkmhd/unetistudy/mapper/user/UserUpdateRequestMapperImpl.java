package com.truongsonkmhd.unetistudy.mapper.user;

import com.truongsonkmhd.unetistudy.dto.AddressDTO.AddressRequest;
import com.truongsonkmhd.unetistudy.dto.UserDTO.UserUpdateRequest;
import com.truongsonkmhd.unetistudy.model.Address;
import com.truongsonkmhd.unetistudy.model.User;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-17T14:24:55+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class UserUpdateRequestMapperImpl implements UserUpdateRequestMapper {

    @Override
    public List<User> toEntity(List<UserUpdateRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserUpdateRequest userUpdateRequest : dtoList ) {
            list.add( toEntity( userUpdateRequest ) );
        }

        return list;
    }

    @Override
    public List<UserUpdateRequest> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserUpdateRequest> list = new ArrayList<UserUpdateRequest>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public Set<User> toEntity(Set<UserUpdateRequest> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<User> set = new LinkedHashSet<User>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( UserUpdateRequest userUpdateRequest : dtoSet ) {
            set.add( toEntity( userUpdateRequest ) );
        }

        return set;
    }

    @Override
    public Set<UserUpdateRequest> toDto(Set<User> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<UserUpdateRequest> set = new LinkedHashSet<UserUpdateRequest>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( User user : entitySet ) {
            set.add( toDto( user ) );
        }

        return set;
    }

    @Override
    public User toEntity(UserUpdateRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.addresses( addressRequestSetToAddressSet( dto.getAddresses() ) );
        user.fullName( dto.getFullName() );
        user.gender( dto.getGender() );
        user.birthday( dto.getBirthday() );
        user.email( dto.getEmail() );
        user.phone( dto.getPhone() );
        user.username( dto.getUsername() );

        return user.build();
    }

    @Override
    public UserUpdateRequest toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();

        if ( userUpdateRequest.getRoles() != null ) {
            List<String> list = mapRolesToDto( entity.getRoles() );
            if ( list != null ) {
                userUpdateRequest.getRoles().addAll( list );
            }
        }
        if ( userUpdateRequest.getAddresses() != null ) {
            Set<AddressRequest> set = addressSetToAddressRequestSet( entity.getAddresses() );
            if ( set != null ) {
                userUpdateRequest.getAddresses().addAll( set );
            }
        }

        return userUpdateRequest;
    }

    @Override
    public void partialUpdate(User user, UserUpdateRequest userUpdateRequest) {
        if ( userUpdateRequest == null ) {
            return;
        }

        if ( user.getAddresses() != null ) {
            Set<Address> set = addressRequestSetToAddressSet( userUpdateRequest.getAddresses() );
            if ( set != null ) {
                user.getAddresses().clear();
                user.getAddresses().addAll( set );
            }
            else {
                user.setAddresses( null );
            }
        }
        else {
            Set<Address> set = addressRequestSetToAddressSet( userUpdateRequest.getAddresses() );
            if ( set != null ) {
                user.setAddresses( set );
            }
        }
        user.setFullName( userUpdateRequest.getFullName() );
        user.setGender( userUpdateRequest.getGender() );
        user.setBirthday( userUpdateRequest.getBirthday() );
        user.setEmail( userUpdateRequest.getEmail() );
        user.setPhone( userUpdateRequest.getPhone() );
        user.setUsername( userUpdateRequest.getUsername() );
    }

    protected Address addressRequestToAddress(AddressRequest addressRequest) {
        if ( addressRequest == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.apartmentNumber( addressRequest.getApartmentNumber() );
        address.floor( addressRequest.getFloor() );
        address.building( addressRequest.getBuilding() );
        address.streetNumber( addressRequest.getStreetNumber() );
        address.street( addressRequest.getStreet() );
        address.city( addressRequest.getCity() );
        address.country( addressRequest.getCountry() );
        address.addressType( addressRequest.getAddressType() );

        return address.build();
    }

    protected Set<Address> addressRequestSetToAddressSet(Set<AddressRequest> set) {
        if ( set == null ) {
            return null;
        }

        Set<Address> set1 = new LinkedHashSet<Address>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( AddressRequest addressRequest : set ) {
            set1.add( addressRequestToAddress( addressRequest ) );
        }

        return set1;
    }

    protected AddressRequest addressToAddressRequest(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressRequest.AddressRequestBuilder addressRequest = AddressRequest.builder();

        addressRequest.apartmentNumber( address.getApartmentNumber() );
        addressRequest.floor( address.getFloor() );
        addressRequest.building( address.getBuilding() );
        addressRequest.streetNumber( address.getStreetNumber() );
        addressRequest.street( address.getStreet() );
        addressRequest.city( address.getCity() );
        addressRequest.country( address.getCountry() );
        addressRequest.addressType( address.getAddressType() );

        return addressRequest.build();
    }

    protected Set<AddressRequest> addressSetToAddressRequestSet(Set<Address> set) {
        if ( set == null ) {
            return null;
        }

        Set<AddressRequest> set1 = new LinkedHashSet<AddressRequest>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Address address : set ) {
            set1.add( addressToAddressRequest( address ) );
        }

        return set1;
    }
}
