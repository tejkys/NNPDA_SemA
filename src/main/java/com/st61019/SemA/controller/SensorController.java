package com.st61019.SemA.controller;

import com.st61019.SemA.dao.entities.SensorDTO;
import com.st61019.SemA.entities.Device;
import com.st61019.SemA.entities.Sensor;
import com.st61019.SemA.repository.SensorRepository;
import com.st61019.SemA.service.DeviceService;
import com.st61019.SemA.service.SensorService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sensor")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class SensorController {
    private final SensorService sensorService;
    private final DeviceService deviceService;
    @GetMapping("getAll")
    public ResponseEntity<List<Sensor>> getAll() {
        return new ResponseEntity<>(sensorService.getAll(), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<Sensor> getById(@PathVariable("id") int id) {
        Optional<Sensor> data = sensorService.getById(id);

        return data.map(sensor -> new ResponseEntity<>(sensor, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<Sensor> create(@RequestBody SensorDTO sensorDTO) {
        var response = sensorService.create(sensorDTO);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @PutMapping("/")
    public ResponseEntity<String> update(@RequestBody SensorDTO sensorDTO) {

        var response = sensorService.update(sensorDTO);
        return new ResponseEntity<>(response, response.equals("OK") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {

        var response = sensorService.delete(id);
        return new ResponseEntity<>(response, response.equals("OK") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
