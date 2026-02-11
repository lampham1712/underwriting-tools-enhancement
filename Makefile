# Makefile for System Status Dashboard

.PHONY: setup install-backend install-frontend run-backend run-frontend build build-backend build-frontend help

# Default target
help:
	@echo "System Status Dashboard Makefile"
	@echo "--------------------------------"
	@echo "Usage: make [target]"
	@echo ""
	@echo "Targets:"
	@echo "  setup             Install all dependencies for backend and frontend"
	@echo "  install-backend   Install backend dependencies (Maven)"
	@echo "  install-frontend  Install frontend dependencies (npm)"
	@echo "  run-backend       Run the backend server (Spring Boot)"
	@echo "  run-frontend      Run the frontend development server"
	@echo "  build             Build both backend and frontend"
	@echo "  build-backend     Build backend jar"
	@echo "  build-frontend    Build frontend static assets"
	@echo "  clean             Clean build artifacts"

setup: install-backend install-frontend

install-backend:
	cd backend && mvn clean install -DskipTests

install-frontend:
	cd frontend && npm install

run-backend:
	cd backend && mvn spring-boot:run

run-frontend:
	cd frontend && npm start

build: build-backend build-frontend

build-backend:
	cd backend && mvn clean package -DskipTests

build-frontend:
	cd frontend && npm run build

clean:
	cd backend && mvn clean
	cd frontend && rm -rf build node_modules