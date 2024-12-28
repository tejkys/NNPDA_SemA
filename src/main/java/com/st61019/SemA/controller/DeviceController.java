package com.st61019.SemA.controller;

import com.st61019.SemA.dao.entities.DeviceDTO;
import com.st61019.SemA.dao.entities.SensorDTO;
import com.st61019.SemA.entities.Device;
import com.st61019.SemA.entities.Sensor;
import com.st61019.SemA.service.AuthenticationService;
import com.st61019.SemA.service.DeviceService;
import com.st61019.SemA.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/device")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(maxAge = 3600)
public class DeviceController {
    private final DeviceService deviceService;
    private final UserService userService;

    @GetMapping("getAll")
    public ResponseEntity<List<Device>> getAll() {
        return new ResponseEntity<>(deviceService.getAll(), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<Device> getById(@PathVariable("id") int id) {
        Optional<Device> data = deviceService.getById(id);

        return data.map(sensor -> new ResponseEntity<>(sensor, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<Device> create(@RequestBody DeviceDTO deviceDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var user = userService.getByUsername(authentication.getName());
        if(user.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        deviceDTO.setUserId(user.get().getId());
        var response = deviceService.create(deviceDTO);
        return new ResponseEntity<>(response,  HttpStatus.CREATED);
    }
    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody DeviceDTO deviceDTO) {

        var response = deviceService.update(deviceDTO);
        return new ResponseEntity<>(response, response.equals("OK") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {

        var response = deviceService.delete(id);
        return new ResponseEntity<>(response, response.equals("OK") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
