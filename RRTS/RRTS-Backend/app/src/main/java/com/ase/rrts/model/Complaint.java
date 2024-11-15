package com.ase.rrts.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.UUID;

import com.ase.rrts.repository.RoadRepository;

import java.time.LocalDate;

@Entity
@Table(name = "Complaints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate complaintDate;

    @ManyToOne
    @JoinColumn(name = "road_id", nullable = false)
    private Road road;

    @Column(nullable = false, length = 50)
    private String repairType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String residentName;

    @Column(length = 50)
    private String residentContact;

    @Column(nullable = false, length = 50)
    private String status;

    @Column
    private Integer severity;

    @Column
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Users supervisor;

    /**
     * parameterized constructor
     * @param date
     * @param roadId
     * @param repairType
     * @param description
     * @param residentname
     * @param residentContact
     */
    public Complaint(String date, Long roadId, RoadRepository roadRepository, String repairType, String description, String residentname, String residentContact) {
        this.id = UUID.randomUUID();
        this.complaintDate = LocalDate.parse(date);
        this.road = roadRepository.findById(roadId).orElseThrow(() -> new IllegalArgumentException("Road not found!"));
        this.repairType = repairType;
        this.description = description;
        this.residentName = residentname;
        this.residentContact = residentContact;

        this.status = "New";
        this.severity = 4; // lowest
        this.priority = -1; // least important
        this.setSupervisor(null); // no supervisor
    }
}

