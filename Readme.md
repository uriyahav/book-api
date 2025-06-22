# 📚 Book Management API

A production-ready Book Management REST API built with Java, Spring Boot 3, and Hibernate. Supports CRUD operations, user authentication, and advanced queries for users and orders.

---

## 🧰 Tech Stack
- Java 17+
- Spring Boot 3
- Spring Security
- Spring Data JPA (Hibernate)
- H2 In-Memory Database
- Maven
- JUnit & Mockito
- Docker

---

## 🚀 Features
- CRUD for books with validation
- Layered architecture (Controller, Service, Repository, DTO, Model)
- Global exception handling
- In-memory authentication (user/password, admin/password)
- Role-based access control (admin-only DELETE)
- User & Order entities with custom queries
- Full unit and integration test coverage

---

## 📘 API Endpoints
- `GET /books` - List all books
- `GET /books/{id}` - Get book by ID
- `POST /books` - Create book
- `PUT /books/{id}` - Update book
- `DELETE /books/{id}` - Delete book (admin only)

## 📝 API Examples

### Book CRUD

**Create Book**
```
POST /books
Authorization: Basic dXNlcjpwYXNzd29yZA==
Content-Type: application/json

{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "publishedYear": 2008
}
```
**Response:**
```
201 Created
{
  "id": 1,
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "publishedYear": 2008
}
```

**Validation Error**
```
POST /books
{
  "title": "",
  "author": "",
  "publishedYear": 1400
}
```
**Response:**
```
400 Bad Request
{
  "timestamp": "2024-06-19T12:00:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Validation failed",
  "path": "/books",
  "errors": {
    "title": "must not be blank",
    "author": "must not be blank",
    "publishedYear": "must be greater than or equal to 1500"
  }
}
```

**Get All Books**
```
GET /books
Authorization: Basic dXNlcjpwYXNzd29yZA==
```
**Response:**
```
200 OK
[
  {
    "id": 1,
    "title": "Clean Code",
    "author": "Robert C. Martin",
    "publishedYear": 2008
  }
]
```

**Delete Book (admin only)**
```
DELETE /books/1
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
```
**Response:**
```
204 No Content
```

### Security

**Unauthorized (no auth):**
```
GET /books
```
**Response:**
```
401 Unauthorized
```

**Forbidden (user tries to delete):**
```
DELETE /books/1
Authorization: Basic dXNlcjpwYXNzd29yZA==
```
**Response:**
```
403 Forbidden
```

### User Custom Queries

**Users with more than 3 orders**
```
GET /users/more-than-3-orders
```
**Response:**
```
200 OK
[
  { "id": 1, "username": "alice", "role": "USER", ... },
  { "id": 4, "username": "dave", "role": "USER", ... }
]
```

**Users with no orders**
```
GET /users/no-orders
```
**Response:**
```
200 OK
[
  { "id": 3, "username": "carol", "role": "USER", ... }
]
```

**Total order amount per user**
```
GET /users/total-order-amount
```
**Response:**
```
200 OK
[
  { "username": "alice", "totalAmount": 100.0 },
  { "username": "bob", "totalAmount": 40.0 },
  { "username": "dave", "totalAmount": 75.0 }
]
```

---

For more, see the included Postman collection: `Book-API.postman_collection.json`.

---

## 🔐 Security
- In-memory users:
  - User: `user` / `password` (ROLE_USER)
  - Admin: `admin` / `password` (ROLE_ADMIN)
- `/books/**` requires authentication
- `DELETE /books/**` requires ROLE_ADMIN

---

## 🏃 Running Locally

1. **Build the project:**
   ```bash
   ./mvnw clean install
   ```
2. **Run the app:**
   ```bash
   ./mvnw spring-boot:run
   ```
3. **Access API:**
   - Base URL: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

---

## 🐳 Running with Docker

1. **Build the Docker image:**
   ```bash
   docker build -t book-api .
   ```
2. **Run the container:**
   ```bash
   docker run -p 8080:8080 book-api
   ```
3. **Access API:**
   - Base URL: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

---

## 🧪 Testing
- **Run all tests:**
  ```bash
  ./mvnw test
  ```
