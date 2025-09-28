package com.truongsonkmhd.unetistudy.sevice;

import com.nimbusds.jose.JOSEException;
import com.truongsonkmhd.unetistudy.dto.AuthDTO.AuthenticationDTORequest;
import com.truongsonkmhd.unetistudy.dto.AuthDTO.IntrospectDTORequest;
import com.truongsonkmhd.unetistudy.dto.AuthDTO.LogoutDTORequest;
import com.truongsonkmhd.unetistudy.dto.AuthDTO.AuthenticationDTOResponse;
import com.truongsonkmhd.unetistudy.dto.AuthDTO.IntrospectDTOResponse;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationDTOResponse loginWithToken(String token);

    AuthenticationDTOResponse refreshToken(String refreshToken);

    AuthenticationDTOResponse authenticate(AuthenticationDTORequest request);

    void logout(LogoutDTORequest request) throws ParseException, JOSEException;

    IntrospectDTOResponse introspect(IntrospectDTORequest request) throws JOSEException, ParseException;
}
