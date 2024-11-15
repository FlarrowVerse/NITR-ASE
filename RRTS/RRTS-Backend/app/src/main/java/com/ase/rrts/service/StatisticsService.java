package com.ase.rrts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ase.rrts.model.RepairStatisticsDTO;
import com.ase.rrts.model.Resource;
import com.ase.rrts.model.ResourceUtilizationDTO;
import com.ase.rrts.model.Schedule;
import com.ase.rrts.repository.ResourceRepository;
import com.ase.rrts.repository.ScheduleRepository;

@Service
public class StatisticsService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    public List<RepairStatisticsDTO> getRepairsByPeriod(LocalDate startDate, LocalDate endDate) {
        List<Schedule> schedules = scheduleRepository.findCompletedRepairsInPeriod(startDate, endDate);
        List<RepairStatisticsDTO> repairs = new ArrayList<>();

        for (Schedule schedule : schedules) {
            for (int i = 0; i < repairs.size(); i++) {
                if (repairs.get(i).getRepairType().equalsIgnoreCase(schedule.getComplaint().getRepairType())) {
                    repairs.get(i).setCount(repairs.get(i).getCount() + 1);
                } else {
                    repairs.add(new RepairStatisticsDTO(schedule.getComplaint().getRepairType(), 1));
                }
            }
        }
        return repairs;
    }

    public List<Schedule> getOutstandingRepairs() {
        return scheduleRepository.findOutstandingRepairs();
    }

    public ResourceUtilizationDTO getResourceUtilization(LocalDate startDate, LocalDate endDate) {
        List<Resource> resourceList = resourceRepository.findAll();
        Double personnelUtilization = 0.0, personnel = 0.0, personnelWorking = 0.0;
        Double machineryUtilization = 0.0, machinery = 0.0, machineryUsed = 0.0;

        for (Resource resource : resourceList) {
            if (resource.getType().equalsIgnoreCase("Personnel")) {
                personnel += resource.getCount(); personnelWorking += (resource.getCount() - resource.getAvailable());
            } else {
                machinery += resource.getCount(); machineryUsed += (resource.getCount() - resource.getAvailable());
            }
        }

        personnelUtilization = Double.parseDouble(String.format("%.2f", ((personnelWorking / personnel) * 100)));
        machineryUtilization = Double.parseDouble(String.format("%.2f", ((machineryUsed / machinery) * 100)));
        return new ResourceUtilizationDTO(personnelUtilization, machineryUtilization);
    }
    
}
