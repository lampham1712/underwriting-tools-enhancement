package com.project.controller;

import com.project.dto.StatusSummary;
import com.project.model.SystemStatus;
import com.project.service.SystemStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system-status")
@CrossOrigin(origins = "*") // Allow frontend access
public class SystemStatusController {

    private final SystemStatusService systemStatusService;

    public SystemStatusController(SystemStatusService systemStatusService) {
        this.systemStatusService = systemStatusService;
    }

    @GetMapping("/summary")
    public StatusSummary getSummary() {
        return systemStatusService.getSummary();
    }

    @GetMapping
    public List<SystemStatus> getAllSystemStatuses() {
        return systemStatusService.getAllStatuses();
    }

    @PostMapping("/refresh/{code}")
    public ResponseEntity<SystemStatus> refreshSystem(@PathVariable String code) {
        try {
            SystemStatus status = systemStatusService.refreshSystem(code);
            return ResponseEntity.ok(status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/refresh-all")
    public ResponseEntity<Void> refreshAllSystems() {
        systemStatusService.refreshAllSystems();
        return ResponseEntity.accepted().build();
    }
}
