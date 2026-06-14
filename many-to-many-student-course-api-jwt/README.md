# Student Course Many-to-Many Mapping API with JWT

Complete Spring Boot project for direct many-to-many mapping between `Student` and `Course`.

This version uses JWT token-based authentication with users and roles stored in MySQL.

## Features

- Student-Course many-to-many mapping
- User-Role database-based security
- JWT authentication
- Role-based authorization
- CRUD APIs for students and courses
- Assign course to student
- Remove course from student
- Pagination with sorting
- DTO-based request/response
- Global exception handling
- Swagger/OpenAPI
- Logger statements
- MySQL persistence
- Lombok and ModelMapper

## Database

Create database manually:

```sql
CREATE DATABASE many_to_many_demo;
```

Update DB credentials in:

```text
src/main/resources/application.properties
```

## Default Users

Created automatically on application startup:

| Username | Password | Roles |
|---|---|---|
| admin | admin123 | ROLE_ADMIN, ROLE_USER |
| user | user123 | ROLE_USER |

## Run

```bash
mvn spring-boot:run
```

Application runs on:

```text
http://localhost:8080
```

## Login API

Run this first:

```text
POST http://localhost:8080/api/auth/login
```

Admin body:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

User body:

```json
{
  "username": "user",
  "password": "user123"
}
```

Use returned token in secured APIs:

```text
Authorization: Bearer JWT_TOKEN_HERE
```

## Swagger

```text
http://localhost:8080/swagger-ui.html
```

Click Authorize and enter only the JWT token if Swagger asks for a bearer token.

## Postman

Use:

```text
postman/Student_Course_Many_To_Many_API_JWT.postman_collection.json
```

Run order:

1. Login as ADMIN - Save JWT
2. Create courses
3. Create student with course IDs
4. Test GET APIs
5. Test relationship APIs
6. Test update/delete APIs
