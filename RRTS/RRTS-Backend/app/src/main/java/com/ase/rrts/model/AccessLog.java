package com.ase.rrts.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Access_Logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date logoutTime;
}