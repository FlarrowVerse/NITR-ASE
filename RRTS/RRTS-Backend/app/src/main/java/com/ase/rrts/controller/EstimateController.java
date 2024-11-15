package com.ase.rrts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ase.rrts.model.Estimate;
import com.ase.rrts.model.EstimateRequestDTO;
import com.ase.rrts.model.ResponseMessage;
import com.ase.rrts.service.EstimateService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/estimates")
public class EstimateController {

    @Autowired
    private EstimateService estimateService;

    @GetMapping
    public List<Estimate> getAllEstimates() {
        return estimateService.getAllEstimates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estimate> getEstimateById(@PathVariable UUID id) {
        return estimateService.getEstimateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('SUP')")
    @PostMapping("/batch")
    public ResponseEntity<ResponseMessage> createBatchEstimates(@RequestBody EstimateRequestDTO estimateRequestDTO) {
        try {
            estimateService.createBatchEstimates(estimateRequestDTO);
            return ResponseEntity.ok(new ResponseMessage("success", "Estimates saved"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage("failed", "Could not save Estimates"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estimate> updateEstimate(@PathVariable UUID id, @RequestBody Estimate estimate) {
        return ResponseEntity.ok(estimateService.updateEstimate(id, estimate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstimate(@PathVariable UUID id) {
        estimateService.deleteEstimate(id);
        return ResponseEntity.noContent().build();
    }
}

