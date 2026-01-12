package com.truongsonkmhd.unetistudy.mapper.address;

import com.truongsonkmhd.unetistudy.dto.AddressDTO.AddressDTO;
import com.truongsonkmhd.unetistudy.model.Address;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-13T00:25:40+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Eclipse Adoptium)"
)
@Component
public class AddressDTOMapperImpl implements AddressDTOMapper {

    @Override
    public Address toEntity(AddressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Address.AddressBuilder address = Address.builder();

        address.apartmentNumber( dto.getApartmentNumber() );
        address.floor( dto.getFloor() );
        address.building( dto.getBuilding() );
        address.streetNumber( dto.getStreetNumber() );
        address.street( dto.getStreet() );
        address.city( dto.getCity() );
        address.country( dto.getCountry() );
        address.addressType( dto.getAddressType() );

        return address.build();
    }

    @Override
    public AddressDTO toDto(Address entity) {
        if ( entity == null ) {
            return null;
        }

        AddressDTO.AddressDTOBuilder addressDTO = AddressDTO.builder();

        addressDTO.apartmentNumber( entity.getApartmentNumber() );
        addressDTO.floor( entity.getFloor() );
        addressDTO.building( entity.getBuilding() );
        addressDTO.streetNumber( entity.getStreetNumber() );
        addressDTO.addressType( entity.getAddressType() );
        addressDTO.street( entity.getStreet() );
        addressDTO.city( entity.getCity() );
        addressDTO.country( entity.getCountry() );

        return addressDTO.build();
    }

    @Override
    public List<Address> toEntity(List<AddressDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Address> list = new ArrayList<Address>( dtoList.size() );
        for ( AddressDTO addressDTO : dtoList ) {
            list.add( toEntity( addressDTO ) );
        }

        return list;
    }

    @Override
    public List<AddressDTO> toDto(List<Address> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AddressDTO> list = new ArrayList<AddressDTO>( entityList.size() );
        for ( Address address : entityList ) {
            list.add( toDto( address ) );
        }

        return list;
    }

    @Override
    public Set<Address> toEntity(Set<AddressDTO> dtoSet) {
        if ( dtoSet == null ) {
            return null;
        }

        Set<Address> set = new LinkedHashSet<Address>( Math.max( (int) ( dtoSet.size() / .75f ) + 1, 16 ) );
        for ( AddressDTO addressDTO : dtoSet ) {
            set.add( toEntity( addressDTO ) );
        }

        return set;
    }

    @Override
    public Set<AddressDTO> toDto(Set<Address> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<AddressDTO> set = new LinkedHashSet<AddressDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( Address address : entitySet ) {
            set.add( toDto( address ) );
        }

        return set;
    }

    @Override
    public void partialUpdate(Address entity, AddressDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getApartmentNumber() != null ) {
            entity.setApartmentNumber( dto.getApartmentNumber() );
        }
        if ( dto.getFloor() != null ) {
            entity.setFloor( dto.getFloor() );
        }
        if ( dto.getBuilding() != null ) {
            entity.setBuilding( dto.getBuilding() );
        }
        if ( dto.getStreetNumber() != null ) {
            entity.setStreetNumber( dto.getStreetNumber() );
        }
        if ( dto.getStreet() != null ) {
            entity.setStreet( dto.getStreet() );
        }
        if ( dto.getCity() != null ) {
            entity.setCity( dto.getCity() );
        }
        if ( dto.getCountry() != null ) {
            entity.setCountry( dto.getCountry() );
        }
        if ( dto.getAddressType() != null ) {
            entity.setAddressType( dto.getAddressType() );
        }
    }
}
