package com.truongsonkmhd.unetistudy.sevice.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.truongsonkmhd.unetistudy.dto.request.auth.AuthenticationRequest;
import com.truongsonkmhd.unetistudy.dto.request.auth.IntrospectRequest;
import com.truongsonkmhd.unetistudy.dto.request.auth.LogoutRequest;
import com.truongsonkmhd.unetistudy.dto.response.auth.AuthenticationResponse;
import com.truongsonkmhd.unetistudy.dto.response.auth.IntrospectResponse;
import com.truongsonkmhd.unetistudy.dto.response.user.UserResponse;
import com.truongsonkmhd.unetistudy.exception.AppException;
import com.truongsonkmhd.unetistudy.exception.ErrorCode;
import com.truongsonkmhd.unetistudy.exception.InvalidDataException;
import com.truongsonkmhd.unetistudy.exception.payload.DataNotFoundException;
import com.truongsonkmhd.unetistudy.model.InvalidatedToken;
import com.truongsonkmhd.unetistudy.model.Token;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.repository.InvalidatedTokenRepository;
import com.truongsonkmhd.unetistudy.repository.TokenRepository;
import com.truongsonkmhd.unetistudy.repository.UserRepository;
import com.truongsonkmhd.unetistudy.security.MyUserDetail;
import com.truongsonkmhd.unetistudy.sevice.AuthenticationService;
import com.truongsonkmhd.unetistudy.security.JwtService;
import com.truongsonkmhd.unetistudy.sevice.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import static com.truongsonkmhd.unetistudy.common.UserStatus.ACTIVE;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AUTHENTICATION-SERVICE")
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${security.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpirationSeconds;

    @Value("${security.jwt.token-validity-in-seconds}")
    private long accessTokenExpirationSeconds;

    @NonFinal
    @Value("${security.jwt.signerKey}")
    protected String SIGNER_KEY;
    @Value("${security.jwt.refresh-token-validity-in-seconds-for-remember-me}")
    private long refreshTokenExpirationSecondsRememberMe;

    private final UserRepository userRepository;


    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    InvalidatedTokenRepository invalidatedTokenRepository;

    private final UserService userService;

    @Transactional
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // 1) Lấy user + roles
        User user = userRepository
                .getByUsernameAndIsDeletedWithRoles(request.getUsername(), false)
                .orElseThrow(() -> new RuntimeException("User not found or deleted"));
        log.info("USER: {}" , user.getRoles());

        // 2) Trạng thái
        if (!ACTIVE.equals(user.getStatus())) {
            throw new RuntimeException("User is not activated");
        }

        // 3) Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 4) Tạo token
        boolean rememberMe = Boolean.TRUE.equals(request.getIsRememberMe());
        long rtTtl = rememberMe ? refreshTokenExpirationSecondsRememberMe : refreshTokenExpirationSeconds;

        MyUserDetail myUserDetail = new MyUserDetail(user);
        String accessToken = jwtService.generateToken(myUserDetail, rememberMe);
        String refreshToken = jwtService.generateRefreshToken(myUserDetail, rememberMe);

        log.info("Authorities: {}" , myUserDetail.getAuthorities());

        Instant now = Instant.now();
        Instant accessExp = now.plusSeconds(accessTokenExpirationSeconds);
        Instant refreshExp = now.plusSeconds(rtTtl);

        // Nếu user đã có token → update thay vì insert
        Token dbToken = tokenRepository.findByUser(user).orElse(new Token());
        dbToken.setUser(user);
        dbToken.setToken(accessToken);
        dbToken.setRefreshToken(refreshToken);
        dbToken.setTokenType("Bearer");
        dbToken.setExpirationTime(accessExp);
        dbToken.setRefreshExpirationTime(refreshExp);
        dbToken.setRevoked(false);
        dbToken.setExpired(false);
        tokenRepository.save(dbToken);

        return createAuthenticationResponse(accessToken, refreshToken, myUserDetail);
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
    public AuthenticationResponse loginWithToken(String token) {
        String userName = jwtService.extractUsername(token);
        User user = this.userRepository.getByUsernameAndIsDeletedWithRoles(userName, false).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return createAuthenticationResponse(token, user.getToken().getRefreshToken(), new MyUserDetail(user));
    }

    @Transactional
    public AuthenticationResponse refreshToken(String rawRefreshToken){

        Token token = verifyRefreshToken(rawRefreshToken);

        if (token.getRefreshExpirationTime() == null || !token.getRefreshExpirationTime().isAfter(Instant.now())) {
            throw new RuntimeException("RefreshToken is expired!");
        }

        User user = token.getUser();
        MyUserDetail myUserDetail = new MyUserDetail(user);

        // Tạo mới cặp token
        String newAccessToken  = jwtService.generateToken(myUserDetail, false);
        String newRefreshToken = jwtService.generateRefreshToken(myUserDetail, false);

        // Cập nhật đầy đủ record
        token.setToken(newAccessToken);
        token.setExpirationTime(Instant.now().plusSeconds(accessTokenExpirationSeconds));
        token.setRefreshToken(newRefreshToken);
        token.setRefreshExpirationTime(Instant.now().plusSeconds(refreshTokenExpirationSeconds));

        tokenRepository.save(token);

        return createAuthenticationResponse(newAccessToken, newRefreshToken, myUserDetail);
    }

    private AuthenticationResponse createAuthenticationResponse(String token, String refreshToken, MyUserDetail myUserDetail) {
        return AuthenticationResponse.builder()
                .isAuthenticated(true)
                .token(token)
                .refreshToken(refreshToken)
                .user(UserResponse.builder()
                        .id(myUserDetail.user().getId())
                        .fullName(myUserDetail.user().getFullName())
                        .username(myUserDetail.user().getUsername())
                        .email(myUserDetail.user().getEmail())
                        .phone(myUserDetail.user().getPhone())
                        .birthday(myUserDetail.user().getBirthday())
                        .gender(myUserDetail.user().getGender())
                        .build())
                .build();
    }

    public Token verifyRefreshToken(String refreshToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new DataNotFoundException("Invalid refresh token"));

        if (token.getExpirationTime().compareTo(Instant.now()) < 0) {
            tokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }

        return token;
    }

    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }
    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken());

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository
                .existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
}
