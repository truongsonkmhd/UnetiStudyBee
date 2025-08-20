package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.request.AuthenticationRequest;
import com.truongsonkmhd.unetistudy.dto.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse loginWithToken(String token);

    AuthenticationResponse refreshToken(String refreshToken);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    //String logout()
}
