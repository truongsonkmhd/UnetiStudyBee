package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.UserDTO.UserRequest;
import com.truongsonkmhd.unetistudy.dto.UserDTO.UserPasswordRequest;
import com.truongsonkmhd.unetistudy.dto.UserDTO.UserUpdateRequest;
import com.truongsonkmhd.unetistudy.dto.UserDTO.UserPageResponse;
import com.truongsonkmhd.unetistudy.dto.UserDTO.UserResponse;
import com.truongsonkmhd.unetistudy.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDetailsService userDetailsService();

    UserPageResponse getAllUsersWithSortBy(String sort, int page, int size);

    UserPageResponse getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, List<String> sorts);

    UserResponse findByIdResponse(UUID id);

    User findById(UUID id);

    User findByUsername(String username);

    UserResponse findByEmail(String email);

    UserResponse saveUser(UserRequest user);

    UserResponse update(UUID userId , UserUpdateRequest user);

    UUID changePassword(UserPasswordRequest oldPassword);

    UUID delete(UUID id);

    UUID findUserIDByUserName(String userName);
}
