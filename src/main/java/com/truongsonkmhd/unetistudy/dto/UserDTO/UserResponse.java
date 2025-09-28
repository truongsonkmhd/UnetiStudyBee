package com.truongsonkmhd.unetistudy.dto.UserDTO;

import com.truongsonkmhd.unetistudy.common.Gender;
import com.truongsonkmhd.unetistudy.dto.AddressDTO.AddressDTO;
import com.truongsonkmhd.unetistudy.dto.RoleDTO.RoleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse implements Serializable {
     UUID id;
     String fullName;
     Gender gender;
     Date birthday;
     String username;
     String email;
     String phone;
     Set<RoleResponse> roles;
     Set<AddressDTO> addresses;
}
