package com.ase.rrts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RepairStatisticsDTO {
    private String repairType;
    private long count;
}
