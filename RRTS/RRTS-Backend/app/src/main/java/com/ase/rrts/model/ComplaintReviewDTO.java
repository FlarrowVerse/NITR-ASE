package com.ase.rrts.model;

import java.util.UUID;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ComplaintReviewDTO {

    private String status; // udpate the status
    private Integer severity; // update the severity
    private Integer priority; // assign a priority
    private UUID supervisorId; // assign the supervisor
}

