# Credit Application Service

> A production-ready Spring Boot microservice for managing credit applications with hexagonal architecture, comprehensive observability, and robust testing.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-ready-blue.svg)](https://www.docker.com/)

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Running Tests](#running-tests)
- [API Documentation](#api-documentation)
- [Observability](#observability)
- [Docker Deployment](#docker-deployment)
- [Environment Variables](#environment-variables)
- [Project Structure](#project-structure)
- [Business Rules](#business-rules)
- [Troubleshooting](#troubleshooting)

## 🎯 Overview

The Credit Application Service is a microservice designed to manage credit applications for a cooperative credit institution. It implements hexagonal architecture (ports and adapters pattern) to maintain clean separation between business logic and infrastructure concerns.

### Key Capabilities

- **Affiliate Management**: Register and manage cooperative affiliates
- **Credit Application Processing**: Submit and evaluate credit applications
- **Risk Assessment Integration**: External risk service integration with retry/fallback
- **Role-Based Access Control**: Three-tier authorization (AFILIADO, ANALYST, ADMIN)
- **Comprehensive Observability**: Prometheus metrics, health checks, and Grafana dashboards

## 🏗️ Architecture

### Hexagonal Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                      Adapters (In)                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │
│  │ REST         │  │ Auth         │  │ Exception    │     │
│  │ Controllers  │  │ Controller   │  │ Handler      │     │
│  └──────┬───────┘  └──────┬───────┘  └──────────────┘     │
│         │                  │                                │
│         ▼                  ▼                                │
│  ┌─────────────────────────────────────────────────┐       │
│  │           Application Use Cases                 │       │
│  │  • CreateAfilliateUseCase                       │       │
│  │  • CreateCreditApplicationUseCase               │       │
│  │  • EvaluateCreditApplicationUseCase             │       │
│  └─────────────────────────────────────────────────┘       │
│                        │                                    │
│                        ▼                                    │
│  ┌─────────────────────────────────────────────────┐       │
│  │              Domain Models                      │       │
│  │  • Afilliate (business rules)                   │       │
│  │  • CreditApplication (validations)              │       │
│  │  • RiskEvaluation                               │       │
│  └─────────────────────────────────────────────────┘       │
│                        │                                    │
│                        ▼                                    │
│  ┌─────────────────────────────────────────────────┐       │
│  │            Ports (Interfaces)                   │       │
│  │  • AfilliateRepositoryPort                      │       │
│  │  • CreditApplicationRepositoryPort              │       │
│  │  • RiskEvaluationPort                           │       │
│  └─────────────────────────────────────────────────┘       │
│                        │                                    │
└────────────────────────┼────────────────────────────────────┘
                         │
┌────────────────────────┼────────────────────────────────────┐
│                        ▼                                    │
│                 Adapters (Out)                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │
│  │ JPA          │  │ Risk Service │  │ External     │     │
│  │ Adapters     │  │ Adapter      │  │ Services     │     │
│  └──────────────┘  └──────────────┘  └──────────────┘     │
└─────────────────────────────────────────────────────────────┘
```

## ✨ Features

### Core Functionality
- ✅ Affiliate registration with document uniqueness validation
- ✅ Credit application submission with business rule enforcement
- ✅ Risk evaluation via external service integration
- ✅ Automatic approval/rejection based on risk assessment

### Security
- ✅ JWT-based stateless authentication
- ✅ BCrypt password hashing
- ✅ Role-based access control (RBAC)
- ✅ Protected endpoints with method-level security

### Observability
- ✅ Spring Boot Actuator endpoints
- ✅ Prometheus metrics export
- ✅ Grafana dashboards
- ✅ Structured logging with SLF4J
- ✅ Health checks and readiness probes

### Resilience
- ✅ Retry logic with exponential backoff (Resilience4j)
- ✅ Circuit breaker pattern
- ✅ Graceful degradation with fallback responses
- ✅ Timeout handling

### Testing
- ✅ Comprehensive unit tests (JUnit 5 + Mockito)
- ✅ Parameterized tests for edge cases
- ✅ Integration tests with Testcontainers
- ✅ Test coverage reporting (JaCoCo)

## 📦 Prerequisites

- **Java 17** or higher
- **Maven 3.9+**
- **Docker** and **Docker Compose** (for containerized deployment)
- **PostgreSQL 15** (if running locally without Docker)

## 🚀 Quick Start

### Option 1: Docker Compose (Recommended)

```bash
# Clone the repository
git clone <repository-url>
cd CoopCredit

# Start all services (app, database, monitoring)
docker-compose up -d

# Wait for services to be ready (~60 seconds)
docker-compose logs -f credit-application-service

# Verify deployment
./verify_end_to_end.sh
```

### Option 2: Local Development

```bash
# Start PostgreSQL
docker run -d \
  --name postgres \
  -e POSTGRES_DB=coopcredit \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:15-alpine

# Set environment variables
export DB_URL=jdbc:postgresql://localhost:5432/coopcredit
export DB_USERNAME=postgres
export DB_PASSWORD=postgres
export JWT_SECRET=mySecretKeyForJWTTokenGenerationMustBeLongEnoughForHS256AlgorithmToWork

# Build and run
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

## 🧪 Running Tests

### Unit Tests

```bash
# Run all unit tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=CreateAfilliateUseCaseImplTest

# Run with coverage report
./mvnw clean verify
open target/site/jacoco/index.html
```

### Integration Tests

```bash
# Run integration tests (requires Docker)
./mvnw verify -P integration-tests

# Run specific integration test
./mvnw test -Dtest=CreditApplicationFlowIntegrationTest
```

### Test Coverage

Current coverage: **≥80%** for domain and use case packages

```bash
# Generate coverage report
./mvnw jacoco:report

# View report
open target/site/jacoco/index.html
```

## 📚 API Documentation

### Swagger UI

Access interactive API documentation at:
- **URL**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

### Key Endpoints

#### Authentication

```bash
# Register new affiliate
curl -X POST http://localhost:8085/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "password": "SecurePass123",
    "firstName": "John",
    "lastname": "Doe",
    "document": "123456789",
    "email": "john@example.com",
    "salary": 5000.00
  }'

# Login
curl -X POST http://localhost:8085/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "password": "SecurePass123"
  }'
```

#### Credit Applications

```bash
# Create credit application (requires JWT token)
curl -X POST http://localhost:8085/credit-applications \
  -H "Authorization: Bearer <YOUR_JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 10000.00,
    "term": 12,
    "afilliateId": 1
  }'

# Evaluate credit application
curl -X POST http://localhost:8085/credit-applications/1/evaluate \
  -H "Authorization: Bearer <YOUR_JWT_TOKEN>"

# List all applications
curl -X GET http://localhost:8085/credit-applications \
  -H "Authorization: Bearer <YOUR_JWT_TOKEN>"
```

## 📊 Observability

### Actuator Endpoints

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Application health status |
| `/actuator/metrics` | Available metrics |
| `/actuator/prometheus` | Prometheus-formatted metrics |
| `/actuator/info` | Application information |

### Prometheus Metrics

Access Prometheus at: http://localhost:9090

**Key Metrics:**
- `http_server_requests_seconds` - HTTP request duration
- `jvm_memory_used_bytes` - JVM memory usage
- `jvm_gc_pause_seconds` - Garbage collection pauses
- `auth_failures_total` - Authentication failures (custom)
- `risk_service_failures_total` - Risk service call failures (custom)

### Grafana Dashboards

Access Grafana at: http://localhost:3000 (admin/admin)

**Pre-configured Dashboards:**
- Request latency (p50, p95, p99)
- Error rates (4xx/5xx)
- Auth failure rate
- Risk service call metrics
- JVM metrics (heap, GC, threads)

## 🐳 Docker Deployment

### Build Docker Image

```bash
# Build image
docker build -t credit-application-service:latest .

# Run container
docker run -d \
  --name credit-app \
  -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://postgres:5432/coopcredit \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=postgres \
  credit-application-service:latest
```

### Docker Compose

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f credit-application-service

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

### Services in Docker Compose

| Service | Port | Description |
|---------|------|-------------|
| credit-application-service | 8080 | Main application |
| postgres | 5432 | PostgreSQL database |
| risk-service | 8081 | Mock risk evaluation service |
| prometheus | 9090 | Metrics collection |
| grafana | 3000 | Metrics visualization |

## 🔧 Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SERVER_PORT` | 8080 | Application port |
| `DB_URL` | jdbc:postgresql://localhost:5432/coopcredit | Database URL |
| `DB_USERNAME` | postgres | Database username |
| `DB_PASSWORD` | postgres | Database password |
| `JWT_SECRET` | (see .env.example) | JWT signing secret |
| `JWT_EXPIRATION` | 86400000 | JWT expiration (24h in ms) |
| `JWT_ISSUER` | credit-application-service | JWT issuer |
| `RISK_SERVICE_URL` | http://localhost:8081 | Risk service base URL |
| `RISK_SERVICE_TIMEOUT` | 5000 | Risk service timeout (ms) |
| `RISK_SERVICE_RETRY_MAX_ATTEMPTS` | 3 | Max retry attempts |
| `RISK_SERVICE_RETRY_BACKOFF` | 1000 | Retry backoff delay (ms) |

## 📁 Project Structure

```
CoopCredit/
├── src/
│   ├── main/
│   │   ├── java/com/coopcredit/credit/application_service/
│   │   │   ├── application/          # Use cases
│   │   │   │   └── usecase/
│   │   │   ├── domain/               # Domain models & ports
│   │   │   │   ├── model/
│   │   │   │   └── ports/
│   │   │   │       ├── in/           # Input ports (use cases)
│   │   │   │       └── out/          # Output ports (repositories)
│   │   │   └── infrastructure/       # Adapters
│   │   │       ├── adapter/          # JPA & external service adapters
│   │   │       ├── config/           # Spring configuration
│   │   │       ├── controller/       # REST controllers
│   │   │       ├── entity/           # JPA entities
│   │   │       ├── mapper/           # Domain ↔ Entity mappers
│   │   │       └── repository/       # JPA repositories
│   │   └── resources/
│   │       └── application.yaml      # Application configuration
│   └── test/
│       └── java/                     # Unit & integration tests
├── monitoring/                       # Prometheus & Grafana config
│   ├── prometheus.yml
│   ├── mockserver-init.json
│   └── grafana/
│       ├── provisioning/
│       └── dashboards/
├── Dockerfile                        # Multi-stage Docker build
├── docker-compose.yml                # Full stack deployment
├── verify_end_to_end.sh             # E2E verification script
├── pom.xml                           # Maven configuration
└── README.md                         # This file
```

## 📋 Business Rules

### Affiliate Registration
1. ✅ Document number must be unique
2. ✅ Salary must be greater than zero
3. ✅ Default status is ACTIVE
4. ✅ Registration date is set to current date

### Credit Application
1. ✅ Affiliate must be ACTIVE
2. ✅ Affiliate must have ≥3 months of seniority
3. ✅ Monthly installment ≤ 40% of affiliate's salary
4. ✅ Initial status is PENDING
5. ✅ Requested date is set to current timestamp

### Risk Evaluation
1. ✅ LOW risk → APPROVED
2. ✅ MEDIUM risk → PENDING (manual review)
3. ✅ HIGH risk → REJECTED
4. ✅ Service failure → REJECTED with fallback evaluation

## 🔍 Troubleshooting

### Application won't start

```bash
# Check if port 8080 is already in use
lsof -i :8080

# Check database connectivity
psql -h localhost -U postgres -d coopcredit

# View application logs
docker-compose logs credit-application-service
```

### Tests failing

```bash
# Clean and rebuild
./mvnw clean install

# Check test reports
cat target/surefire-reports/*.txt

# Run with debug logging
./mvnw test -X
```

### Docker issues

```bash
# Rebuild images
docker-compose build --no-cache

# Check service health
docker-compose ps

# View specific service logs
docker-compose logs -f postgres
```

### Database connection errors

```bash
# Verify PostgreSQL is running
docker-compose ps postgres

# Check database logs
docker-compose logs postgres

# Connect to database
docker-compose exec postgres psql -U postgres -d coopcredit
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## 👥 Authors

- **CoopCredit Development Team**

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Testcontainers for simplified integration testing
- Prometheus & Grafana for observability tools

---

**Built with ❤️ using Hexagonal Architecture**
