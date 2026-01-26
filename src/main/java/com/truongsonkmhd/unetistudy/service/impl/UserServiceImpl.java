package com.truongsonkmhd.unetistudy.service.impl;

import com.truongsonkmhd.unetistudy.common.UserStatus;
import com.truongsonkmhd.unetistudy.common.UserType;
import com.truongsonkmhd.unetistudy.dto.user_dto.*;
import com.truongsonkmhd.unetistudy.exception.custom_exception.ResourceNotFoundException;
import com.truongsonkmhd.unetistudy.mapper.user.UserRequestMapper;
import com.truongsonkmhd.unetistudy.mapper.user.UserResponseMapper;
import com.truongsonkmhd.unetistudy.mapper.user.UserUpdateRequestMapper;
import com.truongsonkmhd.unetistudy.model.Role;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.repository.auth.RoleRepository;
import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.security.MyUserDetail;
import com.truongsonkmhd.unetistudy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truongsonkmhd.unetistudy.utils.SortBuilder;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserResponseMapper userResponseMapper;

    private final UserRequestMapper userRequestMapper;

    private final UserUpdateRequestMapper userUpdateRequestMapper;

    @Override
    public UserDetailsService userDetailsService() {

        return username -> userRepository.findByUsername(username)
                .map(MyUserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserPageResponse getAllUsersWithSortBy(String sortBy, int pageNo, int pageSize) {

        // xu ly truong hop FE muon bat dau voi page = 1
        Pageable pageable = PageRequest.of(pageNo, pageSize, SortBuilder.parse(sortBy));
        Page<User> users = userRepository.findAll(pageable);

        return getUserPageResponse(pageNo, pageSize, users);
    }

    @Override
    public UserPageResponse getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, List<String> sorts) {
        // xu ly truong hop FE muon bat dau voi page = 1
        Pageable pageable = PageRequest.of(pageNo, pageSize, SortBuilder.parse(sorts));

        Page<User> users = userRepository.findAll(pageable);

        return getUserPageResponse(pageNo, pageSize, users);
    }

    /**
     * Convert UserEntities to user
     *
     * @param page
     * @param pageSize
     * @param userEntities
     * @return
     */
    private UserPageResponse getUserPageResponse(int page, int pageSize, Page<User> userEntities) {
        log.info("Convert User Entity Page");
        List<UserResponse> userList = userEntities.stream().map(entity -> UserResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .gender(entity.getGender())
                .birthday(entity.getBirthday())
                .username(entity.getUsername())
                .phone(entity.getPhone())
                .classID(entity.getClassId())
                .currentResidence(entity.getCurrentResidence())
                .contactAddress(entity.getContactAddress())
                .studentID(entity.getStudentId())
                .email(entity.getEmail())
                .build()).toList();

        UserPageResponse response = new UserPageResponse();
        response.setPageNumber(page);
        response.setPageSize(pageSize);
        response.setTotalElements(userEntities.getTotalElements());
        response.setTotalPages(userEntities.getTotalPages());
        response.setUsers(userList);
        return response;
    }

    @Override
    public UserResponse findByIdResponse(UUID id) {

        return userResponseMapper.toDto(getUserEntity(id));
    }

    @Override
    public User findById(UUID id) {

        return getUserEntity(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserResponse findByEmail(String email) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse saveUser(UserRequest req) {

        Set<String> roleCodes = req.getRoleCodes();

        if (roleCodes == null || roleCodes.isEmpty()) {
            // Nếu client không gửi roles, mặc định dùng STUDENT
            roleCodes = Set.of(UserType.STUDENT.getValue());
        }

        List<Role> roles = roleRepository.findAllByCodes(roleCodes);

        if (roles.size() != roleCodes.size()) {
            Set<String> foundCodes = roles.stream()
                    .map(Role::getCode)
                    .collect(Collectors.toSet());
            Set<String> notFoundCodes = roleCodes.stream()
                    .filter(code -> !foundCodes.contains(code))
                    .collect(Collectors.toSet());

            throw new IllegalArgumentException(
                    "Roles not found: " + notFoundCodes);
        }

        User user = User.builder()
                .fullName(req.getFullName())
                .gender(req.getGender())
                .birthday(req.getBirthday())
                .email(req.getEmail())
                .phone(req.getPhone())
                .username(req.getUserName())
                .password(passwordEncoder.encode(req.getPassword()))
                .classId(req.getClassID())
                .studentId(req.getStudentID())
                .currentResidence(req.getCurrentResidence())
                .contactAddress(req.getContactAddress())
                .status(UserStatus.ACTIVE)
                .isDeleted(false)
                .build();

        user.setRoles(new HashSet<>(roles));

        log.info("User has added successfully, userId={}", user.getId());

        return userResponseMapper.toDto(user);
    }

    @Override
    @Transactional()
    public UserResponse update(UUID userId, UserUpdateRequest req) {
        log.info("Updating user: {}", req);
        // Get user by id
        User user = getUserEntity(userId);
        userUpdateRequestMapper.partialUpdate(user, req);

        var roles = roleRepository.findAllByCodes(req.getRoles());
        user.setRoles(new HashSet<>(roles));

        log.info("Updated user: {}", req);
        log.info("Updated address: {}", req);

        return userResponseMapper.toDto(userRepository.save(user));
    }

    @Override
    public UUID changePassword(UserPasswordRequest req) {
        log.info("Changing password for user: {}", req);
        // Get user by id
        User user = getUserEntity(req.getId());
        if (req.getPassword().equals(req.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        userRepository.save(user);
        log.info("Changed password for user: {}", req);

        return user.getId();
    }

    @Override
    public UUID delete(UUID id) {
        log.info("Deleting user: {}", id);

        // Get user by id
        User user = getUserEntity(id);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
        log.info("Deleted user: {}", user);
        return id;
    }

    /**
     * Get user by id
     *
     * @param id
     * @return
     */
    private User getUserEntity(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UUID findUserIDByUserName(String userName) {
        return userRepository.getUserIDByUserName(userName);
    };
}
