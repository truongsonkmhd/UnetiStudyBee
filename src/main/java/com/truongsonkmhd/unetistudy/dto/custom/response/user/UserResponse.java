package com.truongsonkmhd.unetistudy.dto.custom.response.user;

import com.truongsonkmhd.unetistudy.common.Gender;
import com.truongsonkmhd.unetistudy.dto.AddressDTO;
import com.truongsonkmhd.unetistudy.dto.custom.response.role.RoleResponse;
import com.truongsonkmhd.unetistudy.model.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
