package com.st61019.SemA.service;

import com.st61019.SemA.dao.entities.DeviceDTO;
import com.st61019.SemA.dao.entities.SensorDTO;
import com.st61019.SemA.entities.Device;
import com.st61019.SemA.entities.Sensor;
import com.st61019.SemA.entities.User;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    List<Device> getAll();
    Optional<Device> getById(int id);

    Device create(DeviceDTO sensorDTO);
    String update(DeviceDTO sensorDTO);

    String delete(int id);
    List<Device> getByUserId(int id);
}
