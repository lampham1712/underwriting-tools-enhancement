# Insurance Policy Automation System Constitution

## Core Principles

### I. Resilience & Recovery
Failures in the automated processing phase must be recoverable without data loss. The system must support "Snapshots" for capturing state at failure points and provide "Retry Processes" to resume execution. Manual intervention (clearing snapshots) is a fallback, not the primary recovery mechanism.

### II. Observability & Monitoring
Real-time health monitoring of external dependencies (CCR, Payment Gateway, BOW) is non-negotiable. The system must provide 10-minute heartbeat checks and visual status indicators (Green/Red). Case progress must be tracked at the policy level with visible status for "Stuck" or "Zombie" cases.

### III. Traceability & Auditability
All system interventions must be traceable. This includes "extracting JSON" for debugging request/response cycles and "DBA Tools" for data remediation. Every manual execution of a data fix script or snapshot clearance must be an explicit, logged action.

### IV. Modular Integration
The system architecture must support the addition of new external integrations (LA, Cube, Casepedia) with minimal disruption. Monitoring and retry logic must be abstract enough to accommodate these new systems by configuration rather than code rewrite.

### V. Data Integrity
Data validation (DOB, Gender) is a core responsibility. The system must prevent invalid data from propagating to downstream systems. Duplicate checks ("Dedup") are a mandatory step in the policy creation flow.

## Technology Standards

### Frontend Ecosystem
*   **Core**: Node.js, React, TypeScript
*   **Styling**: HTML5, CSS
*   **Responsibility**: Operational dashboard, manual intervention interface, visualization.

### Backend Ecosystem
*   **Core**: Java 21, Spring Boot
*   **Responsibility**: Orchestration, external system integration, retry logic implementation, API exposure.

### Data Persistence
*   **Transactional**: MySQL (Structured policy data, business rules).
*   **Document**: MongoDB (Snapshots, Unstructured logs, JSON blobs).

## Feature Governance

### Policy Lifecycle
The standard lifecycle is immutable: `Create Policy -> Dedup Checking -> Auto Settlement -> Auto QC -> Issue Policy`. Any deviation must be handled via the Exception Management framework (Retry/Manual UW).

### Operational Tooling
*   **System Status**: Must accurately reflect "Online/Offline" states.
*   **Extract JSON**: Must include source URLs and calculated response times.
*   **DBA Tools**: Single-script execution restriction applies for safety.

## Versioning & Roadmap
*   **Current Focus**: Stability (Retry, Clear Snapshot), Observability (System Status).
*   **Proposed Evolution**: Advanced Analytics (ELK, Dashboarding), Intelligent Auto-Retry (Rules Engine), Enhanced Reporting.
*   **Ratified**: 2026-01-08

**Version**: 1.0.0