package com.st61019.SemA.service;

import com.st61019.SemA.dao.request.ChangePasswordRequest;
import com.st61019.SemA.dao.request.UpdateUserRequest;
import com.st61019.SemA.entities.Sensor;
import com.st61019.SemA.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDetailsService userDetailsService();

    List<User> getAll();
    Optional<User> getById(int id);
    Optional<User> getByUsername(String username);
    String changePassword(String username,String password);
    String update(UpdateUserRequest user);
    String delete(int id);

}
