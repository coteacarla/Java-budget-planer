# Expense Tracker

A Spring Boot expense-tracking application with user registration, password-protected login, a Thymeleaf dashboard, and a REST API for managing expenses.

## Features

- Register a user account.
- Store passwords using BCrypt hashing.
- Log in and out with Spring Security form authentication.
- View a user-specific expense dashboard.
- Create, list, and delete expenses through the REST API.
- Associate every expense with the authenticated user.
- Store application data in an H2 in-memory database.
- Accept JWT bearer tokens through a security filter when a valid token is supplied.

## Technology Stack

- Java 21
- Spring Boot 3.5.0
- Spring MVC and Spring Web
- Spring Security
- Spring Data JPA and Hibernate
- Thymeleaf
- H2 Database
- JSON Web Token (JJWT)
- Lombok
- Maven Wrapper
- Bootstrap 5 for the HTML templates

## Requirements

- JDK 21 or newer
- No separate database server is required for the default configuration

## Running the Application

From the `demo` directory, run:

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### macOS/Linux

```bash
./mvnw spring-boot:run
```

The application starts on port `8081`:

```text
http://localhost:8081
```

To build the project without starting it:

```powershell
.\mvnw.cmd clean package
```

To run the tests:

```powershell
.\mvnw.cmd test
```

## Using the Web Application

1. Open `http://localhost:8081/register`.
2. Create an account with a username and password.
3. Open the login page at `http://localhost:8081/login`.
4. Sign in to reach `/dashboard`.
5. Use the dashboard to view expenses associated with the current user.
6. Use the logout button to end the session.

## REST API

The expense API is rooted at `/api` and uses the currently authenticated user.

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/expenses` | List expenses for the current user |
| `POST` | `/api/expenses` | Create an expense for the current user |
| `DELETE` | `/api/expenses/{id}` | Delete an expense by ID |

Authenticate with `POST /api/auth/login` to receive a JWT:

```json
{
  "username": "your-username",
  "password": "your-password"
}
```

Send the returned token in the `Authorization: Bearer <token>` header when calling the API.

Example request body for `POST /api/expenses`:

```json
{
  "category": "Food",
  "description": "Lunch",
  "amount": 12.50,
  "date": "2026-10-12"
}
```

The `user` relationship is assigned by the server from the authenticated user and should not be supplied by the client.

## Data Model

### User

- `id`
- `username`
- `password`
- A one-to-many relationship with expenses

### Expense

- `id`
- `category`
- `description`
- `amount`
- `date`
- A many-to-one relationship with a user

## Security

- `/register` and `/login` are available without authentication.
- Successful form login redirects to `/dashboard`.
- Passwords are encoded with `BCryptPasswordEncoder`.
- The application includes a JWT authentication filter that reads `Authorization: Bearer <token>` headers.
- JWTs are configured to expire after 24 hours.
- CSRF protection is enabled for browser forms and ignored for the REST API.

JWT signing uses the `app.jwt.secret` configuration property.

## H2 Database Console

The H2 console is enabled for local development at:

```text
http://localhost:8081/h2-console
```

Default connection settings:

```text
JDBC URL: jdbc:h2:file:./data/expensedb
User Name: sa
Password: budget-dev-password
```

The database is stored under the local `data` directory.

## Project Structure

```text
demo/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/example/demo/
    │   │   ├── config/       # Spring Security configuration
    │   │   ├── controller/   # Web and REST controllers
    │   │   ├── model/        # User and Expense entities
    │   │   ├── repository/   # Spring Data JPA repositories
    │   │   ├── security/     # JWT filter and utility
    │   │   └── service/      # User authentication and expense services
    │   └── resources/
    │       ├── application.properties
    │       └── templates/    # Thymeleaf pages
    └── test/                 # Spring Boot tests
```

