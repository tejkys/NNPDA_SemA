package com.st61019.SemA.service.impl;

import com.st61019.SemA.dao.entities.SensorDTO;
import com.st61019.SemA.entities.Device;
import com.st61019.SemA.entities.Sensor;
import com.st61019.SemA.entities.User;
import com.st61019.SemA.repository.DeviceRepository;
import com.st61019.SemA.repository.SensorRepository;
import com.st61019.SemA.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private final SensorRepository sensorRepository;
    private final DeviceRepository deviceRepository;

    @Override
    public List<Sensor> getAll() {
        return sensorRepository.findAll();
    }

    @Override
    public Optional<Sensor> getById(int id) {
        return sensorRepository.findById(id);
    }
    @Override
    public Sensor create(SensorDTO sensorDTO) {
        Optional<Device> deviceOptional = deviceRepository.findById(sensorDTO.getDeviceId());
        if(deviceOptional.isEmpty())
            return null;
        var device = deviceOptional.get();
        var newSensor = new Sensor();
        newSensor.setName(sensorDTO.getName());
        newSensor.setDevice(device);
        sensorRepository.save(newSensor);
        return newSensor;
    }

    @Override
    public String update(SensorDTO sensorDTO) {
        var sensorOptional = sensorRepository.findById(sensorDTO.getId());
        if(sensorOptional.isEmpty())
            return "Sensor does not exist";
        var sensor = sensorOptional.get();
        sensor.setName(sensorDTO.getName());
        sensorRepository.save(sensor);
        return "OK";
    }

    @Override
    public String delete(int id) {
        var sensorOptional = sensorRepository.findById(id);
        if(sensorOptional.isEmpty())
            return "Sensor does not exist";
        var sensor = sensorOptional.get();
        sensorRepository.delete(sensor);
        return "OK";
    }
}
