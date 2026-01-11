package com.project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "system_status_logs")
public class SystemStatusLog {

    @Id
    private String id;

    @Field("system_code")
    private String systemCode;

    @Field("check_timestamp")
    private LocalDateTime checkTimestamp;

    @Field("status_result")
    private Status statusResult;

    @Field("error_details")
    private String errorDetails;

    @Field("response_time_ms")
    private Integer responseTimeMs;

    @Field("created_at")
    private LocalDateTime createdAt;

    public SystemStatusLog() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }


    public SystemStatusLog(String systemCode, Status statusResult, String errorDetails, Integer responseTimeMs) {
        this();
        this.systemCode = systemCode;
        this.statusResult = statusResult;
        this.errorDetails = errorDetails;
        this.responseTimeMs = responseTimeMs;
        this.checkTimestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSystemCode() { return systemCode; }
    public void setSystemCode(String systemCode) { this.systemCode = systemCode; }

    public LocalDateTime getCheckTimestamp() { return checkTimestamp; }
    public void setCheckTimestamp(LocalDateTime checkTimestamp) { this.checkTimestamp = checkTimestamp; }

    public Status getStatusResult() { return statusResult; }
    public void setStatusResult(Status statusResult) { this.statusResult = statusResult; }

    public String getErrorDetails() { return errorDetails; }
    public void setErrorDetails(String errorDetails) { this.errorDetails = errorDetails; }

    public Integer getResponseTimeMs() { return responseTimeMs; }
    public void setResponseTimeMs(Integer responseTimeMs) { this.responseTimeMs = responseTimeMs; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
