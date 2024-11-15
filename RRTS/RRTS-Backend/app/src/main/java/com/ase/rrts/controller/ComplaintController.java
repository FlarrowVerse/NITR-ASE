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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PreAuthorize("hasAuthority('CLK')")
    @GetMapping("/complaintList")
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @PreAuthorize("hasAuthority('SUP')")
    @GetMapping("/view/{id}")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable UUID id) {
        return complaintService.getComplaintById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('CLK')")
    @GetMapping("/complaintList/{areaId}")
    public ResponseEntity<List<Complaint>> getComplaintsByArea(@PathVariable Long areaId) {
        try {
            return ResponseEntity.ok(complaintService.getComplaintsByArea(areaId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('CLK')")
    @PostMapping("/add")
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
    public ResponseEntity<ResponseMessage> updateComplaint(@PathVariable UUID id, @RequestBody ComplaintReviewDTO complaintReviewDTO) {
        try {
            complaintService.updateComplaint(id, complaintReviewDTO);
            return ResponseEntity.ok(new ResponseMessage("success", "Complaint updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage("failure", "Complaint could not be updated"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Complaint> updateComplaint(@PathVariable UUID id, @RequestBody Complaint complaint) {
        return ResponseEntity.ok(complaintService.updateComplaint(id, complaint));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable UUID id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.noContent().build();
    }
}

