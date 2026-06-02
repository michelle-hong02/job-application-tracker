# Job Tracker API

A backend service for tracking job applications built with Spring Boot.  
It provides JWT-based authentication, PostgreSQL persistence, and a fully containerized development environment using Docker and Docker Compose.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Security (JWT Authentication)
- Spring Data JPA
- PostgreSQL
- Swagger / OpenAPI
- Docker & Docker Compose

---

## Features

- User registration and login
- JWT-based authentication
- Create, read, update, and delete (CRUD) job applications
- User-specific application tracking
- Input validation and global exception handling
- Interactive API documentation via Swagger UI

---

## Getting Started

### Prerequisites

- Docker & Docker Compose (recommended)
- OR Java 17 + PostgreSQL (for local setup)

---

## Run with Docker (Recommended)

This is the easiest way to run the full stack (API + database).

### 1. Create environment variables

Create a `.env` file in the project root:

```env
JWT_SECRET=your-base64-encoded-secret
```

Generate a secure secret using:

```bash
openssl rand -base64 32
```

### 2. Start the application

```bash
docker compose up --build
```

### 3. Access the application

- **API Base URL:** `http://localhost:8080`
- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`

---

## Run Locally (Without Docker)

### 1. Configure PostgreSQL

Ensure PostgreSQL is running locally and create the database:

```sql
CREATE DATABASE jobtracker;
```

### 2. Set environment variable

```bash
export JWT_SECRET=your-base64-encoded-secret
```

### 3. Configure application properties

Update your `application.properties` (or `application.yml`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/jobtracker
spring.datasource.username=jobtracker
spring.datasource.password=jobtracker
```

### 4. Run the application

```bash
./gradlew bootRun
```

---

## API Documentation

Swagger UI is available at: `http://localhost:8080/swagger-ui/index.html`

It provides interactive access to test all endpoints directly from your browser.

---

## Authentication

This API uses JWT-based authentication.

### Endpoints
- **Register:** `POST /auth/register`
- **Login:** `POST /auth/login`

**Example response:**

```json
{
  "token": "your_jwt_token",
  "username": "your_username"
}
```

### Using the Token

For protected endpoints, include the JWT in the request header:

```http
Authorization: Bearer <your_token>
```

---

## Job Application Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **GET** | `/applications/me` | Get current user's applications |
| **POST** | `/applications` | Create a new application |
| **PUT** | `/applications/{id}` | Update an existing application |
| **DELETE** | `/applications/{id}` | Delete an application |

---

## Error Handling

All API errors follow a consistent structure:

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid credentials",
  "timestamp": 1712312312312
}
```

---

## Notes

- Swagger UI is enabled by default via SpringDoc.
- PostgreSQL runs in a Docker container when using Docker Compose.
- JWT secrets must be Base64-encoded for HS256 signing.

---

## License

This project is for educational and portfolio purposes.