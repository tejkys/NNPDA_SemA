package com.st61019.SemA.service.impl;

import com.st61019.SemA.dao.request.ChangePasswordRequest;
import com.st61019.SemA.dao.request.UpdateUserRequest;
import com.st61019.SemA.entities.Sensor;
import com.st61019.SemA.entities.User;
import com.st61019.SemA.repository.UserRepository;
import com.st61019.SemA.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public String changePassword(String username, String password) {
        var user = getByUsername(username).get();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userRepository.save(user);

        return "OK";
    }

    @Override
    public String update(UpdateUserRequest req) {
        var user = userRepository.findById(req.getId());
        if(user.isEmpty())
            return "User does not exist";
        var userUpdate = user.get();
        userUpdate.setFirstName(req.getFirstName());
        userUpdate.setLastName(req.getLastName());
        userRepository.save(userUpdate);
        return "OK";
    }

    @Override
    public String delete(int id) {
        userRepository.deleteById(id);
        return "OK";
    }
}
