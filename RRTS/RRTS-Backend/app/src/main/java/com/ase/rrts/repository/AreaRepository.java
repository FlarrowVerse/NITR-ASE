package com.ase.rrts.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ase.rrts.model.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
    // Custom queries can be added here if needed
}

