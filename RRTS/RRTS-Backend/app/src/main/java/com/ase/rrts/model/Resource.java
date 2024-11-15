package com.ase.rrts.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column
    private Integer count = 0;

    @Column(nullable = false, length = 50)
    private String type;

    @Column
    private Double cost = 0.0;

    @Column
    private Integer available = 0;
}
