package com.st61019.SemA.controller;


import com.st61019.SemA.dao.entities.UserDTO;
import com.st61019.SemA.dao.request.ChangePasswordRequest;
import com.st61019.SemA.dao.request.UpdateUserRequest;
import com.st61019.SemA.entities.Device;
import com.st61019.SemA.entities.User;
import com.st61019.SemA.service.DeviceService;
import com.st61019.SemA.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(maxAge = 3600)
public class UserController {
    private final UserService userService;
    private final DeviceService deviceService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("getAll")
    public ResponseEntity<List<UserDTO>> getAll() {

        return new ResponseEntity<>(userService.getAll().stream().map(UserDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") int id) {
        Optional<User> data = userService.getById(id);

        return data.map(item -> new ResponseEntity<>(new UserDTO(item), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/")
    public ResponseEntity<UserDTO> getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var user = userService.getByUsername(authentication.getName());

        return user.map(item -> new ResponseEntity<>(new UserDTO(item), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/getMyDevices")
    public ResponseEntity<List<Device>> getMyDevices() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var user = userService.getByUsername(authentication.getName());
        if(user.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<Device> devices = deviceService.getByUserId(user.get().getId());
        return user.map(item -> new ResponseEntity<>(devices, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest dao) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = userService.getByUsername(authentication.getName()).get();

        Authentication authenticationCheck = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), dao.getOldPassword()));
        if (!authenticationCheck.isAuthenticated())
            return new ResponseEntity<>("Bad credentials", HttpStatus.BAD_REQUEST);
        if(!dao.getNewPassword().equals(dao.getNewPasswordAgain()))
            return new ResponseEntity<>("Passwords are not equal", HttpStatus.BAD_REQUEST);
        if(dao.getNewPassword().length() < 6)
            return new ResponseEntity<>("Password is too weak", HttpStatus.BAD_REQUEST);

        var data = userService.changePassword(user.getUsername(), dao.getNewPassword());

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody UpdateUserRequest user) {

        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }
    @DeleteMapping("/")
    public ResponseEntity<String> delete(@RequestBody int id) {

        return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
    }
}
