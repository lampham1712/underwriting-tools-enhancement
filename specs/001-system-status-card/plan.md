# Implementation Plan: System Status Dashboard Card

**Branch**: `001-system-status-card` | **Date**: 2026-01-08 | **Spec**: [specs/001-system-status-card/spec.md](spec.md)
**Input**: Feature specification from `/specs/001-system-status-card/spec.md`

## Summary
Implement a high-level "System Status" monitoring dashboard featuring a dark-themed "Black Card" with individual "Mini Cards" for 13 specific systems (CCR, Payment Gateway, BOW, LA, Cube, Casepedia, Nano, JT400, INT, OWB/OPUS, UWMe, FCRM, DMS). The system will provide 10-minute heartbeat checks, manual refresh, and consolidated status reporting (Online/Offline).

## Technical Context

**Language/Version**: 
- Frontend: TypeScript, Node.js (React)
- Backend: Java 21 (Spring Boot)
**Primary Dependencies**: 
- Frontend: React, CSS/Styled Components
- Backend: Spring Boot Starter Web, Spring Boot Starter Data JPA
**Storage**: MySQL (System registry), MongoDB (Logs/History)
**Testing**: 
- Frontend: Jest/React Testing Library
- Backend: JUnit 5, Mockito
**Target Platform**: Web Browser
**Project Type**: Full Stack (Web + Backend)
**Performance Goals**: Dashboard render < 1s, Individual Ping < 3s, Bulk Ping (Retry All) handling async.
**Constraints**: 
- "Black Card" UI styling.
- 10-minute automatic interval.
**Scale/Scope**: 13 external systems to monitor.

## Constitution Check

*GATE: Passed*

- **Resilience**: Failures in pinging one system (e.g., timeout) MUST NOT crash the dashboard (Spec Edge Case).
- **Observability**: This feature directly implements Principle II (10-minute heartbeat, Green/Red status).
- **Technology Standards**: Compliant with Section 3 (React, Spring Boot).

## Project Structure

### Documentation (this feature)

```text
specs/001-system-status-card/
 plan.md              # This file
 research.md          # Phase 0 output
 data-model.md        # Phase 1 output
 quickstart.md        # Phase 1 output
 contracts/           # Phase 1 output
 tasks.md             # Phase 2 output
```

### Source Code Structure (Assumed based on Constitution)

```text
backend/
 src/main/java/com/project/
    controller/      # SystemStatusController
    service/         # SystemStatusService, PingService strategies
    repository/      # SystemStatusRepository
    model/           # SystemStatus Entity
 tests/

frontend/
 src/
    components/
       system-status/
          SystemStatusCard.tsx    # Main Black Card
          MiniSystemCard.tsx      # Individual status card
          StatusHeader.tsx        # Summary counters
       common/
    services/
        SystemStatusService.ts      # API Integration
 tests/
```

**Structure Decision**: Standard Spring Boot Layered Architecture + React Component/Service separation.

## Complexity Tracking

N/A - Standard CRUD + Scheduler implementation.