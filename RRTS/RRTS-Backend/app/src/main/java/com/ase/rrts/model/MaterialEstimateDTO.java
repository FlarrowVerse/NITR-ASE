package com.ase.rrts.model;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
public class MaterialEstimateDTO {
    private UUID materialId;
    private Integer quantity;
}
