# Current Features

## The web-based tool provides 7 functions:
1. Retry Process - Retries cases in auto phase; manual cases require user intervention 
 * Create new policy ---> Dedup checking ---> Auto Settlement ---> Auto QC ---> Issue Policy

2. Clear Snapshot - Clears snapshots for cases stuck in auto phase 
 * When there are problems, system will create a snapshot on the status of that policy. It will update the current status of that snapshot to deleted and can re-trigger that API again.
 * The API using from back-end to trigger the function again (such as reDedupChecking, etc.)

3. Case Stuck at Auto Activity - Displays policy information including case_no, business_no, poactivity,licy_no, case_category,  response_time, exception_type, error_message, exception_message, and submission_channel
 * Need to check the current activity if missing any info and patching the correct info.

4. System Status - Monitors external system health (CCR, Payment Gateway, BOW, etc.) with 10-minute ping intervals. Shows connection status and timestamps. Includes "retry all" button and individual system refresh icons
 * CCR: will ping (cmd) with the ip address of CCR system and wait for response status.
    * If the ping fail, need to check with CCR support staff to verify the staus.
    * If the ping success, the green icon of that system will be maintained.
 * Payment Gateway: will ping (cmd) with the ip address of Payment Gateway system and wait for response status.
    * If the ping fail, need to check with Payment Gateway support staff to verify the staus.
    * If the ping success, the green icon of that system will be maintained.

5. Extract JSON File - Exports request/response data using case_no, policy_no, application_no, integration_code, and category as input
 * To verify the case details & it status.
 * Save at local disk.

6. DBA Tools - Executes .sql scripts on production database (single script execution only)
 * Generate the script require to fix the data.
 * Send the script to the DevOps team to run.

7. Case Progress - Tracks policy status using policy_no, case_no, or business_no
 * Status of each policy.


## Proposed Improvements

1. System Status:
 * Consolidate duplicate system entries 
    * The error might come from .css file that show multiple card info of only one system.

 * Add missing systems (LA, Cube, Casepedia)
    * Now show only CCR, Payment Gateway and BOWS system.

* Display system counter (e.g., "Total: 7, Online: 6, Offline: 1")
    * Show high level summary of current system need support.

2. Extract JSON File:
 * Add URL column in export
    * Example: deudupChecking url https://ccrchecking.com/getInfo/
    * Put one more column into the JSON file export to show the url info that process.

* Calculate and display total response time (response_time - request_time) 
    * Should be display in second.

3. Analytics Dashboard:
 * Vertical Bar Chart: Daily/weekly/monthly policy volume (including cancellations)
 * Pie Chart: Policy distribution by process type (Dedup, NB Settlement, Auto Underwriting, Manual UW)
 * Line Chart: Auto activity success/failure trends
 * Manual UW Table: Track cases in manual underwriting (data requirements TBD to optimize performance)

4. Retry Process:
 * Implement auto-retry rules for specific exceptions (e.g., deadlock, batch job conflicts)
    * Base on the working experience from error message that can be auto retry 
    * Allow configurable rule sets.
    * For each rule need rule_name, exception_message, error_message, url need to be trigger.
    * Can be enable/disable it. 

5. Report Center:
 * Add reporting functionality (pending requirements definition)
    * 

6. Additional Considerations:
 * Data validation (DoB, gender) - requires main system implementation
 * Enhanced error messages with detailed context and system source
 * ELK (Elasticsearch) integration for advanced search

## Current tech stack
1. Front-end: Node JS, React, Typescript, HTML, CSS.
2. Back-end: Java(21), Spring Boot.
3. Database: MySQL, MongoDB.