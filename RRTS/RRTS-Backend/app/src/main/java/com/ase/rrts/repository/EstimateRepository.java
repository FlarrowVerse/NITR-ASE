package com.ase.rrts.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ase.rrts.model.Complaint;
import com.ase.rrts.model.Estimate;
import java.util.List;


@Repository
public interface EstimateRepository extends JpaRepository<Estimate, UUID> {
    // Custom queries for estimates can be added if necessary
    List<Estimate> findByComplaint(Complaint complaint);
}
