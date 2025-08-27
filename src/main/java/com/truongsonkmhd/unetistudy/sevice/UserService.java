package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.request.user.UserRequest;
import com.truongsonkmhd.unetistudy.dto.request.user.UserPasswordRequest;
import com.truongsonkmhd.unetistudy.dto.request.user.UserUpdateRequest;
import com.truongsonkmhd.unetistudy.dto.response.user.UserPageResponse;
import com.truongsonkmhd.unetistudy.dto.response.user.UserResponse;
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

    void changePassword(UserPasswordRequest oldPassword);

    void delete(UUID id);
}
