package com.st61019.SemA.service;


import com.st61019.SemA.dao.request.*;
import com.st61019.SemA.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);

    String recoverPasswordByToken(RecoverPasswordByTokenRequest request);

    String getRecoveryPasswordToken(GetRecoveryPasswordTokenRequest request);

}
