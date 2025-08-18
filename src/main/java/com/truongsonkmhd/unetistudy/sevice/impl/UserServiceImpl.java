package com.truongsonkmhd.unetistudy.sevice.impl;

import com.truongsonkmhd.unetistudy.common.UserStatus;
import com.truongsonkmhd.unetistudy.common.UserType;
import com.truongsonkmhd.unetistudy.dto.request.AddressRequestDTO;
import com.truongsonkmhd.unetistudy.dto.request.UserPasswordRequestDTO;
import com.truongsonkmhd.unetistudy.dto.request.UserRequestDTO;
import com.truongsonkmhd.unetistudy.dto.request.UserUpdateRequestDTO;
import com.truongsonkmhd.unetistudy.dto.response.UserPageResponse;
import com.truongsonkmhd.unetistudy.dto.response.UserResponse;
import com.truongsonkmhd.unetistudy.exception.ResourceNotFoundException;
import com.truongsonkmhd.unetistudy.model.Address;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.repository.AddressRepository;
import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserPageResponse getAllUsersWithSortBy(String sortBy, int pageNo, int pageSize) {

        // xu ly truong hop FE muon bat dau voi page = 1
        if (pageNo > 0) {
            pageNo -= 1;
        }

        List<Sort.Order> sorts = new ArrayList<>();
        // nếu có giá trị
        if(StringUtils.hasLength(sortBy)){
            // firstName: asc|desc
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()){
                if(matcher.group(3).equalsIgnoreCase("asc")){
                    sorts.add(new Sort.Order(Sort.Direction.ASC,matcher.group(1)));
                }else{
                    sorts.add(new Sort.Order(Sort.Direction.DESC,matcher.group(1)));
                }
            }

        }

        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sorts));
        Page<User> users = userRepository.findAll(pageable);


        return getUserPageResponse(pageNo, pageSize, users);
    }

    @Override
    public UserPageResponse getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, List<String> sorts) {
        // xu ly truong hop FE muon bat dau voi page = 1
        if (pageNo > 0) {
            pageNo -= 1;
        }
        List<Sort.Order> orders = new ArrayList<>();

        for (String sortBy: sorts){

            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");

            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()){
                if(matcher.group(3).equalsIgnoreCase("asc")){
                    orders.add(new Sort.Order(Sort.Direction.ASC,matcher.group(1)));
                }else{
                    orders.add(new Sort.Order(Sort.Direction.DESC,matcher.group(1)));
                }
            }
        }


        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(orders));

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
    private static UserPageResponse getUserPageResponse(int page, int pageSize, Page<User> userEntities) {
        log.info("Convert User Entity Page");
        List<UserResponse> userList = userEntities.stream().map(entity -> UserResponse.builder()
                .id(entity.getId())
                .fistName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(entity.getGender())
                .birthday(entity.getBirthday())
                .userName(entity.getUsername())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .build()

        ).toList();

        UserPageResponse response = new UserPageResponse();
        response.setPageNumber(page);
        response.setPageSize(pageSize);
        response.setTotalElements(userEntities.getTotalElements());
        response.setTotalPages(userEntities.getTotalPages());
        response.setUsers(userList);
        return response;
    }

    @Override
    public UserResponse findById(UUID id) {

        log.info("Find user by id: {}", id);

        User user = getUserEntity(id);

        return UserResponse.builder()
                .id(id)
                .fistName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .userName(user.getUsername())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserResponse findByUsername(String username) {
        return null;
    }

    @Override
    public UserResponse findByEmail(String email) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID saveUser(UserRequestDTO req) {
        User user = User.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .gender(req.getGender())
                .birthday(req.getBirthday())
                .email(req.getEmail())
                .phone(req.getPhone())
                .username(req.getUserName())
                .status(UserStatus.NONE)
                .type(UserType.valueOf(req.getType().toUpperCase()))
                .addresses(convertToAddress(req.getAddresses()))
                .build();
        userRepository.save(user);

        log.info("User has added successfully, userId={}", user.getId());

        return user.getId();
    }

    private Set<Address> convertToAddress(Set<AddressRequestDTO> addresses) {
        Set<Address> result = new HashSet<>();
        addresses.forEach(a ->
                result.add(Address.builder()
                        .apartmentNumber(a.getApartmentNumber())
                        .floor(a.getFloor())
                        .building(a.getBuilding())
                        .streetNumber(a.getStreetNumber())
                        .street(a.getStreet())
                        .city(a.getCity())
                        .country(a.getCountry())
                        .addressType(a.getAddressType())
                        .build())
        );
        return result;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UUID userId, UserUpdateRequestDTO req) {
        log.info("Updating user: {}", req);
        // Get user by id
        User user = getUserEntity(userId);
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());

        if(!req.getEmail().equals(user.getEmail())){
            user.setEmail(req.getEmail());
        }

        user.setPhone(req.getPhone());
        user.setUsername(req.getUserName());

        userRepository.save(user);
        log.info("Updated user: {}", req);
        user.setAddresses(convertToAddress(req.getAddresses()));


        log.info("Updated address: {}", req);
    }

    @Override
    public void changePassword(UserPasswordRequestDTO req) {
        log.info("Changing password for user: {}", req);
        // Get user by id
        User user = getUserEntity(req.getId());
        if (req.getPassword().equals(req.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        userRepository.save(user);
        log.info("Changed password for user: {}", req);
    }

    @Override
    public void delete(UUID id) {
        log.info("Deleting user: {}", id);

        // Get user by id
        User user = getUserEntity(id);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
        log.info("Deleted user: {}", user);

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
}
