package com.truongsonkmhd.unetistudy.sevice.impl;

import com.truongsonkmhd.unetistudy.dto.request.SignInRequest;
import com.truongsonkmhd.unetistudy.dto.response.TokenResponse;
import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.sevice.AuthenticationService;
import com.truongsonkmhd.unetistudy.sevice.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public TokenResponse getAccessToken(SignInRequest request) {
        log.info("Get access token "+ request.getUsername() + request.getPassword()) ;

        List<String> authorities = new ArrayList<>();

        try {
            // Thực hiện xác thực với username và password
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            log.info("isAuthenticated = {}", authenticate.isAuthenticated());
            log.info("Authorities: {}", authenticate.getAuthorities().toString());
            authorities.add(authenticate.getAuthorities().toString());

            // Nếu xác thực thành công, lưu thông tin vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (BadCredentialsException | DisabledException e) {
            log.error("errorMessage: {}", e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }

        // Get user
        var user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            log.info("LỖI LÒI");
            throw new UsernameNotFoundException(request.getUsername());
        }

        String accessToken = jwtService.generateAccessToken(user.getUsername(), authorities);
        String refreshToken = jwtService.generateRefreshToken(user.getUsername(), authorities);
        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

}

    @Override
    public TokenResponse getRefreshToken(String request) {
        return null;
    }
}
