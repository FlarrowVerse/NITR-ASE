package com.ase.rrts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ase.rrts.model.Schedule;

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import com.ase.rrts.model.Complaint;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

    List<Schedule> findByStatus(String status);

    List<Schedule> findByScheduledDate(LocalDate date);

    Optional<Schedule> findByComplaint(Complaint complaint);
}