package com.truongsonkmhd.unetistudy.dto;
import com.truongsonkmhd.unetistudy.common.Gender;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtUserInfo implements Serializable {
    private String  userId;
    private String gender;
    private String  birthday;
    private String email;
    private String phone;
    private String username;
    private String fullName;
    private String avatar;
    private String classId;
    private String studentId;
    private String contactAddress;
    private String currentResidence;
}
