package com.ase.rrts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ase.rrts.exceptions.ResourceNotFoundException;
import com.ase.rrts.model.ResponseMessage;
import com.ase.rrts.model.Schedule;
import com.ase.rrts.model.ScheduleRequestDTO;
import com.ase.rrts.service.ScheduleService;

import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;


    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable UUID id) {
        return scheduleService.getScheduleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('SUP')")
    @GetMapping("/complaint/{id}")
    public ResponseEntity<?> getScheduleByComplaintId(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(scheduleService.getScheduleByComplaintId(id).orElseThrow(() -> new ResourceNotFoundException("Schedule not found")));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage("failed", "Could not find any such schedule"));
        }
    }

    @PreAuthorize("hasAuthority('SUP')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseMessage> updateScheduleById(@PathVariable UUID id, @RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        try {
            scheduleService.updateSchedule(id, scheduleRequestDTO);
            return ResponseEntity.ok(new ResponseMessage("success", "Updated scheduled"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage("failed", "Could not find any such schedule"));
        }
    }
}
