package com.ase.rrts.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ase.rrts.model.Road;

@Repository
public interface RoadRepository extends JpaRepository<Road, Long> {
    // Custom queries to fetch roads, if needed
}
