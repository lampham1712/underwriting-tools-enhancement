# Research: System Status Implementation Details

## 1. Connectivity Strategy

To monitor the 13 discrete integrations effectively in a Spring Boot (Java 21) backend, a **Strategy Pattern** is the recommended approach. This allows the `PingService` to be agnostic of the underlying protocol (HTTP, TCP, JDBC) while maintaining a uniform interface for the rest of the application.

### Strategy Pattern Implementation
Define a common interface:
```java
public interface HealthCheckStrategy {
    HealthStatus checkHealth();
    String getSystemIdentifier(); // e.g., "CCR", "JT400"
}
```

Implement concrete strategies for each integration type:
1.  **`RestHealthCheckStrategy`**: Uses `RestClient` or `WebClient` to hit a lightweight endpoint (e.g., `/actuator/health` or a dedicated ping endpoint).
2.  **`JdbcHealthCheckStrategy`**: Uses `java.sql.Connection.isValid(timeout)` or runs a simple validation query (e.g., `SELECT 1 FROM SYSIBM.SYSDUMMY1` for DB2, `SELECT 1` for Postgres/MySQL).
3.  **`Jt400HealthCheckStrategy`**: Specific implementation for the AS/400 system.

**Concurrency Note**: Since checking 13 systems sequentially might be slow (especially if timeouts occur), use Java 21's **Virtual Threads** (Project Loom) or a `CompletableFuture` mechanism to run these checks in parallel.

---

## 2. JT400/Legacy Handling (IBM i / AS/400)

For the **JT400** integration, utilizing the standard JTOpen (jt400.jar) library is the most reliable method.

### "IsAlive" Implementation Options
1.  **Port Probe (Lightest)**:
    *   *Technique*: Open a standard Java `Socket` to the IBM i Host Server Mapper port (standard is **449**) or the Telnet port (23).
    *   *Pros*: Extremely fast, essentially a TCP Ping. No authentication overhead.
    *   *Cons*: Only proves the machine's TCP stack is up, not that the database or specific subsystems are ready.

2.  **Service Connection (Recommended)**:
    *   *Technique*: Use the `AS400` class to connect to the "Central" service.
    *   *Code Snippet*:
        ```java
        AS400 system = new AS400("host", "user", "pass");
        try {
            // CENTRAL service is used for license management and basic OS communication
            system.connectService(AS400.CENTRAL); 
            // OR checks generic connectivity without full service overhead
            if (!system.isConnectionAlive(AS400.CENTRAL)) throw new Exception("Down");
        } finally {
            system.disconnectAllServices();
        }
        ```
    *   *Pros*: Verifies the IBM i host servers are actually responding.

**Recommendation**: Use Option 2 (Service Connection) as it provides a true valid status. The overhead for a 10-minute interval is negligible.

---

## 3. Frontend Polling

Given the requirements (10-minute refresh rate) and the nature of the data (system status), **short polling** is superior to WebSockets.

### Recommended Approach: `TanStack Query` (React Query)
If the project allows external libraries, `TanStack Query` is the industry standard for specific polling intervals.

```javascript
const { data, isError } = useQuery({
  queryKey: ['systemStatus'],
  queryFn: fetchSystemStatus,
  refetchInterval: 10 * 60 * 1000, // 10 minutes
});
```

### Alternative: Custom `useInterval` Hook
If minimizing dependencies is preferred, a custom hook using `useEffect` and `setInterval` is sufficient.

**Why Polling?**
*   **Firewall Friendly**: No persistent connections (WebSockets) that might be dropped by aggressive easy proxies or firewalls.
*   **Simple Recovery**: If a request fails, the next poll simply retries.
*   **Resource Efficiency**: A 10-minute interval puts near-zero load on the client and server compared to maintaining an open socket.

---

## 4. Visual Hierarchy: "Black Card" Design

The "Black Card" suggests a high-contrast, dark-themed UI component (white text on dark background).

### Structure
1.  **Container**: Dark background (`#1e1e1e` or similar), rounded corners, slight drop shadow.
2.  **Header**: 
    *   Title: "System Status"
    *   **Aggregate Badge**: A single dot or badge summarising the whole list.
        *   🟢 (Green): All 13 systems UP.
        *   🟡 (Yellow): 1-3 non-critical systems DOWN (Partial Failure).
        *   🔴 (Red): Critical system (e.g., Payment Gateway, CCR) DOWN or >3 systems DOWN.
3.  **Grid Layout**: A flex or grid container acting as the body.
4.  **Mini Cards (Pills)**:
    *   Each of the 13 systems gets a "pill" or small rectangular area.
    *   **Visual State**:
        *   *Up*: Very faint green background / Green status dot.
        *   *Down*: Bright red border or background / Red status dot / Warning Icon.
    *   *Content*: System Acronym (e.g., "JT400") + Status Icon.

### Handling Partial Failures
*   **Sorting**: Move "Down" systems to the top-left of the grid immediately to catch attention.
*   **Opacity**: Reduce opacity of "Healthy" (Green) cards slightly (e.g., 0.8) so the "Unhealthy" (Red) cards pop visually.
