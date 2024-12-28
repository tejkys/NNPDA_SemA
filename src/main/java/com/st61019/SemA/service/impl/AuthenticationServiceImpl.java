package com.st61019.SemA.service.impl;

import com.st61019.SemA.dao.request.*;
import com.st61019.SemA.dao.response.JwtAuthenticationResponse;
import com.st61019.SemA.entities.Role;
import com.st61019.SemA.entities.User;
import com.st61019.SemA.repository.UserRepository;
import com.st61019.SemA.service.AuthenticationService;
import com.st61019.SemA.service.JwtService;
import com.st61019.SemA.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public String recoverPasswordByToken(RecoverPasswordByTokenRequest request) {
        if(!request.getNewPassword().equals(request.getNewPasswordAgain()))
            return "Passwords are not equal";
        if(request.getNewPassword().length() < 6 )
            return "Password is too weak";

        var userOptional = userRepository.findByEmail(request.getUsername());
        if(userOptional.isEmpty())
            return "User is not valid";

        var user = userOptional.get();
        if(user.getRecoveryToken() == null || user.getRecoveryExpiration() == null)
            return "First request for token";
        if(!user.getRecoveryToken().equals(request.getToken()))
            return "User is not valid";
        if(user.getRecoveryExpiration() < Instant.now().getEpochSecond())
            return "Token expired";

        user.setPassword(new BCryptPasswordEncoder().encode(request.getNewPassword()));
        userRepository.save(user);
        return "OK";
    }

    @Override
    public String getRecoveryPasswordToken(GetRecoveryPasswordTokenRequest request) {
        String token = UUID.randomUUID().toString();
        var user = userRepository.findByEmail(request.getEmail());
        if(user.isEmpty())
            return "User not found";
        var updatedUser = user.get();
        updatedUser.setRecoveryToken(token);
        updatedUser.setRecoveryExpiration(Instant.now().getEpochSecond() + 60*60);
        userRepository.save(updatedUser);
        mailService.sendSimpleMail(new MailDetails(updatedUser.getEmail(), "Recovery token", token));
        return "OK";
    }

}
