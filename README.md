# Job Tracker API

A backend service for tracking job applications, built with Spring Boot.
Supports user authentication, JWT-based security, and CRUD operations for managing applications.

---

## Tech Stack

* Java 17
* Spring Boot
* Spring Security (JWT Authentication)
* Spring Data JPA
* PostgreSQL
* Swagger / OpenAPI (API documentation)
* Docker
---

## ⚙️ Features

- Track and manage job applications
- User registration and login
- JWT-based authentication
- Secure REST API endpoints
- Input validation and global exception handling
- Swagger UI for API testing

---

## 🏃 How to Run Locally

### 1. Clone the repository

```bash
git clone https://github.com/your-username/job-tracker.git
cd job-tracker
```

### 2. Configure environment variables

Update your `application.yml` or `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/jobtracker
spring.datasource.username=your_username
spring.datasource.password=your_password
jwt.secret=your_jwt_secret
```

### 3. Run the application

```bash
./gradlew bootRun
```

App will start at:

```
http://localhost:8080
```

---

## 📘 API Documentation (Swagger)

Once the app is running, access Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔐 Authentication

This API uses JWT authentication.

### 1. Register

```
POST /auth/register
```

### 2. Login

```
POST /auth/login
```

Response:

```json
{
  "token": "your_jwt_token",
  "username": "your_username"
}
```

### 3. Use token in requests

Add header:

```
Authorization: Bearer <your_token>
```

---

## 📌 Example API Calls

### Get current user's applications

```
GET /applications/me
```

Header:

```
Authorization: Bearer <your_token>
```

---

## ❗ Error Handling

All errors follow a consistent format:

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid username or password",
  "timestamp": 1712312312312
}
```

---

## 🐳 Run with Docker

Make sure Docker is installed, then run:

```
docker-compose up --build
```


---

## 📄 License

This project is for educational and portfolio purposes.
