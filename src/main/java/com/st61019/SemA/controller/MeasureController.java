package com.st61019.SemA.controller;

import com.st61019.SemA.dao.entities.MeasureDTO;
import com.st61019.SemA.dao.entities.SensorDTO;
import com.st61019.SemA.entities.Measure;
import com.st61019.SemA.entities.Sensor;
import com.st61019.SemA.repository.MeasureRepository;
import com.st61019.SemA.repository.SensorRepository;
import com.st61019.SemA.service.DeviceService;
import com.st61019.SemA.service.SensorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/measure")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MeasureController {
    private final MeasureRepository measureRepository;
    private final SensorRepository sensorRepository;

    @GetMapping("getAll")
    public ResponseEntity<List<Measure>> getAll() {

        return new ResponseEntity<>(measureRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<Measure> getById(@PathVariable("id") int id) {
        Optional<Measure> data = measureRepository.findById(id);

        return data.map(sensor -> new ResponseEntity<>(sensor, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<MeasureDTO> create(@RequestBody MeasureDTO measureDTO) {
        var measure = new Measure();
        measure.setValue(measureDTO.getValue());
        measure.setSensor(sensorRepository.findById(measureDTO.getSensorId()).get());
        measureRepository.save(measure);
        measureDTO.setId(measure.getId());
        return new ResponseEntity<>(measureDTO,HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Measure> update(@RequestBody Measure measure) {

        measureRepository.save(measure);
        return new ResponseEntity<>(measure, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        measureRepository.deleteById(id);
        return new ResponseEntity<>("OK",  HttpStatus.OK);
    }
}
