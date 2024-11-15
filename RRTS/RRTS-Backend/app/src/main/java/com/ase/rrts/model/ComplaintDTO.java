package com.ase.rrts.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ComplaintDTO {

    private String description;
    private Long roadId;  // The roadId received from the client
    private String complaintDate;
    private String repairType;
    private String residentName;
    private String residentContact;
}

