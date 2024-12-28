package com.st61019.SemA.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasureDTO {
    private int id;
    private int Value;
    private int sensorId;
}
