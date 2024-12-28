package com.st61019.SemA.repository;

import com.st61019.SemA.entities.Device;
import com.st61019.SemA.entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

}
