package com.st61019.SemA.service.impl;

import com.st61019.SemA.dao.entities.DeviceDTO;
import com.st61019.SemA.dao.entities.SensorDTO;
import com.st61019.SemA.entities.Device;
import com.st61019.SemA.entities.Sensor;
import com.st61019.SemA.entities.User;
import com.st61019.SemA.repository.DeviceRepository;
import com.st61019.SemA.repository.UserRepository;
import com.st61019.SemA.service.DeviceService;
import com.st61019.SemA.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    @Override
    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Optional<Device> getById(int id) {
        return deviceRepository.findById(id);
    }


    @Override
    public Device create(DeviceDTO deviceDTO) {
        Optional<User> userOptional = userRepository.findById(deviceDTO.getUserId());
        if(userOptional.isEmpty())
            return null;
        var user = userOptional.get();
        var newDevice = new Device();
        newDevice.setName(deviceDTO.getName());
        newDevice.setUser(user);
        deviceRepository.save(newDevice);
        return newDevice;
    }

    @Override
    public String update(DeviceDTO sensorDTO) {
        var deviceOptional = deviceRepository.findById(sensorDTO.getId());
        if(deviceOptional.isEmpty())
            return "Device does not exist";
        var device = deviceOptional.get();
        device.setName(sensorDTO.getName());
        deviceRepository.save(device);
        return "OK";
    }

    @Override
    public String delete(int id) {
        var deviceOptional = deviceRepository.findById(id);
        if(deviceOptional.isEmpty())
            return "Device does not exist";
        var device = deviceOptional.get();
        deviceRepository.delete(device);
        return "OK";
    }

    @Override
    public List<Device> getByUserId(int id) {
        return deviceRepository.findByUserId(id);
    }

}
