package com.ase.rrts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ase.rrts.model.Complaint;
import com.ase.rrts.model.Estimate;
import com.ase.rrts.model.EstimateRequestDTO;
import com.ase.rrts.model.Material;
import com.ase.rrts.model.MaterialEstimateDTO;
import com.ase.rrts.model.Resource;
import com.ase.rrts.model.ResourceEstimateDTO;
import com.ase.rrts.repository.ComplaintRepository;
import com.ase.rrts.repository.EstimateRepository;
import com.ase.rrts.repository.MaterialRepository;
import com.ase.rrts.repository.ResourceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EstimateService {

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ScheduleService scheduleService;

    public List<Estimate> getAllEstimates() {
        return estimateRepository.findAll();
    }

    public Optional<Estimate> getEstimateById(UUID id) {
        return estimateRepository.findById(id);
    }

    public Estimate createEstimate(Estimate estimate) {
        return estimateRepository.save(estimate);
    }

    public Estimate updateEstimate(UUID id, Estimate estimate) {
        if (estimateRepository.existsById(id)) {
            return estimateRepository.save(estimate);
        }
        return null;
    }

    public void deleteEstimate(UUID id) {
        estimateRepository.deleteById(id);
    }

    public List<Estimate> createBatchEstimates(EstimateRequestDTO estimateRequestDTO) {
        List<Estimate> estimates = new ArrayList<>();

        // Fetch the Complaint object
        Complaint complaint = complaintRepository.findById(estimateRequestDTO.getComplaintId())
                .orElseThrow(() -> new RuntimeException("Complaint not found with id: " + estimateRequestDTO.getComplaintId()));

        // Process Resource Estimates
        for (ResourceEstimateDTO resourceEstimate : estimateRequestDTO.getResources()) {
            Resource resource = resourceRepository.findById(resourceEstimate.getResourceId())
                    .orElseThrow(() -> new RuntimeException("Resource not found with id: " + resourceEstimate.getResourceId()));

            Estimate estimate = new Estimate();
            estimate.setComplaint(complaint);
            estimate.setResource(resource);
            estimate.setMaterial(null); // No material
            estimate.setQuantity(resourceEstimate.getQuantity());
            estimates.add(estimate);
        }

        // Process Material Estimates
        for (MaterialEstimateDTO materialEstimate : estimateRequestDTO.getMaterials()) {
            Material material = materialRepository.findById(materialEstimate.getMaterialId())
                    .orElseThrow(() -> new RuntimeException("Material not found with id: " + materialEstimate.getMaterialId()));

            Estimate estimate = new Estimate();
            estimate.setComplaint(complaint);
            estimate.setResource(null); // No resource
            estimate.setMaterial(material);
            estimate.setQuantity(materialEstimate.getQuantity());
            estimates.add(estimate);
        }

        // schedule repair work
        scheduleService.scheduleRepair(estimateRequestDTO);

        // Save all estimates to the database
        return estimateRepository.saveAll(estimates);
    }
}
