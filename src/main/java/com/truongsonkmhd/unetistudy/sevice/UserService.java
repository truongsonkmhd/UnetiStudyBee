package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.request.UserRequestDTO;
import com.truongsonkmhd.unetistudy.dto.request.UserPasswordRequestDTO;
import com.truongsonkmhd.unetistudy.dto.request.UserUpdateRequestDTO;
import com.truongsonkmhd.unetistudy.dto.response.UserPageResponse;
import com.truongsonkmhd.unetistudy.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDetailsService userDetailsService();

    UserPageResponse getAllUsersWithSortBy(String sort, int page, int size);

    UserPageResponse getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, List<String> sorts);

    UserResponse findById(UUID id);

    UserResponse findByUsername(String username);

    UserResponse findByEmail(String email);

    UUID saveUser(UserRequestDTO user);

    void update(UUID userId , UserUpdateRequestDTO user);

    void changePassword(UserPasswordRequestDTO oldPassword);

    void delete(UUID id);
}
