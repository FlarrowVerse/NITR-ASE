package com.ase.rrts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ase.rrts.model.Complaint;
import com.ase.rrts.model.ComplaintDTO;
import com.ase.rrts.model.ComplaintReviewDTO;
import com.ase.rrts.model.ResponseMessage;
import com.ase.rrts.service.ComplaintService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/complaints")
@Tag(name = "Complaints", description = "Endpoints related to complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;


    @GetMapping("/status/{id}")
    @Operation(summary = "Get complaint status", description = "Retrieve a single complaint's status only, for unauthenticated users")
    public ResponseEntity<String> getComplaintStatusById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(complaintService.getComplaintById(id).get().getStatus());
        } catch (Exception e) {
            return ResponseEntity.ok("Request Not Found");
        }
    }



    @PreAuthorize("hasAuthority('CLK')")
    @GetMapping("/complaintList")
    @Operation(summary = "Get all complaints", description = "Retrieve a list of all complaints")
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @PreAuthorize("hasAuthority('SUP')")
    @GetMapping("/view/{id}")
    @Operation(summary = "Get complaint", description = "Retrieve a single complaint")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable UUID id) {
        return complaintService.getComplaintById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('CLK')")
    @GetMapping("/complaintList/{areaId}")
    @Operation(summary = "Area-wise complaints", description = "Retrieve a list of all complaints area-wise")
    public ResponseEntity<List<Complaint>> getComplaintsByArea(@PathVariable Long areaId) {
        try {
            return ResponseEntity.ok(complaintService.getComplaintsByArea(areaId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('CLK')")
    @PostMapping("/add")
    @Operation(summary = "Add complaints", description = "Adds new complaints to the database")
    public ResponseEntity<ResponseMessage> createComplaint(@RequestBody ComplaintDTO complaintDTO) {
        try {
            complaintService.createComplaint(complaintDTO);
            return ResponseEntity.ok(new ResponseMessage("success", "Complaint created successfully"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage("failure", "Complaint could not be saved"));
        }
    }

    @PreAuthorize("hasAuthority('SUP')")
    @PatchMapping("/update/{id}")
    @Operation(summary = "Update complaints", description = "Updates a complaint given the complaint id")
    public ResponseEntity<ResponseMessage> updateComplaint(@PathVariable UUID id, @RequestBody ComplaintReviewDTO complaintReviewDTO) {
        try {
            complaintService.updateComplaint(id, complaintReviewDTO);
            return ResponseEntity.ok(new ResponseMessage("success", "Complaint updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage("failure", "Complaint could not be updated"));
        }
    }
}

