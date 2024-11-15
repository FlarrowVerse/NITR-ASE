package com.ase.rrts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ase.rrts.exceptions.ResourceNotFoundException;
import com.ase.rrts.model.Complaint;
import com.ase.rrts.model.Estimate;
import com.ase.rrts.model.EstimateRequestDTO;
import com.ase.rrts.model.Schedule;
import com.ase.rrts.model.ScheduleRequestDTO;
import com.ase.rrts.repository.ComplaintRepository;
import com.ase.rrts.repository.EstimateRepository;
import com.ase.rrts.repository.ResourceRepository;
import com.ase.rrts.repository.ScheduleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScheduleService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EstimateRepository estimateRepository;

    @Autowired
    private ResourceRepository resourceRepository;
    

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById(UUID id) {
        return scheduleRepository.findById(id);
    }

    public Optional<Schedule> getScheduleByComplaintId(UUID id) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such complaint!"));
        return scheduleRepository.findByComplaint(complaint);
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void updateSchedule(UUID id, ScheduleRequestDTO scheduleRequestDTO) {
        if (scheduleRepository.existsById(id)) {
            Schedule schedule = scheduleRepository.findById(id).orElseThrow();
            
            if (scheduleRequestDTO.getStatus() != null) {
                schedule.setStatus(scheduleRequestDTO.getStatus());
                if (schedule.getStatus().equalsIgnoreCase("Done")) {
                    // completed
                    // release resources
                    releaseResourceEstimate(schedule.getComplaint());
                }
            }
            
            if (scheduleRequestDTO.getStartDate() != null) {
                schedule.setStartDate(LocalDate.parse(scheduleRequestDTO.getStartDate()));
            }
            
            if (scheduleRequestDTO.getEndDate() != null) {
                schedule.setEndDate(LocalDate.parse(scheduleRequestDTO.getEndDate()));
            }
            scheduleRepository.save(schedule); // save the schedule
        }
        
    }

    public void releaseResourceEstimate(Complaint complaint) {
        List<Estimate> estimates = estimateRepository.findByComplaint(complaint);
        for (Estimate estimate: estimates) {
            if (estimate.getResource() != null) { // resource estimate
                Integer currAvailable = estimate.getResource().getAvailable();
                estimate.getResource().setAvailable(currAvailable + estimate.getQuantity()); // add to the current available
                resourceRepository.save(estimate.getResource()); // update the resource
            }
        }
    }

    public void scheduleRepair(EstimateRequestDTO estimateDTO) {
        createScheduleForComplaint(estimateDTO); // create and save schedule
    }

    @Transactional
    public Schedule createScheduleForComplaint(EstimateRequestDTO estimateDTO) {
        Complaint complaint = complaintRepository.findById(estimateDTO.getComplaintId()).orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        // Validate and check availability of resources and materials (personnel, machinery, raw materials)
        boolean resourcesAvailable = resourceService.checkResourceAvailability(estimateDTO.getResources());
        boolean materialsAvailable = materialService.checkMaterialAvailability(estimateDTO.getMaterials());

        if (!resourcesAvailable) {
            throw new ResourceNotFoundException("Required resources are unavailable");
        }
        if (!materialsAvailable) {
            throw new ResourceNotFoundException("Required materials are unavailable");
        }

        // Create the schedule entry
        Schedule schedule = new Schedule();

        schedule.setComplaint(complaint);
        schedule.setScheduledDate(getStartDate(complaint)); // target start date
        schedule.setStartDate(getStartDate(complaint)); // target start date
        schedule.setDeadline(getEndDate(complaint)); // deadline different from actual end date
        schedule.setEndDate(getEndDate(complaint)); // deadline different from actual end date
        schedule.setStatus("Ready"); // scheduled and ready to start

        // Save the schedule to the database
        schedule = scheduleRepository.save(schedule);

        // Deduct resources and materials after scheduling
        resourceService.updateResourceAvailability(estimateDTO.getResources()); // deduct
        materialService.updateMaterialAvailability(estimateDTO.getMaterials());

        return schedule;
    }

    private LocalDate getStartDate(Complaint complaint) {
        LocalDate startDate = LocalDate.now().plusDays(2); // current date + buffer for resource availability
        return startDate.plusDays(complaint.getPriority());
    }

    private LocalDate getEndDate(Complaint complaint) {
        LocalDate endDate = LocalDate.now().plusDays(3); // current date + buffer for resource availability
        return endDate.plusDays(getExpectedDuration(complaint.getSeverity()));
    }

    private Integer getExpectedDuration(Integer severity) {
        switch (severity) {
            case 1: return 20; // Critical
            case 2: return 14; // High
            case 3: return 7; // Medium
            case 4: return 3; // Low
            default: return Integer.MAX_VALUE-30; // extremely high value
        }
    }
}

