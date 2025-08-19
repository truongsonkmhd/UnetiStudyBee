package com.truongsonkmhd.unetistudy.sevice.impl;

import com.truongsonkmhd.unetistudy.dto.request.AuthenticationRequest;
import com.truongsonkmhd.unetistudy.dto.response.AuthenticationResponse;
import com.truongsonkmhd.unetistudy.dto.response.UserResponse;
import com.truongsonkmhd.unetistudy.model.Token;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.repository.TokenRepository;
import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.security.MyUserDetail;
import com.truongsonkmhd.unetistudy.sevice.AuthenticationService;
import com.truongsonkmhd.unetistudy.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${security.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpirationSeconds;

    @Value("${security.jwt.refresh-token-validity-in-seconds-for-remember-me}")
    private long refreshTokenExpirationSecondsRememberMe;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // 1) Lấy user + roles
        User user = userRepository
                .getByUsernameAndIsDeletedWithRoles(request.getUsername(), false)
                .orElseThrow(() -> new RuntimeException("User not found or deleted"));

        // 2) Trạng thái
        if (Boolean.FALSE.equals(user.getIsActivated())) {
            throw new RuntimeException("User is not activated");
        }

        // 3) Check password (hash)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 4) Tạo token
        boolean rememberMe = request.getIsRememberMe() != null && request.getIsRememberMe();
        long rtTtl = rememberMe ? refreshTokenExpirationSecondsRememberMe : refreshTokenExpirationSeconds;

        MyUserDetail principal = new MyUserDetail(user);
        String accessToken = jwtService.generateToken(principal, rememberMe);
        String refreshToken = jwtService.generateRefreshToken(principal, rememberMe);

        Instant now = Instant.now();
        Instant accessExp = now.plusSeconds(refreshTokenExpirationSeconds);
        Instant refreshExp = now.plusSeconds(rtTtl);

        // 5) Ghi/rotate bản ghi Token (OneToOne)
        Token dbToken = tokenRepository.findByUser(user).orElseGet(Token::new);
        dbToken.setUser(user);
        dbToken.setToken(accessToken);
        dbToken.setRefreshToken(refreshToken);
        dbToken.setTokenType("Bearer");
        dbToken.setExpirationTime(accessExp);
        dbToken.setRefreshExpirationTime(refreshExp);
        dbToken.setRevoked(false);
        dbToken.setExpired(false);
        tokenRepository.save(dbToken);

        // 6) Trả response
        return createAuthenticationResponse(accessToken, refreshToken, principal);
    }

//    @Override
//    public String login(String userName, String password) throws DataNotFoundException {
//        // exists by user
//        Optional<User> optionalUser = userRepository.findByUsername(userName);
//        if (optionalUser.isEmpty()) {
////            throw new DataNotFoundException(
////                    translate(MessageKeys.PHONE_NUMBER_AND_PASSWORD_FAILED)
////            );
//        }
//        User user = optionalUser.get();
//        // check password
////        if (user.getFacebookAccountId() == 0 && user.getGoogleAccountId() == 0) {
////            if (!passwordEncoder.matches(password, user.getPassword())) {
////                throw new BadCredentialsException(
////                        translate(MessageKeys.PHONE_NUMBER_AND_PASSWORD_FAILED)
////                );
////            }
////        }
////        Optional<Role> optionalRole = roleRepository.findById(user.getRole().getId());
////        if (optionalRole.isEmpty() || ) {}
//
//        // kiểm tra xem user đã được active hay chưa
//        if (!(optionalUser.get().getStatus() == UserStatus.ACTIVE)) {
//       //     throw new DataNotFoundException(translate(MessageKeys.USER_ID_LOCKED));
//        }
////        // authenticated with java spring security
////        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
////                user.getUsername(),
////                password,
////                user.getAuthorities())
////        );
//
//        // generate token
//        return "";
//    }



    @Transactional
    @Override
    public AuthenticationResponse loginWithToken(String token){
        String userName = jwtService.extractUsername(token);
        User user = this.userRepository.getByUsernameAndIsDeletedWithRoles(userName, false).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return createAuthenticationResponse(token, user.getToken().getRefreshToken(), new MyUserDetail(user));
    }

    @Transactional
    public AuthenticationResponse refreshToken(String refreshToken){

        Token token =  tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        User user = token.getUser();

        if(!token.getRefreshExpirationTime().isAfter(Instant.now())){
            throw new RuntimeException("RefreshToken is expired!");
        }
        MyUserDetail myUserDetail = new MyUserDetail(user);

        var jwtToken = this.jwtService.generateToken(myUserDetail, false);
        var newRefreshToken = this.jwtService.generateRefreshToken(myUserDetail, false);
        Instant refreshTokenExpirationTime = Instant.now().plusSeconds(refreshTokenExpirationSeconds);
        token.setRefreshToken(newRefreshToken);
        token.setRefreshExpirationTime(refreshTokenExpirationTime);
        this.userRepository.save(user);

        return createAuthenticationResponse(jwtToken, newRefreshToken, myUserDetail);
    }

    private AuthenticationResponse createAuthenticationResponse(String token, String refreshToken, MyUserDetail myUserDetail){
        return AuthenticationResponse.builder()
                .isAuthenticated(true)
                .token(token)
                .refreshToken(refreshToken)
                .user(UserResponse.builder()
                        .id(myUserDetail.user().getId())
                        .fullName(myUserDetail.user().getFullName())
                        .userName(myUserDetail.user().getUsername())
                        .email(myUserDetail.user().getEmail())
                        .phone(myUserDetail.user().getPhone())
                        .birthday(myUserDetail.user().getBirthday())
                        .gender(myUserDetail.user().getGender())
                        .build())
                .build();
    }
}
