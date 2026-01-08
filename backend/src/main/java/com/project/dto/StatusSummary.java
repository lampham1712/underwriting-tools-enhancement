package com.project.dto;

public record StatusSummary(
    int totalSystems,
    int onlineCount,
    int offlineCount,
    GlobalStatus globalStatus
) {
    public enum GlobalStatus {
        ONLINE,
        PARTIAL,
        OFFLINE
    }
}
