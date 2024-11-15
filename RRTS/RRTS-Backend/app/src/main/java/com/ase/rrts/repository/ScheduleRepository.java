package com.ase.rrts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    List<Schedule> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Schedule> findByEndDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT s FROM Schedule s WHERE s.status = 'Done' AND s.startDate >= :startDate AND s.endDate <= :endDate")
    List<Schedule> findCompletedRepairsInPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM Schedule s WHERE s.status <> 'Done'")
    List<Schedule> findOutstandingRepairs();
}
