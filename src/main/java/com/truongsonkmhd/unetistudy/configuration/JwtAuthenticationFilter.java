package com.truongsonkmhd.unetistudy.configuration;

import com.truongsonkmhd.unetistudy.context.UserContext;
import com.truongsonkmhd.unetistudy.security.JwtService;
import com.truongsonkmhd.unetistudy.sevice.UserService;
import com.truongsonkmhd.unetistudy.sevice.UserServiceDetail;
import com.truongsonkmhd.unetistudy.utils.CacheUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
@RequiredArgsConstructor
@Slf4j(topic = "CUSTOMIZE-REQUEST-FILTER ")
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;
    private final CacheUtil cacheUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("{} {}", request.getMethod(), request.getRequestURI());
        if (request.getServletPath().contains("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            if(this.cacheUtil.getMapTokenUserDetail().containsKey(jwt)){
                userDetails = this.cacheUtil.getMapTokenUserDetail().get(jwt);
            }else{
                userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                this.cacheUtil.updateMapTokenUserDetail(jwt, userDetails);
            }


            if (jwtService.isValid(jwt, userDetails) /*&& isTokenValid*/) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            String token = null;


            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            // Nếu token hợp lệ → set vào ThreadLocal
            if (token != null && jwtService.validateToken(token)) {
                String username = jwtService.extractUsername(token);
                UUID userID = jwtService.extractUserID(token);
                UserContext.setUsername(username);
                UserContext.setUserID(userID);
            }


        }
        filterChain.doFilter(request, response);
    }

}
