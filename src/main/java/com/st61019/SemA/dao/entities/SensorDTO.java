package com.st61019.SemA.dao.entities;

import com.st61019.SemA.entities.Sensor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorDTO {
    private int id;
    private String name;
    private int deviceId;
}
