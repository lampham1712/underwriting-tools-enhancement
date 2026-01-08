# Data Model: System Status Dashboard

## Entities

### `SystemStatus` 
Represents the last known health state of an external integration. (Not editable directly; updated via heartbeat checks)

| Field Name | Type | Constraints | Description |
|---|---|---|---|
| `id` | UUID v4 | Primary Key | Unique identifier. |
| `system_code` | VARCHAR(20) | Unique, Not Null | Code Identifier (e.g., "CCR", "JT400"). |
| `display_name` | VARCHAR(100) | Not Null | Human-readable name (e.g., "Credit Check Repository"). |
| `status` | ENUM | Not Null | `ONLINE`, `OFFLINE`, `UNKNOWN`, `MAINTENANCE`. |
| `last_checked` | TIMESTAMP | Not Null | Time of the last successful/failed check. |
| `next_check` | TIMESTAMP | Not Null | Scheduled time for the next check. |
| `error_message` | TEXT | Not Null | Detail if status is OFFLINE. |
| `is_critical` | BOOLEAN | Default FALSE | Helps determine aggregate status color (Red/Yellow). |

### `SystemStatusLog`
Audit trail history of status checks. (Not editable, append-only)

| Field Name | Type | Constraints | Description |
|---|---|---|---|
| `id` | UUID v4 | Primary Key | Unique identifier. |
| `system_code` | VARCHAR(20) | Reference to SystemStatus. |
| `check_timestamp` | TIMESTAMP | Not Null | When the check occurred. | Reference to last_checked in SystemStatus |
| `status_result` | VARCHAR(20) | Not Null | Result of the check. |
| `error_details` | TEXT | Nullable | Error details if the check failed. |
| `response_time_ms` | INTEGER | Not Null | Latency of the ping in milliseconds. |
| `created_at` | TIMESTAMP | Not Null | When the log entry was created. |

## Relationships
- One `SystemStatus` has Many `SystemStatusLog` entries (One-to-Many).
- `system_code` in `SystemStatusLog` references `system_code` in `SystemStatus`.
- Cascade delete `SystemStatusLog` entries when a `SystemStatus` is removed.