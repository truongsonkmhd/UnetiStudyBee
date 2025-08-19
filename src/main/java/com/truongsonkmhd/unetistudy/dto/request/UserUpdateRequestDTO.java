package com.truongsonkmhd.unetistudy.dto.request;

import com.truongsonkmhd.unetistudy.common.Gender;
import com.truongsonkmhd.unetistudy.model.Address;
import com.truongsonkmhd.unetistudy.validator.GenderSubset;
import com.truongsonkmhd.unetistudy.validator.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static com.truongsonkmhd.unetistudy.common.Gender.*;

@Getter
public class UserUpdateRequestDTO implements Serializable {
    @NotBlank(message = "fullName must be not blank") // Khong cho phep gia tri blank
    private String fullName;

    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})// cách 2 validation
    private Gender gender;

    @NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date birthday;

    @NotNull(message = "username must be not null")
    private String userName;

    @Email(message = "email invalid format") // Chi chap nhan nhung gia tri dung dinh dang email
    private String email;

    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @NotEmpty(message = "addresses can not empty")
    private Set<AddressRequestDTO> addresses;
}
