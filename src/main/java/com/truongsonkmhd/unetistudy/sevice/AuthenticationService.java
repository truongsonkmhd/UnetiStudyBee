package com.truongsonkmhd.unetistudy.sevice;

import com.nimbusds.jose.JOSEException;
import com.truongsonkmhd.unetistudy.dto.request.auth.AuthenticationRequest;
import com.truongsonkmhd.unetistudy.dto.request.auth.IntrospectRequest;
import com.truongsonkmhd.unetistudy.dto.request.auth.LogoutRequest;
import com.truongsonkmhd.unetistudy.dto.response.auth.AuthenticationResponse;
import com.truongsonkmhd.unetistudy.dto.response.auth.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse loginWithToken(String token);

    AuthenticationResponse refreshToken(String refreshToken);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
