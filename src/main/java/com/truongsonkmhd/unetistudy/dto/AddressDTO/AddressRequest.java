package com.truongsonkmhd.unetistudy.dto.AddressDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressRequest implements Serializable {
     String apartmentNumber;
     String floor;
     String building;
     String streetNumber;
     String street;
     String city;
     String country;
     Integer addressType;
}
