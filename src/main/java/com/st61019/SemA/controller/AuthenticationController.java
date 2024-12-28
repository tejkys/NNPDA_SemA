package com.st61019.SemA.controller;


import com.st61019.SemA.dao.request.*;
import com.st61019.SemA.dao.response.JwtAuthenticationResponse;
import com.st61019.SemA.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
    @PostMapping("/getRecoveryPasswordToken")
    public ResponseEntity<String> getRecoveryPasswordToken(@RequestBody GetRecoveryPasswordTokenRequest request) {

        return ResponseEntity.ok(authenticationService.getRecoveryPasswordToken(request));
    }
    @PostMapping("/recoverPasswordByToken")
    public ResponseEntity<String> recoverPasswordByToken(@RequestBody RecoverPasswordByTokenRequest request) {
        return ResponseEntity.ok(authenticationService.recoverPasswordByToken(request));
    }
}
