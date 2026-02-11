# Test Scenarios: System Status Card

These test scenarios verify the implementation of the System Status Card feature, covering Backend Services, API Endpoints, and Scheduler logic.

## 1. Unit Tests (Backend)

### 1.1 SystemStatusService
*   **Goal**: Verify business logic for aggregating status and handling health checks.
*   **Case 1.1.1: Get Summary**: 
    *   Setup: Mock Repository to return 5 Online, 2 Offline.
    *   Expect: Summary object with `online=5`, `offline=2`, `total=7`.
*   **Case 1.1.2: Refresh Status (Success)**:
    *   Setup: Mock `HealthCheckStrategy` for "CCR" system returns `Success`.
    *   Action: Trigger `refreshAll()`.
    *   Expect: 
        *   `SystemStatusRepository.save()` is called with updated timestamp.
        *   `SystemStatusLogRepository.save()` is called.
*   **Case 1.1.3: Refresh Status (Failure)**:
    *   Setup: Mock `HealthCheckStrategy` returns `Failure`.
    *   Action: Trigger `refreshAll()`.
    *   Expect: Status updated to `OFFLINE`.

### 1.2 HealthCheckStrategies
*   **Goal**: Verify individual strategies handle their specific protocols.
*   **Case 1.2.1: RestStrategy Success**: Mock simple HTTP 200 OK.
*   **Case 1.2.2: RestStrategy Timeout**: Mock SocketTimeoutException (verifies config).
*   **Case 1.2.3: Jt400Strategy**: Verify resources are closed (using spy/mock).

## 2. Integration Tests (Backend)

### 2.1 SystemStatusController (API)
*   **Goal**: Verify HTTP contract.
*   **Case 2.1.1: GET /api/system-status/summary**: Returns 200 OK and JSON structure matching Frontend expectations.
*   **Case 2.1.2: GET /api/system-status**: Returns list of all systems.

## 3. End-to-End / Manual Verification

### 3.1 Data Persistence
*   **Goal**: Verify H2 DB retains state between refreshes (but resets on restart).
*   Mock a refresh cycles and query H2 Console (if enabled) or API to see Log history.

### 3.2 Frontend Integration
*   **Goal**: Verify React App displays data.
*   **Action**: Start Backend + Frontend.
*   **Expect**: Dashboard shows "System Status" card with correct counts.

## 4. Environment Verification

### 4.1 Database Connectivity
*   **Goal**: Ensure Backend can connect to required persistence layers.
*   **Date Verified**: 2026-02-11
*   **MySQL**: 
    *   **Config**: `jdbc:mysql://localhost:3306/underwriting_enhance`
    *   **User**: `root`
    *   **Status**: **PASSED** (Verified via `mvn spring-boot:run` successful bootstrap and SQL execution).
*   **MongoDB**:
    *   **Config**: `mongodb://localhost:27017/underwriting_enhance`
    *   **Status**: **PASSED** (Verified port connectivity and Repository initialization).
