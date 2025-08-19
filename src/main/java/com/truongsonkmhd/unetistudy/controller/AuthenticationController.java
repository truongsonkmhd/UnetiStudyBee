package com.truongsonkmhd.unetistudy.controller;

import com.truongsonkmhd.unetistudy.configuration.Translator;
import com.truongsonkmhd.unetistudy.dto.request.AuthenticationRequest;
import com.truongsonkmhd.unetistudy.dto.request.SignInRequest;
import com.truongsonkmhd.unetistudy.dto.request.UserRequestDTO;
import com.truongsonkmhd.unetistudy.dto.response.AuthenticationResponse;
import com.truongsonkmhd.unetistudy.dto.response.ResponseData;
import com.truongsonkmhd.unetistudy.dto.response.ResponseError;
import com.truongsonkmhd.unetistudy.dto.response.TokenResponse;
import com.truongsonkmhd.unetistudy.dto.response.common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.response.common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.sevice.AuthenticationService;
import com.truongsonkmhd.unetistudy.sevice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth/authenticate")
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

    @Operation(summary = "sign-in-with-token", description = "API sign-in-with-token")
    @PostMapping("/sign-in-with-token")
    public ResponseEntity<IResponseMessage> loginWithToken(@RequestBody String token){
        AuthenticationResponse authenticationResponse = this.authenticationService.loginWithToken(token);
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(authenticationResponse));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<IResponseMessage> login(@RequestBody String refreshToken){
        AuthenticationResponse authenticateResponse = this.authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(authenticateResponse));
    }
}
