# System Status Dashboard

This repository contains the source code for the System Status Dashboard, a full-stack application for monitoring system health. It consists of a Spring Boot backend and a React/TypeScript frontend.

## Project Structure

- `backend/`: Java Spring Boot application
- `frontend/`: React TypeScript application
- `docs/`: Project documentation
- `specs/`: Project specifications

## Prerequisites

Ensure you have the following installed on your local machine:

- **Java Development Kit (JDK) 21**
- **Maven**
- **Node.js** (LTS version recommended)
- **npm** (usually included with Node.js)

---

## Backend Setup

The backend is built with Spring Boot and uses an H2 in-memory database by default for development.

### 1. Navigate to the backend directory

```bash
cd backend
```

### 2. Build the application

```bash
mvn clean install
```

### 3. Run the application

You can run the application using the Spring Boot Maven plugin:

```bash
mvn spring-boot:run
```

Alternatively, you can run the built JAR file:

```bash
java -jar target/system-status-0.0.1-SNAPSHOT.jar
```

The server will start on `http://localhost:8080`.
The H2 Console is available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, User: `sa`, Password: empty).

---

## Frontend Setup

The frontend is a React application built with TypeScript.

### 1. Navigate to the frontend directory

```bash
cd frontend
```

### 2. Install dependencies

```bash
npm install
```

### 3. Start the development server

```bash
npm start
```

The application will start and open in your browser at `http://localhost:3000`.

---

## Running the Full Stack

1. Start the **Backend** in one terminal window.
2. Start the **Frontend** in a second terminal window.

Ensure the backend is running before using the frontend to avoid connection errors.