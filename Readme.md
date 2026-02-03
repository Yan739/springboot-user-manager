# Spring Boot User Manager API

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-blue?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1-green?logo=spring&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2-Database-blue?logo=h2&logoColor=white)
![Build](https://img.shields.io/badge/Build-Maven-red?logo=apachemaven&logoColor=white)
![Code Style](https://img.shields.io/badge/Code%20Style-DTO%2FEntity-lightgrey)
![JWT](https://img.shields.io/badge/JWT-Security-orange)

</div>

---

## Description

Complete REST API for user management built with **Spring Boot**. It demonstrates a clean layered architecture (**Controller** → **Service** → **Repository** → **Entity/DTO**), data validation, exception handling, secure password management, JWT authentication, role-based access, logout with token blacklist, and REST best practices.

---

## Features

- **Complete CRUD operations for users**
  - Create, Read, Update, Delete
  - Role-based access control (`ADMIN`, `USER`)
- **Authentication & Security**
  - Register with BCrypt password hashing
  - Login with JWT generation
  - Logout with token blacklisting
  - Refresh token support
  - Role-based access via `@PreAuthorize`
- **Architecture & Quality**
  - Server-side data validation (Jakarta Validation)
  - Centralized error handling via `@RestControllerAdvice`
  - DTOs for data exposure (no direct entity exposure)
  - Static mapper for Entity ↔ DTO transformation
  - Correct HTTP status codes for each operation

---

## Technologies

| Technology | Version |
|---|---|
| Java | 17+ |
| Spring Boot | 3.1+ |
| Spring Data JPA | - |
| Spring Security | - |
| H2 Database | In-memory / File |
| Lombok | - |
| Jakarta Validation | - |
| JWT (JSON Web Tokens) | - |
| BCrypt | Password Hashing |

---

## Project Structure
```
com.example.springboot_user_manager
│
├── config/           → Beans and Spring configuration (PasswordEncoder, JWT filter, SecurityConfig)
├── controller/       → REST endpoints (Auth, User)
├── service/          → Business logic, JWT generation, blacklist, password hashing
├── repository/       → JPA interfaces for persistence
├── entity/           → JPA entities (User, BlacklistedToken, RefreshToken)
├── dto/              → API communication objects (UserDTO, RegisterDTO, LoginDTO, RefreshTokenDTO)
├── mapper/           → Entity ↔ DTO transformation
└── exception/        → Centralized error handling (GlobalExceptionHandler, custom exceptions)
```

---

## Security Flow
```
Client                          Serveur
  │                                │
  │──── POST /auth/register ──────>│  Hash password (BCrypt), assign role
  │<─── 200 OK ────────────────────│
  │                                │
  │──── POST /auth/login ─────────>│  Validate credentials
  │<─── { accessToken, refreshToken }
  │                                │
  │──── GET /users ───────────────>│  Header: Authorization: Bearer <accessToken>
  │<─── 200 OK + données ──────────│  JwtFilter: valid + not expired + not blacklisted
  │                                │
  │──── POST /auth/logout ────────>│  Token added to blacklist
  │<─── 200 OK ────────────────────│
  │                                │
  │──── POST /auth/refresh ───────>│  Sends refreshToken
  │<─── { nouveau accessToken } ───│
```

1. **Register** — Password is hashed with BCrypt before saving. Role (`USER` by default or `ADMIN`) is assigned.
2. **Login** — Validates credentials and returns a JWT access token and a refresh token.
3. **JWT Validation** — `JwtAuthenticationFilter` intercepts requests. Checks if the token is valid, not expired, and not blacklisted.
4. **Role-based Access** — Certain endpoints are restricted to `ADMIN` via `@PreAuthorize("hasRole('ADMIN')")`.
5. **Logout** — The token is added to a blacklist, preventing any future use.
6. **Refresh Token** — Generates a new access token using a valid refresh token.

---

## Endpoints

### Authentication

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | `/auth/register` | Register a new user | No |
| POST | `/auth/login` | Login and get tokens | No |
| POST | `/auth/logout` | Logout and blacklist token | Yes |
| POST | `/auth/refresh` | Refresh access token | No |

### Users

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| GET | `/users` | List all users | Yes |
| GET | `/users/{id}` | Get user by ID | Yes |
| POST | `/users` | Create a user | Yes |
| PUT | `/users/{id}` | Update a user | Yes |
| DELETE | `/users/{id}` | Delete a user | Yes |
| GET | `/users/admin` | Admin only endpoint | ADMIN |

---

## Examples

### Register
```http
POST /auth/register
Content-Type: application/json

{
  "name": "Alice",
  "email": "alice@mail.com",
  "password": "SecurePass123!",
  "role": "ADMIN"
}
```

### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "alice@mail.com",
  "password": "SecurePass123!"
}
```

### Login Response
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "..."
}
```

### Logout
```http
POST /auth/logout
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Refresh Token
```http
POST /auth/refresh
Content-Type: application/json

{
  "refreshToken": "..."
}
```

### Refresh Token Response
```json
{
  "accessToken": "newAccessToken..."
}
```

### CRUD Users
```http
GET /users
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```
```http
GET /users/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```
```http
POST /users
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "name": "Bob",
  "email": "bob@mail.com",
  "password": "SecurePass123!",
  "role": "USER"
}
```
```http
PUT /users/1
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

{
  "name": "Alice Updated",
  "email": "alice.updated@mail.com"
}
```
```http
DELETE /users/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## Installation
```bash
# Clone the project
git clone <repo-url>
cd springboot-user-manager

# Build
mvn clean compile

# Run tests
mvn test

# Run the project
mvn spring-boot:run
```

API available at: `http://localhost:8080`

---

## H2 Console

| Parameter | Value |
|---|---|
| URL | `http://localhost:8080/h2-console` |
| JDBC URL | `jdbc:h2:mem:testdb` |
| User | `sa` |
| Password | *(empty)* |

---

## Developer Notes

- Passwords are never exposed in API responses (filtered via DTOs).
- Blacklisted tokens are stored in the database and checked on every request.
- Tokens have an expiration time and can be refreshed securely.
- Roles can be extended easily using Spring Security configuration.
- Recommended testing flow: Register → Login → Access endpoints → Logout → Refresh token.
- Ensure the JWT secret key is strong and not hard-coded in production.

---

*Projet personnel — public — pas de licence restrictive.*