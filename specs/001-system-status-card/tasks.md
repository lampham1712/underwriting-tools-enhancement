# Tasks: System Status Dashboard Card

**Spec**: [specs/001-system-status-card/spec.md](spec.md)
**Plan**: [specs/001-system-status-card/plan.md](plan.md)
**Status**: In Progress

## Phase 1: Setup
*Goal: Initialize project structure and database entities.*

- [x] T001 Create backend directory structure and Initialize Spring Boot project in backend/
- [x] T002 Create frontend directory structure and Initialize React project in frontend/
- [x] T003 [P] Configure MySQL and MongoDB connections in backend/src/main/resources/application.properties
- [x] T004 [P] Create SystemStatus entity in backend/src/main/java/com/project/model/SystemStatus.java
- [x] T005 [P] Create SystemStatusLog entity in backend/src/main/java/com/project/model/SystemStatusLog.java
- [x] T006 [P] Create SystemStatusRepository interface in backend/src/main/java/com/project/repository/SystemStatusRepository.java
- [x] T007 [P] Create SystemStatusLogRepository interface in backend/src/main/java/com/project/repository/SystemStatusLogRepository.java

## Phase 2: Foundational
*Goal: Implement core business logic and health check strategies. Blocking for all user stories.*

- [x] T008 Define HealthCheckStrategy interface in backend/src/main/java/com/project/service/health/HealthCheckStrategy.java
- [x] T009 [P] Implement RestHealthCheckStrategy in backend/src/main/java/com/project/service/health/RestHealthCheckStrategy.java
- [x] T010 [P] Implement JdbcHealthCheckStrategy in backend/src/main/java/com/project/service/health/JdbcHealthCheckStrategy.java
- [x] T011 [P] Implement Jt400HealthCheckStrategy in backend/src/main/java/com/project/service/health/Jt400HealthCheckStrategy.java
- [x] T012 Create SystemStatusService class with basic methods in backend/src/main/java/com/project/service/SystemStatusService.java
- [x] T013 Implement SystemStatusScheduler for 10-minute intervals in backend/src/main/java/com/project/scheduler/SystemStatusScheduler.java
- [x] T014 Create SystemStatusController skeleton in backend/src/main/java/com/project/controller/SystemStatusController.java

## Phase 3: Monitor System Health High-Level (Priority: P1)
*Goal: Display aggregate system status summary.*

- [x] T015 [P] [US1] Create StatusSummary DTO in backend/src/main/java/com/project/dto/StatusSummary.java
- [x] T016 [US1] Implement getSummary method in backend/src/main/java/com/project/service/SystemStatusService.java
- [x] T017 [US1] Implement GET /api/system-status/summary endpoint in backend/src/main/java/com/project/controller/SystemStatusController.java
- [x] T018 [P] [US1] Define TypeScript data types for API in frontend/src/types/SystemStatus.ts
- [x] T019 [P] [US1] Create SystemStatusService frontend client in frontend/src/services/SystemStatusService.ts
- [x] T020 [P] [US1] Create StatusHeader component in frontend/src/components/system-status/StatusHeader.tsx
- [x] T021 [US1] Integrate StatusHeader into main Dashboard in frontend/src/pages/Dashboard.tsx

## Phase 4: View Individual System Status (Priority: P1)
*Goal: Display mini-cards for each monitored system.*

- [x] T022 [US2] Implement getAllSystemStatuses method in backend/src/main/java/com/project/service/SystemStatusService.java
- [x] T023 [US2] Implement GET /api/system-status endpoint in backend/src/main/java/com/project/controller/SystemStatusController.java
- [x] T024 [US2] Update frontend service to fetch all systems in frontend/src/services/SystemStatusService.ts
- [x] T025 [P] [US2] Create MiniSystemCard component in frontend/src/components/system-status/MiniSystemCard.tsx
- [x] T026 [P] [US2] Create SystemStatusCard container component in frontend/src/components/system-status/SystemStatusCard.tsx
- [x] T027 [US2] Integrate SystemStatusCard list rendering in frontend/src/pages/Dashboard.tsx

## Phase 5: Refresh System Status (Priority: P2)
*Goal: Allow manual refresh of system status.*

- [x] T028 [US3] Implement refreshSystem method in backend/src/main/java/com/project/service/SystemStatusService.java
- [x] T029 [US3] Implement refreshAllSystems method (async) in backend/src/main/java/com/project/service/SystemStatusService.java
- [x] T030 [US3] Implement POST /refresh/{code} and /refresh-all endpoints in backend/src/main/java/com/project/controller/SystemStatusController.java
- [x] T031 [US3] Add refresh functions to frontend service in frontend/src/services/SystemStatusService.ts
- [x] T032 [US3] Add click handler and loading state to MiniSystemCard in frontend/src/components/system-status/MiniSystemCard.tsx
- [x] T033 [US3] Add Refresh All button to StatusHeader in frontend/src/components/system-status/StatusHeader.tsx

## Phase 6: Polish & Cross-Cutting Concerns
*Goal: UI styling, error handling, and verification.*

- [x] T034 Apply "Black Card" styling and responsiveness in frontend/src/components/system-status/SystemStatusCard.css
- [x] T035 Implement global error handling for API failures in frontend/src/services/api.ts
- [x] T036 Verify SystemStatusLog entries are created correctly in backend/src/main/java/com/project/service/SystemStatusService.java
- [x] T037 Conduct final end-to-end verification of all 13 systems

## Dependencies
1. **Foundational Phase** must be completed before **User Story 1**.
2. **User Story 1** (Summary) should be completed before **User Story 2** (Detailed List) for logical UI progression, though technically parallelizable.
3. **User Story 3** (Refresh) depends on **User Story 2** (UI components existence) and **Foundational** (Service logic).

## Parallel Execution Opportunities
- **All Phases**: Backend and Frontend tasks within the same story can generally be executed in parallel once the DTO/Contract is defined (which is done in Setup/Foundational).
- **Phase 2**: HealthCheck strategies (Rest, Jdbc, Jt400) can be implemented in parallel by different developers.

## Implementation Strategy
- **Stage 1 (MVP)**: specific focus on **User Story 1 & 2** to get the dashboard visible with read-only data (auto-refresh only).
- **Stage 2**: Add interactivity with **User Story 3** (Manual Refresh).
- **Stage 3**: Refine UI and Error Handling (Polish).
