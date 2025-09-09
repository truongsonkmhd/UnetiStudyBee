package com.truongsonkmhd.unetistudy.configuration;

import com.truongsonkmhd.unetistudy.security.AuthoritiesConstants;
import com.truongsonkmhd.unetistudy.security.impl.JwtServiceImpl;
import com.truongsonkmhd.unetistudy.sevice.UserServiceDetail;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

import static com.truongsonkmhd.unetistudy.security.AuthoritiesConstants.ADMIN;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {


    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .cors(Customizer.withDefaults()) // cấu hình cors
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        authz ->
                                authz
                                        .requestMatchers(JwtServiceImpl.WHITE_LIST_URL).permitAll()
                                        .requestMatchers(mvc.pattern("/api/permissions/**")).hasAnyAuthority(AuthoritiesConstants.ADMIN , AuthoritiesConstants.SYS_ADMIN)
                                        .requestMatchers(mvc.pattern("/api/users/**")).hasAnyAuthority(AuthoritiesConstants.ADMIN ,AuthoritiesConstants.SYS_ADMIN)
                                        .requestMatchers(mvc.pattern("/api/roles/**")).hasAnyAuthority(AuthoritiesConstants.ADMIN ,  AuthoritiesConstants.SYS_ADMIN)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(
                        exceptions ->
                                exceptions
                                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public WebSecurityCustomizer ignoreResources() {
        return webSecurity -> webSecurity
                .ignoring()
                .requestMatchers("/actuator/**", "/v3/**", "/webjars/**", "/swagger-ui*/*swagger-initializer.js", "/swagger-ui*/**", "/favicon.ico");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:8080",
                "http://localhost:8081  ",
                "http://localhost:5173",
                "http://localhost:4221",
                "https://localhost:4221"
        ));
        config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization","Link","X-Total-Count"));
        config.setAllowCredentials(true);
        config.setMaxAge(1800L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}