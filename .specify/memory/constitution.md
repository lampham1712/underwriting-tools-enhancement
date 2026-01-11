<!--
SYNC IMPACT REPORT
Version Change: 1.0.0 -> 1.1.0
Type: MINOR (New engineering principles and expanded resilience guidance)
Modified Principles:
- I. Resilience & Recovery: Added mandatory network timeouts constraint.
- II. Observability & Monitoring: Added standard health exposure requirement.
- Backend Ecosystem: Added configuration and DI standards.
Added Sections:
- VI. Sustainable Engineering: New core principle covering Dependency Injection, External Configuration, and Clean Code standards.
Templates requiring updates:
- .specify/templates/plan-template.md: ⚠ pending
- .specify/templates/tasks-template.md: ⚠ pending
-->

# Insurance Policy Automation System Constitution

## Core Principles

### I. Resilience & Recovery
Failures in the automated processing phase must be recoverable without data loss. The system must support "Snapshots" for capturing state at failure points and provide "Retry Processes" to resume execution. Manual intervention (clearing snapshots) is a fallback, not the primary recovery mechanism. Network interactions must enforce explicit timeouts (connection and read) to prevent cascading failures.

### II. Observability & Monitoring
Real-time health monitoring of external dependencies (CCR, Payment Gateway, BOW) is non-negotiable. The system must provide 10-minute heartbeat checks and visual status indicators (Green/Red). Case progress must be tracked at the policy level with visible status for "Stuck" or "Zombie" cases. Internal application health must be exposed via standard endpoints (e.g., Spring Actuator) to enable orchestration monitoring.

### III. Traceability & Auditability
All system interventions must be traceable. This includes "extracting JSON" for debugging request/response cycles and "DBA Tools" for data remediation. Every manual execution of a data fix script or snapshot clearance must be an explicit, logged action.

### IV. Modular Integration
The system architecture must support the addition of new external integrations (LA, Cube, Casepedia) with minimal disruption. Monitoring and retry logic must be abstract enough to accommodate these new systems by configuration rather than code rewrite.

### V. Data Integrity
Data validation (DOB, Gender) is a core responsibility. The system must prevent invalid data from propagating to downstream systems. Duplicate checks ("Dedup") are a mandatory step in the policy creation flow.

### VI. Sustainable Engineering
Code must prioritize long-term maintainability through loose coupling.
*   **Dependency Injection**: Constructor-based injection is mandatory for ensuring fully initialized options and testability.
*   **Immutability**: Dependency fields should be `final` to ensure thread safety.
*   **Configuration**: All infrastructure targets (URLs, endpoints) and environment-specific constraints must be externalized to properties files. Hardcoding these values ("Magic Strings") is prohibited.
*   **Interface-Driven**: Dependencies must be referenced by interface, not implementation, to facilitate future component swapping.

## Technology Standards

### Frontend Ecosystem
*   **Core**: Node.js, React, TypeScript
*   **Styling**: HTML5, CSS
*   **Responsibility**: Operational dashboard, manual intervention interface, visualization.

### Backend Ecosystem
*   **Core**: Java 21, Spring Boot
*   **Configuration**: Java Config (`@Configuration`), External Properties (`application.properties`)
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
*   **Current Focus**: Stability (Retry, Clear Snapshot), Observability (System Status), Code Quality Refactoring.
*   **Proposed Evolution**: Advanced Analytics (ELK, Dashboarding), Intelligent Auto-Retry (Rules Engine), Enhanced Reporting.
*   **Ratified**: 2026-01-08
*   **Last Amended**: 2026-01-11

**Version**: 1.1.0