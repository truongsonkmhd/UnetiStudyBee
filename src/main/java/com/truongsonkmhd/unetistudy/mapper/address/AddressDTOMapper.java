package com.truongsonkmhd.unetistudy.mapper.address;

import com.truongsonkmhd.unetistudy.dto.AddressDTO.AddressDTO;
import com.truongsonkmhd.unetistudy.mapper.EntityMapper;
import com.truongsonkmhd.unetistudy.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressDTOMapper extends EntityMapper<AddressDTO, Address> {
}
