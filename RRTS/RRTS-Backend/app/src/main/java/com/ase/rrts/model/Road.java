package com.ase.rrts.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "Roads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Road {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String roadName;

    @Column(nullable = false, length = 50)
    private String localityType;

    @ManyToOne
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @Column(columnDefinition = "TEXT")
    private String additionalInfo;
}
