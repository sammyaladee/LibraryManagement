Library Management API

A RESTful API for managing books and authors, built with Spring Boot 3.x and secured with Spring Security Basic Authentication.

Tech Stack

- Java 17
- Spring Boot 3.5.10
- Spring Security(Basic Auth)
- Spring Data JPA (Hibernate)
- H2 (In-memory database)
- Maven

How to Run

Prerequisites
- Java 17 or 21 installed
- Maven (or use the included Maven wrapper)

Steps

1. Clone or unzip the project
```bash
cd LibraryManagement
```

2. Run the application

On Windows:
```powershell
.\mvnw.cmd spring-boot:run
```

On Mac/Linux:
```bash
./mvnw spring-boot:run
```

3. App is running when you see:
```
Started LibraryManagementApplication in X seconds
```

The server starts at http://localhost:8080


Database

This project uses H2 in-memory database — no installation required. The database is created fresh every time the app starts and wiped when it stops.

You can browse the database visually at:
```
http://localhost:8080/h2-console
```

| Field    | Value                  |
|----------|------------------------|
| JDBC URL | `jdbc:h2:mem:librarydb` |
| Username | `sa`                   |
| Password | *(leave blank)*        |


Security

The API uses HTTP Basic Authentication.

| Role      | Username    | Password   |
|-----------|-------------|------------|
| Librarian | `librarian` | `password` |

Authorization rules:
- `GET` requests → public (no login needed)
- `POST`, `PUT`, `DELETE` requests → **require authentication**


API Endpoints

Authors

| Method | Endpoint           | Auth Required | Description          |
|--------|--------------------|---------------|----------------------|
| POST   | `/api/authors`     | ✅ Yes        | Create a new author  |
| GET    | `/api/authors`     | ❌ No         | Get all authors      |
| GET    | `/api/authors/{id}`| ❌ No         | Get author by ID     |

Books

| Method | Endpoint        | Auth Required | Description            |
|--------|-----------------|---------------|------------------------|
| POST   | `/api/books`    | ✅ Yes        | Create a new book      |
| GET    | `/api/books`    | ❌ No         | Get all books          |
| GET    | `/api/books/{id}`| ❌ No        | Get book by ID         |
| PUT    | `/api/books/{id}`| ✅ Yes       | Update a book          |
| DELETE | `/api/books/{id}`| ✅ Yes       | Delete a book          |


Testing the Endpoints (cURL)

Create an Author
```bash
curl -X POST http://localhost:8080/api/authors \
  -u librarian:password \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Robert C. Martin",
    "email": "robert@example.com"
  }'
```

Get All Authors
```bash
curl http://localhost:8080/api/authors
```

Create a Book (link to an existing Author ID)
```bash
curl -X POST http://localhost:8080/api/books \
  -u librarian:password \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Clean Code",
    "isbn": "9780132350884",
    "publicationYear": "2008",
    "authorId": 1
  }'
```

Get All Books
```bash
curl http://localhost:8080/api/books
```

Get a Specific Book
```bash
curl http://localhost:8080/api/books/1
```

Update a Book
```bash
curl -X PUT http://localhost:8080/api/books/1 \
  -u librarian:password \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Clean Code (2nd Ed)",
    "isbn": "9780132350884",
    "publicationYear": "2009",
    "authorId": 1
  }'
```

Delete a Book
```bash
curl -X DELETE http://localhost:8080/api/books/1 \
  -u librarian:password
```

Test Unauthorized Access (should return 401)
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title": "Test"}'
```


HTTP Status Codes

| Status | Meaning                              |
|--------|--------------------------------------|
| 200    | OK — request succeeded               |
| 201    | Created — resource created           |
| 400    | Bad Request — validation failed      |
| 401    | Unauthorized — missing/wrong credentials |
| 404    | Not Found — resource doesn't exist   |


# Thought Process

The goal was to keep the architecture clean and follow standard Spring Boot conventions.

# Layered architecture: 
Controller → Service → Repository. Each layer has one job — the controller handles HTTP, the service handles business logic, the repository handles data access.

# Security design:
GET endpoints are public because reading library data shouldn't require a login. Any operation that modifies data (POST, PUT, DELETE) requires the librarian credentials. CSRF protection is disabled because REST APIs using Basic Auth are stateless — there are no browser session cookies for an attacker to hijack.

# H2 for development:
Using H2 means zero setup for anyone running the project. The schema is generated automatically by Hibernate on startup via `ddl-auto=create-drop`, so there are no SQL migration files to manage.

# Cascading deletes:
The `Author` → `Book` relationship uses `CascadeType.ALL` and `orphanRemoval=true`, meaning deleting an author automatically deletes all their books — no orphaned records left behind.