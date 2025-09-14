package com.truongsonkmhd.unetistudy.dto.custom.response.user;

import com.truongsonkmhd.unetistudy.dto.custom.response.PageResponseAbstract;
import lombok.*;

import java.io.Serializable;
import java.util.List;
@Setter
@Getter
public class UserPageResponse extends PageResponseAbstract implements Serializable {
    private List<UserResponse> users;
}
