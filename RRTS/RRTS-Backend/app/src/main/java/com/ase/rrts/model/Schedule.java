package com.ase.rrts.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDate;

@Entity
@Table(name = "Schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate scheduledDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate deadline;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Column(length = 7)
    private String status;
}

