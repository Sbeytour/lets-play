# Let's Play

A RESTful CRUD API built with Spring Boot and MongoDB for managing users and products with JWT authentication.

## Tech Stack

- Spring Boot 4.0.2
- MongoDB
- Spring Security + JWT
- BCrypt password hashing

## API Endpoints

### Auth
| Method | Endpoint | Access |
|--------|----------|--------|
| POST | `/auth/register` | Public |
| POST | `/auth/login` | Public |
| GET | `/auth/me` | Authenticated |

### Products
| Method | Endpoint | Access |
|--------|----------|--------|
| GET | `/api/products` | Public |
| GET | `/api/products/{id}` | Public |
| POST | `/api/products` | Authenticated |
| PATCH | `/api/products/{id}` | Owner |
| DELETE | `/api/products/{id}` | Owner/Admin |

### Users (Admin only)
| Method | Endpoint | Access |
|--------|----------|--------|
| GET | `/api/users` | Admin |
| GET | `/api/users/{id}` | Admin |
| DELETE | `/api/users/{id}` | Admin |
| PATCH | `/api/users` | Authenticated (self) |

## Run

```bash
./mvnw spring-boot:run
```

## Configuration

Set the following in `application.properties`:
- MongoDB connection
- JWT secret key
