package com.truongsonkmhd.unetistudy.dto.response;

import com.truongsonkmhd.unetistudy.common.Gender;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {
    private UUID id;
    private String fistName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String userName;
    private String email;
    private String phone;
    //more
}
