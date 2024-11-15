package com.ase.rrts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ase.rrts.exceptions.ResourceNotFoundException;
import com.ase.rrts.model.Complaint;
import com.ase.rrts.model.ComplaintDTO;
import com.ase.rrts.model.ComplaintReviewDTO;
import com.ase.rrts.model.Road;
import com.ase.rrts.model.Users;
import com.ase.rrts.repository.ComplaintRepository;
import com.ase.rrts.repository.RoadRepository;
import com.ase.rrts.repository.UserRepository;


import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private RoadRepository roadRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Optional<Complaint> getComplaintById(UUID id) {
        return complaintRepository.findById(id);
    }

    public List<Complaint> getComplaintsByArea(Long areaId) {
        return complaintRepository.findByRoad_AreaId(areaId);
    }

    public Complaint createComplaint(ComplaintDTO complaintDTO) {
        Complaint complaint = new Complaint(complaintDTO.getComplaintDate(), complaintDTO.getRoadId(), roadRepository,
                complaintDTO.getRepairType(), complaintDTO.getDescription(), complaintDTO.getResidentName(),
                complaintDTO.getResidentContact());

        Road road = roadRepository.findById(complaintDTO.getRoadId()).orElseThrow(() -> new ResourceNotFoundException("No such road"));
        List<Users> supervisors = userRepository.findByArea(road.getArea()).orElseThrow(() -> new ResourceNotFoundException("No supervisors available at the moment"));
        
        if (supervisors != null && !supervisors.isEmpty()) {
            // Randomly select a supervisor if multiple supervisors exist
            Random rand = new Random();
            Users selectedSupervisor = supervisors.get(rand.nextInt(supervisors.size()));

            // Assign the selected supervisor to the complaint
            complaint.setSupervisor(selectedSupervisor);
        }

        return complaintRepository.save(complaint);
    }

    public void updateComplaint(UUID id, ComplaintReviewDTO complaintReviewDTO) {
        Complaint current = complaintRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Complaint with given ID not found!"));
        
        if (!complaintReviewDTO.getStatus().isEmpty()) {
            current.setStatus(complaintReviewDTO.getStatus());
        }

        if (complaintReviewDTO.getPriority() != null) {
            current.setPriority(complaintReviewDTO.getPriority());
        }

        if (complaintReviewDTO.getSeverity() != null) {
            current.setSeverity(complaintReviewDTO.getSeverity());
        }

        if (complaintReviewDTO.getSupervisorId() != null) {
            Users supervisor = userRepository.findById(complaintReviewDTO.getSupervisorId()).orElseThrow(() -> new ResourceNotFoundException("No such user"));
            current.setSupervisor(supervisor);
        }
        complaintRepository.save(current); // save the complaint
    }

    public Complaint updateComplaint(UUID id, Complaint complaint) {
        if (complaintRepository.existsById(id)) {
            complaint.setId(id);
            return complaintRepository.save(complaint);
        }
        return null;
    }

    public void deleteComplaint(UUID id) {
        complaintRepository.deleteById(id);
    }
}
