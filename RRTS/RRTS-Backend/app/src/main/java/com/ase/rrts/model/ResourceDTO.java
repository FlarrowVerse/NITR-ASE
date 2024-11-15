package com.ase.rrts.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDTO {
    private Integer count, available;
    private Double cost;    
}
