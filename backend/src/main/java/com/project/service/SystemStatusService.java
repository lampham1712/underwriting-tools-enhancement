package com.project.service;

import com.project.dto.StatusSummary;
import com.project.model.Status;
import com.project.model.SystemStatus;
import com.project.model.SystemStatusLog;
import com.project.repository.SystemStatusLogRepository;
import com.project.repository.SystemStatusRepository;
import com.project.service.health.HealthCheckResult;
import com.project.service.health.HealthCheckStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SystemStatusService {

    private final SystemStatusRepository statusRepository;
    private final SystemStatusLogRepository logRepository;
    private final Map<String, HealthCheckStrategy> strategies;
    private final ExecutorService executorService;

    public SystemStatusService(SystemStatusRepository statusRepository,
                               SystemStatusLogRepository logRepository,
                               List<HealthCheckStrategy> strategyList) {
        this.statusRepository = statusRepository;
        this.logRepository = logRepository;
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(HealthCheckStrategy::getSystemCode, Function.identity()));
        this.executorService = Executors.newVirtualThreadPerTaskExecutor(); // Java 21 feature
    }

    public List<SystemStatus> getAllStatuses() {
        return statusRepository.findAll();
    }

    public StatusSummary getSummary() {
        List<SystemStatus> all = getAllStatuses();
        int total = all.size();
        int online = (int) all.stream().filter(s -> s.getStatus() == Status.ONLINE).count();
        // Offline includes OFFLINE, UNKNOWN, MAINTENANCE for calculation purposes
        long downCount = all.stream().filter(s -> s.getStatus() != Status.ONLINE).count();
        boolean criticalDown = all.stream()
                .anyMatch(s -> Boolean.TRUE.equals(s.getIsCritical()) && s.getStatus() != Status.ONLINE);

        StatusSummary.GlobalStatus globalStatus;
        if (criticalDown || downCount > 3) {
            globalStatus = StatusSummary.GlobalStatus.OFFLINE;
        } else if (downCount > 0) {
            globalStatus = StatusSummary.GlobalStatus.PARTIAL;
        } else {
            globalStatus = StatusSummary.GlobalStatus.ONLINE;
        }

        return new StatusSummary(total, online, (int) downCount, globalStatus);
    }

    public Optional<SystemStatus> getStatus(String systemCode) {
        return statusRepository.findBySystemCode(systemCode);
    }

    @Transactional
    public void refreshAllSystems() {
        strategies.values().forEach(strategy -> {
            executorService.submit(() -> performHealthCheck(strategy));
        });
    }

    public SystemStatus refreshSystem(String systemCode) {
        HealthCheckStrategy strategy = strategies.get(systemCode);
        if (strategy == null) {
             throw new IllegalArgumentException("Unknown system code: " + systemCode);
        }
        return performHealthCheck(strategy);
    }

    private SystemStatus performHealthCheck(HealthCheckStrategy strategy) {
        String code = strategy.getSystemCode();
        HealthCheckResult result = strategy.checkHealth();

        // Update SQL Entity
        SystemStatus status = statusRepository.findBySystemCode(code)
                .orElse(createInitialStatus(code)); // Should exist, but fail-safe

        status.setStatus(result.getStatus());
        status.setLastChecked(LocalDateTime.now());
        status.setNextCheck(LocalDateTime.now().plusMinutes(10));
        status.setErrorMessage(result.getErrorDetails());
        SystemStatus savedStatus = statusRepository.save(status);

        // Append to Mongo Log
        SystemStatusLog log = new SystemStatusLog(
                code,
                result.getStatus(),
                result.getErrorDetails(),
                (int) result.getResponseTimeMs()
        );
        logRepository.save(log);

        return savedStatus;
    }

    private SystemStatus createInitialStatus(String code) {
        SystemStatus status = new SystemStatus();
        status.setSystemCode(code);
        status.setDisplayName(code); // improved later
        status.setStatus(Status.UNKNOWN);
        return status;
    }
}
