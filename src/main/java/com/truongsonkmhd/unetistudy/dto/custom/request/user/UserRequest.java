package com.truongsonkmhd.unetistudy.dto.custom.request.user;

import com.truongsonkmhd.unetistudy.common.Gender;
import com.truongsonkmhd.unetistudy.common.UserType;
import com.truongsonkmhd.unetistudy.dto.custom.request.AddressRequest;
import com.truongsonkmhd.unetistudy.validator.EnumValue;
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
public class UserRequest implements Serializable {
    @NotBlank(message = "firstName must be not blank") // Khong cho phep gia tri blank
    private String firstName;

    @NotBlank(message = "lastName must be not blank") // Khong cho phep gia tri blank
    private String lastName;

    @NotBlank(message = "fullName must be not blank") // Khong cho phep gia tri blank
    private String fullName;

    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})// cách 2 validation
    private Gender gender;

    @NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date birthday;

    @NotNull(message = "username must be not null")
    private String username;

    @NotNull(message = "password must be not null")
    private String password;

    @Email(message = "email invalid format") // Chi chap nhan nhung gia tri dung dinh dang email
    //@Pattern(regexp = "^\\d{10}$", message = "phone invalid format")
    private String email;

    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = UserType.class)// cách 3 validation
    private String type;

    @NotEmpty(message = "addresses can not empty")
    private Set<AddressRequest> addresses;// home company
}
