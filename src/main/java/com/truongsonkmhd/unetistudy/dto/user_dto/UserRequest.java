package com.truongsonkmhd.unetistudy.dto.user_dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.truongsonkmhd.unetistudy.common.Gender;
import com.truongsonkmhd.unetistudy.common.UserType;
import com.truongsonkmhd.unetistudy.validator.EnumValue;
import com.truongsonkmhd.unetistudy.validator.GenderSubset;
import com.truongsonkmhd.unetistudy.validator.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static com.truongsonkmhd.unetistudy.common.Gender.*;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest implements Serializable {

    @NotBlank(message = "fullName must be not blank") // Khong cho phep gia tri blank
    String fullName;

    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})// cách 2 validation
    Gender gender;

    @NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    Date birthday;

    @NotNull(message = "username must be not null")
    String userName;

    @NotNull(message = "password must be not null")
    String password;

    @Email(message = "email invalid format") // Chi chap nhan nhung gia tri dung dinh dang email
    //@Pattern(regexp = "^\\d{10}$", message = "phone invalid format")
    String email;

    @PhoneNumber(message = "phone invalid format")
    String phone;

    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = UserType.class)// cách 3 validation
    String type;

    // Địa chỉ liên hệ (không bắt buộc, nhưng nếu có thì không được rỗng)
    @NotBlank(message = "contactAddress must not be blank")
    String contactAddress;

    // Hộ khẩu thường trú (không bắt buộc, nhưng nếu có thì không được rỗng)
    @NotBlank(message = "currentResidence must not be blank")
    String currentResidence;

    // Mã sinh viên UNETI – bắt buộc – đúng 11 chữ số
    @NotBlank(message = "studentID must not be blank")
    @jakarta.validation.constraints.Pattern(
            regexp = "^\\d{11}$",
            message = "studentID must be exactly 11 digits"
    )
    String studentID;

    // Mã lớp UNETI – bắt buộc – ví dụ: DHTI16A3HN
    @NotBlank(message = "classID must not be blank")
    @jakarta.validation.constraints.Pattern(
            regexp = "^(DH|CD|LT|VB2|VLVH|TX)"
                    + "(TI|KT|QTKD|TC|CK|DD|OT|XD|TM)"
                    + "\\d{2}[A-Z]\\d"
                    + "(HN|ND|TH)$",
            message = "Invalid UNETI classID format (e.g. DHTI16A3HN)"
    )
    String classID;

    Set<String> roleCodes;
}
