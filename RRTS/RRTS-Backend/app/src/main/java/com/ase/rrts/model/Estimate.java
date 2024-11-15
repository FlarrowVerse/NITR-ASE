package com.ase.rrts.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "Estimate", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"complaint_id", "resource_id", "material_id"})
})
@Getter
@Setter
public class Estimate {

    @Id
    @GeneratedValue
    @Column(name = "estimate_id", updatable = false, nullable = false)
    private UUID estimateId;

    @ManyToOne
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;
}
