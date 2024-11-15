package com.ase.rrts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ase.rrts.service.StatisticsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.ase.rrts.model.RepairStatisticsDTO;
import com.ase.rrts.model.ResourceUtilizationDTO;
import com.ase.rrts.model.Schedule;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@Tag(name = "Statistics", description = "Endpoints related to mayor services")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @PreAuthorize("hasAuthority('MAYOR')")
    @GetMapping("/repairs")
    @Operation(summary = "Repairs By Period", description = "Retrieves number and types of completed repairs in given duration")
    public List<RepairStatisticsDTO> getRepairsByPeriod(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return statisticsService.getRepairsByPeriod(startDate, endDate);
    }

    @PreAuthorize("hasAuthority('MAYOR')")
    @GetMapping("/outstanding-repairs")
    @Operation(summary = "Outstanding Repairs", description = "Retrieves list of pending complaints or repair works")
    public List<Schedule> getOutstandingRepairs() {
        return statisticsService.getOutstandingRepairs();
    }

    @PreAuthorize("hasAuthority('MAYOR')")
    @GetMapping("/utilization")
    @Operation(summary = "Resource Utilization", description = "Retrieves resource utilization of personnel and machinery")
    public ResourceUtilizationDTO getResourceUtilization(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return statisticsService.getResourceUtilization(startDate, endDate);
    }
}

