package com.ase.rrts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ase.rrts.model.Estimate;
import com.ase.rrts.model.EstimateRequestDTO;
import com.ase.rrts.model.ResponseMessage;
import com.ase.rrts.service.EstimateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/estimates")
@Tag(name = "Estimates", description = "Endpoints related to estimates")
public class EstimateController {

    @Autowired
    private EstimateService estimateService;

    @PreAuthorize("hasAuthority('SUP')")
    @GetMapping
    @Operation(summary = "Get all estimates", description = "Retrieve a list of all estimates")
    public List<Estimate> getAllEstimates() {
        return estimateService.getAllEstimates();
    }

    @PreAuthorize("hasAuthority('SUP')")
    @GetMapping("/{id}")
    @Operation(summary = "Single estimate", description = "Retrieve a single estimate")
    public ResponseEntity<Estimate> getEstimateById(@PathVariable UUID id) {
        return estimateService.getEstimateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('SUP')")
    @PostMapping("/batch")
    @Operation(summary = "Batch Submit", description = "Submits all estimates for a complaint")
    public ResponseEntity<ResponseMessage> createBatchEstimates(@RequestBody EstimateRequestDTO estimateRequestDTO) {
        try {
            estimateService.createBatchEstimates(estimateRequestDTO);
            return ResponseEntity.ok(new ResponseMessage("success", "Estimates saved"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage("failed", "Could not save Estimates"));
        }
    }

    @PreAuthorize("hasAuthority('SUP')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Estimate", description = "Deletes an estimate based on UUID")
    public ResponseEntity<Void> deleteEstimate(@PathVariable UUID id) {
        estimateService.deleteEstimate(id);
        return ResponseEntity.noContent().build();
    }
}

