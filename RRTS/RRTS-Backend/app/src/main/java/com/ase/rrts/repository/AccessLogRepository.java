package com.ase.rrts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ase.rrts.model.AccessLog;

import java.util.UUID;
import java.util.List;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Integer> {

    List<AccessLog> findByUserId(UUID userId);
}
