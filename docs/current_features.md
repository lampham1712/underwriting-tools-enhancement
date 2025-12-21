Current Features
The web-based tool provides 7 functions:

Retry Process - Retries cases in auto phase; manual cases require user intervention
Clear Snapshot - Clears snapshots for cases stuck in auto phase
Case Stuck at Auto Activity - Displays policy information including case_no, business_no, policy_no, case_category, activity, response_time, exception_type, error_message, exception_message, and submission_channel
System Status - Monitors external system health (CCR, Payment Gateway, BOW, etc.) with 10-minute ping intervals. Shows connection status and timestamps. Includes "retry all" button and individual system refresh icons
Extract JSON File - Exports request/response data using case_no, policy_no, application_no, integration_code, and category as input
DBA Tools - Executes .sql scripts on production database (single script execution only)
Case Progress - Tracks policy status using policy_no, case_no, or business_no
Proposed Improvements
1. System Status

Consolidate duplicate system entries
Add missing systems (LA, Cube, Casepedia)
Display system counter (e.g., "Total: 7, Online: 6, Offline: 1")
2. Extract JSON File

Add URL column in export
Calculate and display total response time (response_time - request_time)
3. Analytics Dashboard

Vertical Bar Chart: Daily/weekly/monthly policy volume (including cancellations)
Pie Chart: Policy distribution by process type (Dedup, NB Settlement, Auto Underwriting, Manual UW)
Line Chart: Auto activity success/failure trends
Manual UW Table: Track cases in manual underwriting (data requirements TBD to optimize performance)
4. Retry Process

Implement auto-retry rules for specific exceptions (e.g., deadlock, batch job conflicts)
Allow configurable rule sets
5. Report Center

Add reporting functionality (pending requirements definition)
6. Additional Considerations

Data validation (DoB, gender) - requires main system implementation
Enhanced error messages with detailed context and system source
ELK (Elasticsearch) integration for advanced search