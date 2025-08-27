package com.truongsonkmhd.unetistudy.controller;

import com.truongsonkmhd.unetistudy.configuration.Translator;
import com.truongsonkmhd.unetistudy.dto.request.user.UserRequest;
import com.truongsonkmhd.unetistudy.dto.request.user.UserPasswordRequest;
import com.truongsonkmhd.unetistudy.dto.request.user.UserUpdateRequest;
import com.truongsonkmhd.unetistudy.dto.response.ResponseData;
import com.truongsonkmhd.unetistudy.dto.response.ResponseError;
import com.truongsonkmhd.unetistudy.dto.response.common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.response.common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.dto.response.user.UserPageResponse;
import com.truongsonkmhd.unetistudy.dto.response.user.UserResponse;
import com.truongsonkmhd.unetistudy.exception.ResourceNotFoundException;
import com.truongsonkmhd.unetistudy.sevice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller")
@Slf4j(topic = "USER_CONTROLLER")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    // có 3 cách khởi tạo bind
    // c1: sử dụng toán tử new
    // c2 RequiredArgsConstructor
    UserService userService;
    // như ví dụ dưới (đỡ p viết dòng này:)
    /*public UserController(UserService userService){
        this.userService = userService;
    }
    */

    private static final String ERROR_MESSAGE = "errorMessage={}";

    // c3: sử dụng @Autowired
    /*
        @Autowired
        private final UserService userService;
    */

    @Operation(summary = "Get User Sorted", description = "API retrieve user sorted ")
        @GetMapping("/List")
    ResponseData<UserPageResponse> getAllUsersWithSortBy(@RequestParam(required = false) String sortBy,
                                                                @RequestParam(defaultValue = "0", required = false) int pageNo,
                                                                @RequestParam(defaultValue = "20") int pageSize) {
        log.info("Request get all users with sort by");

        try {
            return new ResponseData<>(HttpStatus.OK.value(),
                    "Get All Users With Sort by",
                    userService.getAllUsersWithSortBy( sortBy, pageNo, pageSize));
        } catch (Exception e){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @Operation(summary = "Get User Sorted multiple column", description = "API retrieve user sorted multiple column")
    @GetMapping("/List_sort_multiple")
    ResponseData<UserPageResponse> getAllUsersWithSortByMultipleColumns(@RequestParam(required = false) List<String> sort,
                                                                @RequestParam(defaultValue = "0", required = false) int pageNo,
                                                                @RequestParam(defaultValue = "20") int pageSize) {
        log.info("Request get all of users with sort by multiple column");

        try {
            return new ResponseData<>(HttpStatus.OK.value(),
                    "Get All Users With Sort by",
                    userService.getAllUsersWithSortByMultipleColumns( pageNo, pageSize, sort));
        } catch (Exception e){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID from database")
    @GetMapping("/{userId}")
    ResponseData<UserResponse> getUserDetail(@PathVariable UUID userId) {
        log.info("Get user detail by ID: {}", userId);

        try{
            return new ResponseData<>(HttpStatus.OK.value(), "get User Detail", userService.findById(userId));
        }catch (ResourceNotFoundException e){
            log.error("errorMessager = {}" , e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }

    }

    @Operation(summary = "Create User", description = "API add new user to database")
    @PostMapping("/add")
    ResponseData<UUID> createUser(@RequestBody UserRequest request) {

        try{
            UUID userId = userService.saveUser(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), Translator.toLocale("user.add.success"),userId);
        }catch (Exception e){
            log.info("errorMessage = {}", e.getMessage() , e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Add user fail");
        }

    }

    @Operation(summary = "Update User", description = "API update user to database")
    @PutMapping("/upd/{userId}")
    ResponseEntity<IResponseMessage> updateUser(@PathVariable UUID userId, @RequestBody UserUpdateRequest request) {
        log.info("Updating user: {}", request);
        return ResponseEntity.ok().body(SuccessResponseMessage.UpdatedSuccess(userService.update(userId,request)));

    }

    @Operation(
            summary = "Deactivate User",
            description = "API deactivate (soft delete) user from database"
    )
    @DeleteMapping("del/{userId}")
    ResponseData<Void> deleteUser(@PathVariable("userId") UUID userId) {
        log.info("Deleting user: {}", userId);
        try{
            userService.delete(userId);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), Translator.toLocale("user.del.success"));
        }catch (Exception e){
            log.error(ERROR_MESSAGE, e.getMessage(),e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "delete user fail");
        }
    }

    @Operation(summary = "Change Password", description = "API change password user to database")
    @PatchMapping("/change-pwd")
    ResponseData<Void> changePassword(@RequestBody UserPasswordRequest request) {
        log.info("Changing password for user: {}", request);

        try{
            userService.changePassword(request);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), Translator.toLocale("password.change.success"));
        } catch (Exception e){
            log.error(ERROR_MESSAGE,e.getMessage(),e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "change-pwd fail");
        }

    }


}
