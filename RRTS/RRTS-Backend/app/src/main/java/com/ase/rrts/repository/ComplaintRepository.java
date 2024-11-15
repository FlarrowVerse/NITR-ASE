package com.ase.rrts.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ase.rrts.model.Complaint;

import java.util.UUID;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {

    List<Complaint> findBySupervisorId(UUID supervisorId);

    List<Complaint> findByStatus(String status);
    
    List<Complaint> findByPriority(Integer priority);

    List<Complaint> findByRoad_AreaId(Long areaId);
}

