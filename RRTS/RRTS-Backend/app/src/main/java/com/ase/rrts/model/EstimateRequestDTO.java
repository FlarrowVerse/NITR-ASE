package com.ase.rrts.model;

import java.util.List;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstimateRequestDTO {
    private UUID complaintId;
    private List<ResourceEstimateDTO> resources;
    private List<MaterialEstimateDTO> materials;
}
