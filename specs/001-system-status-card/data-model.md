# Data Model: System Status Dashboard

## Entities

### `SystemStatus`
Represents the last known health state of an external integration.

| Field Name | Type | Constraints | Description |
|---|---|---|---|
| `id` | UUID | Primary Key | Unique identifier. |
| `system_code` | VARCHAR(20) | Unique, Not Null | Code Identifier (e.g., "CCR", "JT400"). |
| `display_name` | VARCHAR(100) | Not Null | Human-readable name (e.g., "Credit Check Repository"). |
| `status` | ENUM | Not Null | `ONLINE`, `OFFLINE`, `UNKNOWN`, `MAINTENANCE`. |
| `last_checked` | TIMESTAMP | Nullable | Time of the last successful/failed check. |
| `next_check` | TIMESTAMP | Nullable | Scheduled time for the next check. |
| `response_time_ms` | INTEGER | Nullable | Latency of the ping in milliseconds. |
| `error_message` | TEXT | Nullable | Detail if status is OFFLINE. |
| `is_critical` | BOOLEAN | Default FALSE | Helps determine aggregate status color (Red/Yellow). |

### `SystemStatusLog`
Audit trail history of status checks.

| Field Name | Type | Constraints | Description |
|---|---|---|---|
| `id` | UUID | Primary Key | Unique identifier. |
| `system_code` | VARCHAR(20) | Foreign Key | Reference to SystemStatus. |
| `check_timestamp` | TIMESTAMP | Not Null | When the check occurred. |
| `status_result` | VARCHAR(20) | Not Null | Result of the check. |
| `duration_ms` | INTEGER | Not Null | Duration of check. |

## Relationships
- One `SystemStatus` has Many `SystemStatusLog` entries (One-to-Many).
