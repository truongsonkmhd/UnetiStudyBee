package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.request.SignInRequest;
import com.truongsonkmhd.unetistudy.dto.response.TokenResponse;

public interface AuthenticationService {
    String login(String userName, String password) ;
}
