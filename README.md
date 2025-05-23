# Library Management System

A Spring Boot REST API backend for managing books, users, borrowing, and returning books in a library.

---

## Features

### Book Management
- Add new books
- Update existing books
- Delete books by ID
- Fetch all books or a specific book by ID

### User Management
- Register new users
- Update user details
- Delete users by ID
- Get list of all users

### Borrowing & Returning Books
- Borrow a book (only if available and user hasn’t borrowed another)
- Return a borrowed book
- View all borrow records

### Extended Borrowings
- Get all borrow records where return date is extended beyond 7 days (late returns)

### User Login
- Authenticate user login with email and password

---

## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL
- Lombok
- Maven
- RESTful API

---

## Database Schema

### Tables

#### 1. `books`

| Column     | Type          | Constraints                  | Description                          |
|------------|---------------|-----------------------------|------------------------------------|
| book_id    | BIGINT (PK)   | AUTO_INCREMENT              | Primary key (Book ID)               |
| title      | VARCHAR       | NOT NULL                    | Book title                         |
| author     | VARCHAR       | NOT NULL                    | Book author                        |
| isbn       | VARCHAR       | NOT NULL, UNIQUE            | Unique ISBN code                   |
| quantity   | INT           | NOT NULL                    | Number of copies                   |
| available  | BOOLEAN       | DEFAULT FALSE               | Availability status                |
| created_at | DATETIME      | NOT NULL, auto timestamp    | Creation timestamp                 |
| updated_at | DATETIME      | auto timestamp              | Last update timestamp              |

#### 2. `borrowed_books`

| Column      | Type        | Constraints                   | Description                           |
|-------------|-------------|------------------------------|-------------------------------------|
| borrow_id   | BIGINT (PK) | AUTO_INCREMENT               | Primary key (Borrow record ID)       |
| user_id     | BIGINT (FK) | NOT NULL                    | Foreign key to `users.user_id`       |
| book_id     | BIGINT (FK) | NOT NULL                    | Foreign key to `books.book_id`       |
| borrow_date | DATE        | Nullable                    | Date book was borrowed                |
| return_date | DATE        | NOT NULL                   | Due return date                       |

#### 3. `users`

| Column        | Type          | Constraints                  | Description                          |
|---------------|---------------|-----------------------------|------------------------------------|
| user_id       | BIGINT (PK)   | AUTO_INCREMENT              | Primary key (User ID)               |
| name          | VARCHAR       | NOT NULL                   | User full name                     |
| email         | VARCHAR       | NOT NULL, UNIQUE           | Unique user email                  |
| mobile_number | VARCHAR       | NOT NULL                   | User contact number                |
| password      | VARCHAR       | NOT NULL                   | User password (hashed)             |
| user_role     | VARCHAR       | NOT NULL                   | Role of user (ADMIN, USER, etc.)  |
| is_borrowed   | BOOLEAN       | DEFAULT FALSE              | Current borrow status              |
| created_at    | DATETIME      | NOT NULL, auto timestamp   | Account creation time              |
| updated_at    | DATETIME      | auto timestamp             | Last update time                   |

---
## API Testing and Documentation

### Postman Collection

You can import the Postman collection for this project to test all APIs quickly:

**Postman Collection Download Link:**  
[Postman Collection](https://speeding-desert-183863.postman.co/workspace/My-Workspace~0435e3fe-58d1-4a82-861b-12a401f18364/collection/21725198-aeef230a-6a99-46af-8063-ecb586cd6c76?action=share&creator=21725198&active-environment=21725198-9e1366bc-1384-4c77-ac96-7fb879df31bd)

---

### Swagger UI

Interactive API documentation is available via Swagger UI once the project is running.

**Access Swagger UI at:**
http://localhost:8080/library/swagger-ui/index.html
## How to Run

1. **Clone the repository:**
2. **Configure the database connection in application.yml.**
3. **Build the project using Maven**
4. ****mvn clean install****

