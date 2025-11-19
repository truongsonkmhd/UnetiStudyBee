package com.truongsonkmhd.unetistudy.controller;

import com.nimbusds.jose.JOSEException;
import com.truongsonkmhd.unetistudy.dto.AuthDTO.AuthenticationDTORequest;
import com.truongsonkmhd.unetistudy.dto.AuthDTO.IntrospectDTORequest;
import com.truongsonkmhd.unetistudy.dto.AuthDTO.LogoutDTORequest;
import com.truongsonkmhd.unetistudy.dto.AuthDTO.RefreshTokenDTORequest;
import com.truongsonkmhd.unetistudy.dto.a_custom.ApiResponse;
import com.truongsonkmhd.unetistudy.dto.AuthDTO.AuthenticationDTOResponse;
import com.truongsonkmhd.unetistudy.dto.AuthDTO.IntrospectDTOResponse;
import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.sevice.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("")
    @Operation(summary = "Đăng nhập tài khoan" +
            "" +
            "")
    ResponseEntity<IResponseMessage> login(@RequestBody AuthenticationDTORequest request){
        AuthenticationDTOResponse authenticateResponse = this.authenticationService.authenticate(request);
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(authenticateResponse));
    }


    @PostMapping("/login-with-token")
    ResponseEntity<IResponseMessage> loginWithToken(@RequestBody RefreshTokenDTORequest req){
        AuthenticationDTOResponse authenticationDTOResponse = this.authenticationService.loginWithToken(req.getRefreshToken());
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(authenticationDTOResponse));
    }

    @PostMapping("/refresh-token")
    ResponseEntity<IResponseMessage> login(@RequestBody RefreshTokenDTORequest req){
        AuthenticationDTOResponse authenticateResponse = this.authenticationService.refreshToken(req.getRefreshToken());
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(authenticateResponse));
    }


    @PostMapping("/introspect")
    ApiResponse<IntrospectDTOResponse> authenticate(@RequestBody IntrospectDTORequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectDTOResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutDTORequest request)
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
