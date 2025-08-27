package com.truongsonkmhd.unetistudy.security.impl;

import com.truongsonkmhd.unetistudy.common.UserStatus;
import com.truongsonkmhd.unetistudy.model.Token;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.repository.TokenRepository;
import com.truongsonkmhd.unetistudy.security.JwtService;
import com.truongsonkmhd.unetistudy.security.MyUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@Slf4j(topic = "JWT-SERVICE")
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jhipster.security.authentication.jwt.base64-secret}")
    private String secretKey;
    @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds}")
    private long jwtExpiration;
    @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds-for-remember-me}")
    private long jwtExpirationRememberMe;
    @Value("${security.jwt.refresh-token-validity-in-seconds}")
    private long refreshExpiration;
    @Value("${security.jwt.refresh-token-validity-in-seconds-for-remember-me}")
    private long refreshExpirationRememberMe;

    public static final String CLAIM_USER_ID = "id";

    public static final String CLAIM_USER_FULL_NAME = "fullName";

    public static final String SCOPE = "scope";

    private final TokenRepository tokenRepository;

    public static final String[] WHITE_LIST_URL = { "/api/v1/auth/**" ,
            "/api/authenticate/**",
            "/api/users/add"
    };
    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String generateRefreshToken(MyUserDetail myUserDetail, boolean isRememberMe) {
        return buildToken(new HashMap<>(), myUserDetail, isRememberMe ? refreshExpirationRememberMe : refreshExpiration);
    }

    @Override
    public String generateToken(MyUserDetail myUserDetail, boolean isRememberMe) {
        User user = myUserDetail.user();
        Map<String, Object> extractClaims = Map.ofEntries(
                Map.entry(CLAIM_USER_ID, user.getId()),
                Map.entry(CLAIM_USER_FULL_NAME, user.getFullName()),
                Map.entry(SCOPE, buildScope(myUserDetail))
        );
        return generateToken(extractClaims, myUserDetail, isRememberMe);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            MyUserDetail myUserDetail,
            boolean isRememberMe
    ) {
        return buildToken(extraClaims, myUserDetail, isRememberMe ? jwtExpirationRememberMe : jwtExpiration);
    }


    // generate token using jwt utility class and return token as string
    public String buildToken(Map<String, Object> extractClaims, MyUserDetail myUserDetail, long expiration) {
        try {
            return Jwts
                    .builder()
                    .setClaims(extractClaims)
                    .setSubject(myUserDetail.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + (expiration + 1000)))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // decode and get the key
    private Key getSignInKey() {
        // decode SECRET_KEY
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    private Key getKey(TokenType type) {
//        log.info("Create key for type {}", type);
//
//        switch (type) {
//            case ACCESS_TOKEN -> {
//                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
//            }
//            case REFRESH_TOKEN -> {
//                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
//            }
//            default -> throw new InvalidDataException("Invalid token type");
//        }
//    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    // if token is valid by checking if token is expired for current user
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String phoneNumber = extractPhoneNumber(token);
        Token existingToken = tokenRepository.findByToken(token);
        if (existingToken == null
                || existingToken.isRevoked()
                || !(existingToken.getUser().getStatus() == UserStatus.ACTIVE)
        ) {
            return false;
        }
        return phoneNumber.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // extract user from token
    public String extractPhoneNumber(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // if token expirated
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // get expiration data from token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String buildScope(MyUserDetail myUserDetail){
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(myUserDetail.user().getRoles()))
            myUserDetail.user().getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getCode());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions()
                            .forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }
}
