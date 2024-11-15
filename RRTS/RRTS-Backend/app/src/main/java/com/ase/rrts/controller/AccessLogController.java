package com.ase.rrts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ase.rrts.model.AccessLog;
import com.ase.rrts.service.AccessLogService;

import java.util.List;

@RestController
@RequestMapping("/api/access-logs")
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;

    @GetMapping
    public List<AccessLog> getAllAccessLogs() {
        return accessLogService.getAllAccessLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessLog> getAccessLogById(@PathVariable Integer id) {
        return accessLogService.getAccessLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AccessLog> createAccessLog(@RequestBody AccessLog accessLog) {
        return ResponseEntity.ok(accessLogService.createAccessLog(accessLog));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessLog(@PathVariable Integer id) {
        accessLogService.deleteAccessLog(id);
        return ResponseEntity.noContent().build();
    }
}

