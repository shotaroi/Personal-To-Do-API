# Personal To-Do API

A RESTful backend API for managing personal to-do items with **JWT-based authentication**.  
Built with Spring Boot and PostgreSQL, focusing on clean architecture, security, and correctness.

This project is intended as a **portfolio-quality backend application**, not a UI-focused app.

---

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven
- Swagger / OpenAPI (springdoc)

---

## Features

- User registration and login
- Password hashing with BCrypt
- JWT-based stateless authentication
- User-scoped to-dos (users can only access their own data)
- CRUD operations for to-dos
- Pagination support
- Input validation
- Global exception handling
- Interactive API documentation with Swagger UI

---

## Domain Model (Simplified)

- **User**
  - id
  - email (unique)
  - passwordHash

- **Todo**
  - id
  - ownerEmail
  - title
  - description
  - status (TODO, IN_PROGRESS, DONE)
  - dueDate

---

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|------|---------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT |

### To-Dos (JWT required)
| Method | Endpoint | Description |
|------|---------|-------------|
| GET | `/api/todos` | List user’s todos (paginated) |
| POST | `/api/todos` | Create a new todo |
| PATCH | `/api/todos/{id}` | Update a todo |
| DELETE | `/api/todos/{id}` | Delete a todo |

---

## Running the Application

### 1. Prerequisites
- Java 21
- PostgreSQL
- Maven

---

### 2. Database Setup

Create a database and user (example):

```sql
CREATE USER todo_user WITH PASSWORD 'todo_pass';
CREATE DATABASE todo_db OWNER todo_user;
GRANT ALL PRIVILEGES ON DATABASE todo_db TO todo_user;
GRANT ALL ON SCHEMA public TO todo_user;
