package com.truongsonkmhd.unetistudy.dto.AddressDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDTO {
     String apartmentNumber;
     String floor;
     String building;
     String streetNumber;
     Integer addressType;
     String street;
     String city;
     String country;
}
