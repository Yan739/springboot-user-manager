# Spring Boot User Manager API

---

## Description

This project is a **complete REST API** for user management built with **Spring Boot**.  
It demonstrates a clean layered architecture (**Controller** → **Service** → **Repository** → **Entity/DTO**), data validation, exception handling, secure password management, JWT authentication, role-based access, logout with token blacklist, and REST best practices.

The goal is to showcase mastery of **Spring Boot** for creating professional, testable, and maintainable APIs.

---

## Features

- **Complete CRUD operations for users**
  - **Create** (`POST /users`)
  - **Read** (`GET /users` and `GET /users/{id}`)
  - **Update** (`PUT /users/{id}`)
  - **Delete** (`DELETE /users/{id}`)
- **Authentication & Authorization**
  - **Register** (`POST /auth/register`)
  - **Login** (`POST /auth/login`) with JWT generation
  - **Role-based access control** (`@PreAuthorize("hasRole('ADMIN')")`)
  - **Logout & blacklist tokens** (`POST /auth/logout`)
  - **Refresh token endpoint** (`POST /auth/refresh`)
- **Server-side data validation** (`@NotBlank`, `@Email`, `@Size`)
- **Centralized error handling** with `@RestControllerAdvice`
- **DTOs for data exposure** (no direct entity exposure)
- **Static mapper** to transform **Entity** ↔ **DTO**
- **Correct HTTP status codes** for each operation
- **Secure password storage using BCrypt** (hashed before saving to database)
- **Compatible with in-memory H2 database** for quick testing and development
- **Postman collection ready** for testing all endpoints
- **H2 console available** for inspecting database contents

---

## Technologies Used

- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA**
- **H2 Database** (in-memory or file)
- **Lombok**
- **Jakarta Validation** (`@NotBlank`, `@Email`, `@Size`)
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **BCrypt password hashing**

---

## Project Structure
```
com.example.springboot_user_manager
│
├── config        → Beans and Spring configuration (PasswordEncoder, JWT filter, security config)
├── controller    → REST endpoints for users and auth
├── service       → business logic, JWT generation, blacklist, password hashing
├── repository    → JPA interfaces for persistence
├── entity        → JPA entities (User, BlacklistedToken, RefreshToken)
├── dto           → objects for API communication (UserDTO, RegisterDTO, LoginDTO, RefreshTokenDTO, etc.)
├── mapper        → Entity ↔ DTO transformation
└── exception     → centralized error handling (GlobalExceptionHandler, custom exceptions)
```

---

## Security Flow

1. **Register**
  - Endpoint: `POST /auth/register`
  - Password is hashed with BCrypt before saving.
  - Role (`USER` by default or `ADMIN`) is assigned.

2. **Login**
  - Endpoint: `POST /auth/login`
  - Validates credentials and returns **JWT access token** and optional **refresh token**.

3. **JWT Validation**
  - `JwtAuthenticationFilter` intercepts requests to protected endpoints
  - Checks if token is **valid**, **not expired**, and **not blacklisted**.

4. **Role-based Access**
  - Example: `/users/admin` accessible only to `ADMIN` users via `@PreAuthorize("hasRole('ADMIN')")`.

5. **Logout**
  - Endpoint: `POST /auth/logout`
  - Adds the token to the **blacklist**, preventing future use.

6. **Refresh Token**
  - Endpoint: `POST /auth/refresh`
  - Generates a new access token using a valid refresh token.

---

## Usage Examples (Postman / HTTP)

---

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

---

### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "alice@mail.com",
  "password": "SecurePass123!"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "..."
}
```

---

### Logout
```http
POST /auth/logout
Authorization: Bearer {{accessToken}}
```

- Token is added to blacklist.
- Any request with this token after logout will return **401 Unauthorized**.

---

### Refresh Token
```http
POST /auth/refresh
Content-Type: application/json

{
  "refreshToken": "{{refreshToken}}"
}
```

**Response:**
```json
{
  "accessToken": "newAccessToken..."
}
```

---

### CRUD User Endpoints (require JWT)
```http
GET /users
GET /users/{id}
POST /users
PUT /users/{id}
DELETE /users/{id}
```

- Use **Authorization** header: `Bearer {{accessToken}}`
- Role-based endpoints (`/users/admin`) only accessible by **ADMIN**.

---

## H2 Console

- **Accessible at:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb` (or your file path)
- **Username:** `sa`
- **Password:** (leave empty)

Allows inspecting users, refresh tokens, and blacklisted tokens.

---

## Notes for Developers

- **Passwords** are never exposed in API responses (only via DTOs without `password`).
- **Blacklist** prevents reuse of tokens after logout.
- **Tokens** have expiration and can be refreshed securely.
- **Roles** can be extended or modified easily using Spring Security.
- **Recommended testing flow in Postman:**
  1. Register → 2. Login → 3. Access protected endpoints → 4. Logout → 5. Refresh token
- **H2 console** is useful for debugging and inspecting database content during development.
- **Ensure JWT secret key** is strong and not hard-coded for production.

---