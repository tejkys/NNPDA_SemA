package com.st61019.SemA.service;

import com.st61019.SemA.dao.entities.SensorDTO;
import com.st61019.SemA.entities.Sensor;
import com.st61019.SemA.entities.User;

import java.util.List;
import java.util.Optional;

public interface SensorService {
    List<Sensor> getAll();
    Optional<Sensor> getById(int id);

    Sensor create(SensorDTO sensorDTO);
    String update(SensorDTO sensorDTO);

    String delete(int id);

}
