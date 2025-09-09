package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.custom.request.user.UserRequest;
import com.truongsonkmhd.unetistudy.dto.custom.request.user.UserPasswordRequest;
import com.truongsonkmhd.unetistudy.dto.custom.request.user.UserUpdateRequest;
import com.truongsonkmhd.unetistudy.dto.custom.response.user.UserPageResponse;
import com.truongsonkmhd.unetistudy.dto.custom.response.user.UserResponse;
import com.truongsonkmhd.unetistudy.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDetailsService userDetailsService();

    UserPageResponse getAllUsersWithSortBy(String sort, int page, int size);

    UserPageResponse getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, List<String> sorts);

    UserResponse findById(UUID id);

    User findByUsername(String username);

    UserResponse findByEmail(String email);

    UUID saveUser(UserRequest user);

    UserResponse update(UUID userId , UserUpdateRequest user);

    UUID changePassword(UserPasswordRequest oldPassword);

    UUID delete(UUID id);
}
