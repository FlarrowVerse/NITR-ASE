package com.ase.rrts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceUtilizationDTO {
    private double personnelUtilizationPercentage;
    private double machineryUtilizationPercentage;
}
