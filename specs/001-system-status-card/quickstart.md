# Quickstart: System Status Dashboard

## Prerequisites

- **Java**: JDK 21+
- **Node.js**: v20+ (LTS)
- **Database**:
  - MySQL service running on port 3306
  - MongoDB service running on port 27017

## Environment Configuration

Create a `.env` file in the backend root:

```properties
# Database
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/insurance_db
SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/insurance_logs

# Legacy System Config (Mock or Real)
JT400_HOST=192.168.1.100
JT400_USER=testuser
JT400_PASS=testpass
```

## Running the Application

### Backend (Spring Boot)

1. Navigate to `backend/`
2. Run the application:
   ```powershell
   ./mvnw spring-boot:run
   ```
3. Verify API is up: `http://localhost:8080/api/system-status/summary`

### Frontend (React)

1. Navigate to `frontend/`
2. Install dependencies:
   ```powershell
   npm install
   ```
3. Start the dev server:
   ```powershell
   npm start
   ```
4. Access the Dashboard: `http://localhost:3000`

## Verification

1. On the main dashboard, look for the "System Status" Black Card.
2. It should show 13 items.
3. Stop the "CCR" mock service (or disconnect VPN).
4. Click the refresh icon on the CCR card.
5. Verify it turns RED.
