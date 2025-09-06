package com.truongsonkmhd.unetistudy.controller;

import com.nimbusds.jose.JOSEException;
import com.truongsonkmhd.unetistudy.dto.request.auth.AuthenticationRequest;
import com.truongsonkmhd.unetistudy.dto.request.auth.IntrospectRequest;
import com.truongsonkmhd.unetistudy.dto.request.auth.LogoutRequest;
import com.truongsonkmhd.unetistudy.dto.request.auth.RefreshTokenRequest;
import com.truongsonkmhd.unetistudy.dto.response.ApiResponse;
import com.truongsonkmhd.unetistudy.dto.response.auth.AuthenticationResponse;
import com.truongsonkmhd.unetistudy.dto.response.auth.IntrospectResponse;
import com.truongsonkmhd.unetistudy.dto.response.common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.response.common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.sevice.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/authenticate")
@Slf4j(topic = "AUTHENTICATION-CONTROLLER")
@Tag(name = "Authentication Controller")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

//    @Operation(summary = "Create User", description = "API add new user to database")
//    @PostMapping("/login")
//    public ResponseEntity<IResponseMessage> createUser(
//            @RequestBody SignInRequest request
//    ) {
//        String tokenGenerator = authenticationService.login(
//                request.getUsername(),
//                request.getPassword()
//        );
//        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(tokenGenerator));
//
//    }

    @PostMapping("")
    public ResponseEntity<IResponseMessage> login(@RequestBody AuthenticationRequest request){
        AuthenticationResponse authenticateResponse = this.authenticationService.authenticate(request);
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(authenticateResponse));
    }


    @PostMapping("/login-with-token")
    public ResponseEntity<IResponseMessage> loginWithToken(@RequestBody  RefreshTokenRequest req){
        AuthenticationResponse authenticationResponse = this.authenticationService.loginWithToken(req.getRefreshToken());
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(authenticationResponse));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<IResponseMessage> login(@RequestBody RefreshTokenRequest req){
        AuthenticationResponse authenticateResponse = this.authenticationService.refreshToken(req.getRefreshToken());
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(authenticateResponse));
    }


    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

//    B1: Thêm id cho claimset
//    B2: tạo một cái entity giữ thông tin token đã bị logout
//
//    B3: tạo api logout (verify + save vào bảng invalidedToken
//            B4: update introspect để check xem token còn tồn hiệu lực không, có trong bảng invalided không


}
