package com.truongsonkmhd.unetistudy.dto.AuthDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.truongsonkmhd.unetistudy.common.Gender;
import com.truongsonkmhd.unetistudy.dto.AddressDTO.AddressDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class RegisterDTORequest {

    private String fullName;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String type; // ADMIN | STUDENT | TEACHER
    private Gender gender;

    @NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date birthday;

    private Set<AddressDTO> addresses;

    private Set<String> roleCodes;
}