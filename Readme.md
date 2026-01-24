# Spring Boot User Manager API

---

## Description

This project is a **complete REST API** for user management built with **Spring Boot**.  
It demonstrates a clean layered architecture (**Controller** → **Service** → **Repository** → **Entity/DTO**), data validation, exception handling, and REST best practices.

The goal is to showcase mastery of **Spring Boot** for creating professional, testable, and maintainable APIs.

---

## Features

- **Complete CRUD operations for users**
    - **Create** (`POST /users`)
    - **Read** (`GET /users` and `GET /users/{id}`)
    - **Update** (`PUT /users/{id}`)
    - **Delete** (`DELETE /users/{id}`)
- **Server-side data validation** (`@NotBlank`, `@Email`)
- **Centralized error handling** with `@RestControllerAdvice`
- **DTOs for data exposure** (no direct entity exposure)
- **Static mapper** to transform **Entity** ↔ **DTO**
- **Correct HTTP status codes** for each operation
- **Compatible with in-memory H2 database** for quick testing and development

---

## Technologies Used

- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Lombok**
- **Jakarta Validation** (`@NotBlank`, `@Email`)

---

## Project Structure
```
com.example.springboot_user_manager
│
├── controller    → REST endpoints
├── service       → business logic
├── repository    → JPA interface for persistence
├── entity        → JPA entities
├── dto           → objects for API communication
├── mapper        → Entity ↔ DTO transformation
└── exception     → centralized error handling
```

---

## Usage Examples (Postman / HTTP)

---

### Create a user
```http
POST /users
Content-Type: application/json

{
  "name": "Alice",
  "email": "alice@mail.com",
  "password": "SecurePass123!"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Alice",
  "email": "alice@mail.com"
}
```

---

### Get all users
```http
GET /users
```

---

### Get a user by ID
```http
GET /users/1
```

---

### Update a user
```http
PUT /users/1
Content-Type: application/json

{
  "name": "Alice Updated",
  "email": "alice.updated@mail.com",
  "password": "NewSecurePass456!"
}
```

---

### Delete a user
```http
DELETE /users/1
```

---

## Security Notes

⚠️ **Important**: The password should **never be returned** in API responses (DTO). It must be **hashed** (bcrypt, Argon2, etc.) before being stored in the database.

---