# Student Course Many-to-Many Mapping API

Complete Spring Boot project for direct many-to-many mapping between `Student` and `Course`, with database-based role security using `AppUser` and `Role`.

## Features

- Student-Course many-to-many mapping
- User-Role database-based security
- HTTP Basic Authentication
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

## Swagger

```text
http://localhost:8080/swagger-ui.html
```

## Postman Collection

```text
postman/Student_Course_Many_To_Many_API.postman_collection.json
```
