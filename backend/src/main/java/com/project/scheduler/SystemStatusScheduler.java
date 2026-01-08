package com.project.scheduler;

import com.project.service.SystemStatusService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SystemStatusScheduler {

    private final SystemStatusService systemStatusService;

    public SystemStatusScheduler(SystemStatusService systemStatusService) {
        this.systemStatusService = systemStatusService;
    }

    // Every 10 minutes (600,000 ms)
    @Scheduled(fixedRate = 600000)
    public void scheduleHealthChecks() {
        systemStatusService.refreshAllSystems();
    }
}
