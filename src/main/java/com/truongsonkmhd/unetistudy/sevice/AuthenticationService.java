package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.request.auth.AuthenticationRequest;
import com.truongsonkmhd.unetistudy.dto.response.auth.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse loginWithToken(String token);

    AuthenticationResponse refreshToken(String refreshToken);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    //String logout()
}
