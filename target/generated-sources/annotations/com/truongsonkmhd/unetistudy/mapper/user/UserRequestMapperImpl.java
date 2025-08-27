package com.truongsonkmhd.unetistudy.mapper.user;

import com.truongsonkmhd.unetistudy.common.UserType;
import com.truongsonkmhd.unetistudy.dto.request.AddressRequest;
import com.truongsonkmhd.unetistudy.dto.request.user.UserRequest;
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
    date = "2025-08-27T14:22:58+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class UserRequestMapperImpl implements UserRequestMapper {

    @Override
    public User toEntity(UserRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.fullName( dto.getFullName() );
        user.gender( dto.getGender() );
        user.birthday( dto.getBirthday() );
        user.email( dto.getEmail() );
        user.phone( dto.getPhone() );
        user.username( dto.getUsername() );
        user.password( dto.getPassword() );
        if ( dto.getType() != null ) {
            user.type( Enum.valueOf( UserType.class, dto.getType() ) );
        }
        user.addresses( addressRequestSetToAddressSet( dto.getAddresses() ) );

        return user.build();
    }

    @Override
    public UserRequest toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserRequest userRequest = new UserRequest();

        if ( userRequest.getAddresses() != null ) {
            Set<AddressRequest> set = addressSetToAddressRequestSet( entity.getAddresses() );
            if ( set != null ) {
                userRequest.getAddresses().addAll( set );
            }
        }

        return userRequest;
    }

    @Override
    public List<User> toEntity(List<UserRequest> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserRequest userRequest : dtoList ) {
            list.add( toEntity( userRequest ) );
        }

        return list;
    }

    @Override
    public List<UserRequest> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserRequest> list = new ArrayList<UserRequest>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(User entity, UserRequest dto) {
        if ( dto == null ) {
            return;
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
        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getType() != null ) {
            entity.setType( Enum.valueOf( UserType.class, dto.getType() ) );
        }
        if ( entity.getAddresses() != null ) {
            Set<Address> set = addressRequestSetToAddressSet( dto.getAddresses() );
            if ( set != null ) {
                entity.getAddresses().clear();
                entity.getAddresses().addAll( set );
            }
        }
        else {
            Set<Address> set = addressRequestSetToAddressSet( dto.getAddresses() );
            if ( set != null ) {
                entity.setAddresses( set );
            }
        }
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

        AddressRequest addressRequest = new AddressRequest();

        addressRequest.setApartmentNumber( address.getApartmentNumber() );
        addressRequest.setFloor( address.getFloor() );
        addressRequest.setBuilding( address.getBuilding() );
        addressRequest.setStreetNumber( address.getStreetNumber() );
        addressRequest.setStreet( address.getStreet() );
        addressRequest.setCity( address.getCity() );
        addressRequest.setCountry( address.getCountry() );
        addressRequest.setAddressType( address.getAddressType() );

        return addressRequest;
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
