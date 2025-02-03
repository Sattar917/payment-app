# Banking Microservices Project

## Overview
This project is a small banking backend REST application using Spring Boot. It includes multiple microservices with secure communication, authentication, and transaction management.

## Services
- **Eureka Server** (Service Discovery)
- **Spring Cloud Gateway** (API Gateway)
- **ms-auth** (Authentication Service with JWT)
- **ms-transaction** (Transaction Management)

## Tech Stack
- Java, Spring Boot
- Spring Cloud (Eureka, Gateway)
- PostgreSQL
- Docker & Docker Compose
- JWT Authentication
- Gradle

## Setup & Run

### Prerequisites
- Install [Docker](https://www.docker.com/)
- Install [Java 17+](https://adoptopenjdk.net/)
- Install [Gradle](https://gradle.org/)
- Install [Postman](https://www.postman.com/) (optional for testing)

### Running with Docker Compose

git clone https://github.com/Sattar917/payment-app.git

then build and start the services

docker-compose up --build

Services should now be available:
Eureka Server: http://localhost:8761

API Gateway: http://localhost:8080

Auth Service: http://localhost:8081

Transaction Service: http://localhost:8082

## Features:
-registration
-login
-topup
-purchase
-refund



