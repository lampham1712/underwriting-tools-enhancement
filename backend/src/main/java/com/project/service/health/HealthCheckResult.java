package com.project.service.health;

import com.project.model.Status;

public class HealthCheckResult {
    private final Status status;
    private final long responseTimeMs;
    private final String errorDetails;

    public HealthCheckResult(Status status, long responseTimeMs, String errorDetails) {
        this.status = status;
        this.responseTimeMs = responseTimeMs;
        this.errorDetails = errorDetails;
    }

    public static HealthCheckResult success(long responseTimeMs) {
        return new HealthCheckResult(Status.ONLINE, responseTimeMs, null);
    }

    public static HealthCheckResult failure(String errorDetails, long responseTimeMs) {
        return new HealthCheckResult(Status.OFFLINE, responseTimeMs, errorDetails);
    }
    
    public static HealthCheckResult failure(String errorDetails) {
        return new HealthCheckResult(Status.OFFLINE, 0, errorDetails);
    }

    public Status getStatus() { return status; }
    public long getResponseTimeMs() { return responseTimeMs; }
    public String getErrorDetails() { return errorDetails; }
}
