package com.truongsonkmhd.unetistudy.controller;

import com.truongsonkmhd.unetistudy.dto.custom.request.user.UserPasswordRequest;
import com.truongsonkmhd.unetistudy.dto.custom.request.user.UserRequest;
import com.truongsonkmhd.unetistudy.dto.custom.request.user.UserUpdateRequest;
import com.truongsonkmhd.unetistudy.dto.common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.sevice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
    ResponseEntity<IResponseMessage> getAllUsersWithSortBy(@RequestParam(required = false) String sortBy,
                                                         @RequestParam(defaultValue = "0", required = false) int pageNo,
                                                         @RequestParam(defaultValue = "20") int pageSize) {
        log.info("Request get all users with sort by");

        var listAllUsersWithSortBy = userService.getAllUsersWithSortBy( sortBy, pageNo, pageSize);
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(listAllUsersWithSortBy));
    }

    @Operation(summary = "Get User Sorted multiple column", description = "API retrieve user sorted multiple column")
    @GetMapping("/List_sort_multiple")
    ResponseEntity<IResponseMessage> getAllUsersWithSortByMultipleColumns(@RequestParam(required = false) List<String> sort,
                                                                @RequestParam(defaultValue = "0", required = false) int pageNo,
                                                                @RequestParam(defaultValue = "20") int pageSize) {
        log.info("Request get all of users with sort by multiple column");
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(userService.getAllUsersWithSortByMultipleColumns( pageNo, pageSize, sort)));

    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID from database")
    @GetMapping("/{userId}")
    public ResponseEntity<IResponseMessage> getUserDetail(@PathVariable UUID userId) {
        log.info("Get user detail by ID: {}", userId);
        var userDetailById = userService.findById(userId);
        return ResponseEntity. ok().body(SuccessResponseMessage.LoadedSuccess(userDetailById));
    }

    @Operation(summary = "Create User", description = "API add new user to database")
    @PostMapping("/add")
    ResponseEntity<IResponseMessage> createUser(@RequestBody UserRequest request) {
        UUID userId = userService.saveUser(request);
        return ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(userId));
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
    ResponseEntity<IResponseMessage> deleteUser(@PathVariable("userId") UUID userId) {
        log.info("Deleting user: {}", userId);
        return ResponseEntity.ok().body(SuccessResponseMessage.UpdatedSuccess(userService.delete(userId)));

    }

    @Operation(summary = "Change Password", description = "API change password user to database")
    @PatchMapping("/change-pwd")
    ResponseEntity<IResponseMessage> changePassword(@RequestBody UserPasswordRequest request) {
        log.info("Changing password for user: {}", request);
        return ResponseEntity. ok().body(SuccessResponseMessage.LoadedSuccess(userService.changePassword(request)));
    }

}