- Includes:
  - Controller integration tests (`@SpringBootTest` + MockMvc)
  - Service unit tests (Mockito)
  - Entity and mapper validation tests
  - **UserController integration tests for custom queries**

---

## 🗂️ Postman Collection
- Import `Book-API.postman_collection.json` into Postman for full API coverage:
  - Book CRUD (all security scenarios)
  - User/Order custom queries
  - Validation and error cases

---

## 🔄 Serialization & Recursion Handling
- Uses Jackson `@JsonManagedReference`/`@JsonBackReference` to prevent infinite recursion in User/Order JSON responses.

---

## ✅ Project Status
- All requirements, extensions, and tests are complete and passing.
- Fully review-ready and demo-ready.

---

## 🛠️ Next Steps & Suggested Improvements
- JavaDocs, inline comments, API examples
- Pagination, CORS, more roles
- Swagger/OpenAPI docs
- Test coverage badge, and more

Pull requests and suggestions are welcome!

---

## 👤 User & Order Extension
- **User Entity**: `id`, `username`, `role` (enum: USER, ADMIN)
- **Order Entity**: `id`, `amount`, `user` (many-to-one relationship)
- **Custom Repository Queries**:
  - Find users with more than 3 orders
  - Calculate total order amount per user
  - Find users with no orders

### User & Order Endpoints
- `GET /users/more-than-3-orders` - Users with >3 orders
- `GET /users/no-orders` - Users with no orders
- `GET /users/total-order-amount` - Total amount per user

---

## 🏗️ Project Structure
```
src/
├── main/java/com/example/bookapi/
│   ├── BookApiApplication.java          # Main application class
│   ├── config/
│   │   └── SecurityConfig.java          # Security configuration
│   ├── controller/
│   │   ├── BookController.java          # Book REST endpoints
│   │   └── UserController.java          # User custom queries
│   ├── dto/
│   │   ├── BookMapper.java              # Entity-DTO mapping
│   │   ├── BookPageResponse.java        # Pagination response
│   │   ├── BookRequest.java             # Book creation/update DTO
│   │   ├── BookResponse.java            # Book response DTO
│   │   └── UserOrderTotal.java          # User order totals DTO
│   ├── exception/
│   │   └── GlobalExceptionHandler.java  # Global error handling
│   ├── model/
│   │   ├── Book.java                    # Book entity
│   │   ├── Order.java                   # Order entity
│   │   ├── User.java                    # User entity
│   │   └── enums/
│   │       └── Role.java                # User roles enum
│   ├── repository/
│   │   ├── BookRepository.java          # Book data access
│   │   ├── OrderRepository.java         # Order data access
│   │   └── UserRepository.java          # User data access
│   └── service/
│       ├── BookService.java             # Book service interface
│       └── impl/
│           └── BookServiceImpl.java     # Book service implementation
├── resources/
│   ├── application.yml                  # Application configuration
│   └── data.sql                         # Sample data
└── test/                                # Comprehensive test suite
    └── java/com/example/bookapi/
        ├── controller/                   # Integration tests
        ├── dto/                         # DTO tests
        ├── model/                       # Entity tests
        └── service/                     # Service tests
```

---

## 📊 Database Schema
- **Book**: `id`, `title`, `author`, `publishedYear`
- **User**: `id`, `username`, `role`
- **Order**: `id`, `amount`, `user_id` (foreign key)

---

## 🔧 Configuration
- **Database**: H2 in-memory with sample data
- **Security**: In-memory authentication with role-based access
- **Validation**: Bean Validation with custom validators
- **Error Handling**: Global exception handler with detailed error messages

---

## 🚀 Quick Start
1. **Clone and run:**
   ```bash
   git clone <repository-url>
   cd book-api
   ./mvnw spring-boot:run
   ```

2. **Test with curl:**
   ```bash
   # Get all books (requires auth)
   curl -u user:password http://localhost:8080/books
   
   # Create a book
   curl -X POST -u user:password -H "Content-Type: application/json" \
     -d '{"title":"Test Book","author":"Test Author","publishedYear":2024}' \
     http://localhost:8080/books
   ```

---

## 📝 License
This project is licensed under the MIT License.

---

**🎉 Ready for production use! All features implemented, tested, and documented.**
