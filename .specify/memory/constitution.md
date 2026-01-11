<!--
SYNC IMPACT REPORT
Version Change: 1.1.0 -> 1.2.0
Type: MINOR (Added comprehensive technology best practices)
Modified Principles:
- Technology Standards: Expanded with specific best practices for JS, TS, React, and HTML/CSS.
Added Sections:
- JavaScript Best Practices
- TypeScript Best Practices
- React.JS Best Practices
- HTML & CSS Best Practices
Templates requiring updates:
- .specify/templates/plan-template.md: ⚠ pending (should include standards compliance check)
- .specify/templates/tasks-template.md: ⚠ pending (task definitions should reflect code quality steps)
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

#### JavaScript Best Practices
*   **Scope & Variables**: Avoid Global Scope. Use `const` by default, `let` only when necessary. Never use `var`.
*   **Asynchronous Code**: Master Promises and `async/await`. Avoid "callback hell" by chaining promises vs nesting.
*   **Safety**: Always use `"use strict";` mechanics. Use `===` (strict equality) to avoid type coercion errors.
*   **Purity**: Write pure functions (no side effects) where possible to simplify testing.

#### TypeScript Best Practices
*   **Types**: Avoid `any`; use `unknown` or specific interfaces.
*   **Configuration**: Enable `"strict": true` (including `noImplicitAny` and `strictNullChecks`).
*   **Structure**: Rely on Structural Typing ("duck typing") over nominal typing.
*   **Inference**: Let TypeScript infer types where obvious (e.g., `const x = 5`) rather than cluttering code.
*   **Immutability**: Use `readonly` for properties that shouldn't change after initialization.

#### React.JS Best Practices
*   **Composition**: Build small, focused components; avoid monolithic "God components".
*   **State**: Never modify `state` or `props` directly (Immutability). Use spread operators or immutable patterns.
*   **Data Flow**: Unidirectional (Parent -> Child). Use callbacks for Child -> Parent communication.
*   **Hooks**: Only call Hooks at the top level of component functions (not in loops/conditions).
*   **Keys**: Always provide a unique `key` prop when rendering lists.

#### HTML & CSS Best Practices
*   **Semantics**: Use correct semantic tags (`<button>`, `<nav>`, `<article>`) rather than generic `<div>`s.
*   **Separation of Concerns**: Keep structure (HTML), presentation (CSS), and behavior (JS) separate.
*   **Responsive**: Mobile-First design pattern (base styles for mobile, `min-width` for larger screens).
*   **Units**: Use relative units (`rem`, `em`) over `px` for accessibility and scaling.
*   **Specificity**: Keep CSS selectors simple/flat to prevent "specificity wars" and `!important` usage.

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

**Version**: 1.2.0