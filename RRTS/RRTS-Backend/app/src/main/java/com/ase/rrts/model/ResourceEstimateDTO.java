package com.ase.rrts.model;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
public class ResourceEstimateDTO {
    private UUID resourceId;
    private Integer quantity;
}
