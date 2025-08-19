package com.truongsonkmhd.unetistudy.sevice.impl;

import com.truongsonkmhd.unetistudy.common.UserStatus;
import com.truongsonkmhd.unetistudy.dto.request.SignInRequest;
import com.truongsonkmhd.unetistudy.dto.response.TokenResponse;
import com.truongsonkmhd.unetistudy.exception.payload.DataNotFoundException;
import com.truongsonkmhd.unetistudy.model.User;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String login(String userName, String password) throws DataNotFoundException {
        // exists by user
        Optional<User> optionalUser = userRepository.findByUsername(userName);
//        if (optionalUser.isEmpty()) {
//            throw new DataNotFoundException(
//                    translate(MessageKeys.PHONE_NUMBER_AND_PASSWORD_FAILED)
//            );
//        }
        User user = optionalUser.get();
        // check password
//        if (user.getFacebookAccountId() == 0 && user.getGoogleAccountId() == 0) {
//            if (!passwordEncoder.matches(password, user.getPassword())) {
//                throw new BadCredentialsException(
//                        translate(MessageKeys.PHONE_NUMBER_AND_PASSWORD_FAILED)
//                );
//            }
//        }
//        Optional<Role> optionalRole = roleRepository.findById(user.getRole().getId());
//        if (optionalRole.isEmpty() || ) {}

        // kiểm tra xem user đã được active hay chưa
        if (!(optionalUser.get().getStatus() == UserStatus.ACTIVE)) {
       //     throw new DataNotFoundException(translate(MessageKeys.USER_ID_LOCKED));
        }
        // authenticated with java spring security
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                password,
                user.getAuthorities())
        );

        // generate token
        return jwtService.generateToken(user);
    }
}
