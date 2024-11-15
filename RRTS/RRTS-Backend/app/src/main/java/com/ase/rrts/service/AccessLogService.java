package com.ase.rrts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ase.rrts.model.AccessLog;
import com.ase.rrts.repository.AccessLogRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccessLogService {

    @Autowired
    private AccessLogRepository accessLogRepository;

    public List<AccessLog> getAllAccessLogs() {
        return accessLogRepository.findAll();
    }

    public Optional<AccessLog> getAccessLogById(Integer id) {
        return accessLogRepository.findById(id);
    }

    public AccessLog createAccessLog(AccessLog accessLog) {
        return accessLogRepository.save(accessLog);
    }

    public void deleteAccessLog(Integer id) {
        accessLogRepository.deleteById(id);
    }
}

