# Feature Specification: System Status Dashboard Card

**Feature Branch**: `001-system-status-card`
**Created**: 2026-01-08
**Status**: Draft
**Input**: User description: "Build an black card, in that card, having mini card to show the status of each system that the main product have to interact"

## User Scenarios & Testing

### User Story 1 - Monitor System Health High-Level (Priority: P1)

As an Operations Manager, I want to see a high-level summary of all external system connections, so that I can instantly know if the platform is fully operational without counting individual icons.

**Why this priority**: Immediate situational awareness is critical for maintaining uptime. A summary view reduces cognitive load.

**Independent Test**: Mock 4 systems online and 2 offline, then verify the dashboard displays "Total: 6, Online: 4, Offline: 2".

**Acceptance Scenarios**:

1. **Given** the dashboard is loaded, **When** I look at the System Status header, **Then** I see the total count of monitored systems and the breakdown of Online vs. Offline.
2. **Given** all systems are healthy, **When** I view the dashboard, **Then** the "Offline" counter shows 0.

---

### User Story 2 - View Individual System Status (Priority: P1)

As a Support Engineer, I want to see a dedicated "mini card" for each external system (CCR, Payment Gateway, BOW, LA, Cube, Casepedia, Nano, JT400, INT, OWB/OPUS, UWMe, FCRM, DMS), so that I can pinpoint exactly which dependency is failing.

**Why this priority**: Essential requirement for troubleshooting specific integration failures. Matches the "Black card with mini cards" visual requirement.

**Independent Test**: Configure the "CCR" system to fail its heartbeat check and verify its specific mini-card turns Red/Offline while others remain Green.

**Acceptance Scenarios**:

1. **Given** I am on the dashboard, **When** I view the System Status section, **Then** I see the "Black Card" container housing individual mini-cards for all configured systems.
2. **Given** a system (e.g., Payment Gateway) is unresponsive, **When** the dashboard refreshes, **Then** its mini-card displays a Red status indicator and the last checked timestamp.
3. **Given** duplicate configurations exist in the backend, **When** the cards are rendered, **Then** the UI consolidates them into a single entry per system (Requirements from Current Features).

---

### User Story 3 - Refresh System Status (Priority: P2)

As a Support Engineer, I want to manually trigger a refresh for the system status, so that I can verify if a fix has worked immediately.

**Why this priority**: Passive monitoring (10-min interval) is insufficient during active incident resolution.

**Independent Test**: Click the "Refresh" icon on a mini-card and verify a network request is sent to check that specific system.

**Acceptance Scenarios**:

1. **Given** a system shows as Offline, **When** I click its refresh icon, **Then** the status updates based on the immediate result of the ping command.

### Edge Cases

- **Ping Timeout**: If a system does not respond within a defined threshold (e.g., 5 seconds), it MUST default to "Offline" rather than hanging the UI.
- **Backend Unavailability**: If the backend service providing status checks is down, the entire card should display a "Monitoring Service Unavailable" state.
- **Empty List**: If no systems are configured, the card should display a "No systems monitored" message rather than an empty black box.
- **Partial Failure**: If a system responds to Ping but returns a 500 Error, it should be treated as "Offline" (or "Unhealthy").

## Requirements

### Functional Requirements

- **FR-001**: The System Status component MUST be styled as a "Black Card" (dark-themed container) to distinguish it as a monitoring console.
- **FR-002**: The container MUST display a summary header with counters: "Total Systems", "Online", and "Offline".
- **FR-003**: The component MUST verify and display the status of the following systems: CCR, Payment Gateway, BOW, LA, Cube, Casepedia, Nano, JT400, INT, Main System (OWB/OPUS), UWMe, FCRM, and DMS.
- **FR-004**: Each system MUST be represented by a "Mini Card" displaying: System Name, Status Icon (Green=Online, Red=Offline), Last Checked Timestamp, and a Refresh button, time next Ping, time last Ping.
- **FR-005**: The system status MUST be refreshed automatically every 10 minutes.
- **FR-006**: The system MUST consolidate duplicate data records regarding the same system into a single UI card.
- **FR-007**: The component MUST support a "Retry All" function to trigger heartbeat checks for all systems simultaneously.
- **FR-008**: The system MUST log each status check result with a timestamp for audit purposes.
- **FR-009**: The system MUST handle and display error states gracefully for "Offline card" (e.g., "Monitoring Service Unavailable").

### Key Entities

- **SystemStatus**: Represents the health state of an external integration.
    - `id`: Unique identifier. (UUID v7 Not Null) PRIMARY KEY
    - `system_code`: Unique identifier (e.g., "CCR", "PAY_GATE"). (string max 20 chars Not Null).
    - `display_name`: Human-readable name. (string max 100 chars Not Null).
    - `status`: Enum (ONLINE, OFFLINE, UNKNOWN). (string Not Null)
    - `last_checked`: Timestamp of the last ping. (ISO 8601 datetime Not Null)
    - `next_check`: Timestamp of the next scheduled ping. (ISO 8601 datetime Not Null)
    - `response_time_ms`: Duration of the ping response. (integer Not Null)
    - `created_at`: Timestamp when the record was created. (ISO 8601 datetime Not Null)
    - `updated_at`: Timestamp when the record was last updated. (ISO 8601 datetime Not Null)

## Success Criteria

### Measurable Outcomes

- **SC-001**: Operations team can identify an offline system within 1 second of looking at the dashboard (Scanning visual red cards).
- **SC-002**: The UI displays correct status for all core systems (CCR, Payment Gateway, BOW, LA, Cube, Casepedia, Nano, JT400, INT, OWB/OPUS, UWMe, FCRM, DMS).
- **SC-003**: No duplicate cards appear for a single system integration.
- **SC-004**: Manual refresh of a system's status completes within 3 seconds and updates the UI accordingly.
- **SC-005**: The system successfully handles edge cases (e.g., timeouts, backend unavailability) without crashing the UI.
- **SC-006**: 100% of status checks are logged with timestamps for audit purposes.
- **SC-007**: The automatic refresh occurs every 10 minutes without user intervention.