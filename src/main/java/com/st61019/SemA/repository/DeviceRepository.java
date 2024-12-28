package com.st61019.SemA.repository;

import com.st61019.SemA.entities.Device;
import com.st61019.SemA.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    List<Device> findByUserId(int id);
}
