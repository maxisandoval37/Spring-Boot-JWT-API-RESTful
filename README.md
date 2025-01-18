# Spring Boot JWT Authentication API

This application provides a REST API for user authentication and token management using JSON Web Tokens (JWT).

# Technologies Used
- Spring Boot
- Spring Security
- Spring Boot JPA
- JWT (jjwt-api, jjwt-impl and jjwt-jackson)
- Swagger UI
- Lombok
- H2 Database

## Endpoints

### 1. **Register User**

**URL:**  
`POST http://localhost:8080/auth/register`

**Request Body:**
```json
{
    "name": "usernameTest",
    "email": "user@email.com",
    "password": "pass123"
}
```

**Response (200):**
```json
{
    "access_token": "<access_token>",
    "refresh_token": "<refresh_token>"
}
```

### 2. **Login User**
**URL:**
`POST http://localhost:8080/auth/login`

**Request Body:**
```json
{
    "name": "usernameTest",
    "email": "user@email.com",
    "password": "pass123"
}
```

**Headers:**

***Authorization:***

`Bearer <access_token>`

**Response (200):**
```json
{
    "access_token": "<access_token>",
    "refresh_token": "<refresh_token>"
}
```

### 3. **Get User List**
**URL:**
`GET http://localhost:8080/api/usuarios`

**Headers:**

***Authorization:***

`Bearer <access_token>`

**Response (200):**
```json
[
    {
        "name": "usernameTest",
        "email": "user@email.com"
    }
]
```

### 4. **Refresh Token**
**URL:**
`POST http://localhost:8080/auth/refresh-token`

**Headers:**

***Authorization:***

`Bearer <refresh_token>`

**Response (200):**
```json
{
    "access_token": "<access_token>",
    "refresh_token": "<refresh_token>"
}
```

### 5. **Logout**
**URL:**
`POST http://localhost:8080/auth/logout`

**Headers:**

***Authorization:***

`Bearer <access_token>`

## Notes
- Ensure that all requests requiring authentication include the Authorization header with a valid JWT token.
- Tokens expire after a certain period; use the refresh token endpoint to obtain a new access token when needed.
- For testing, replace <access_token> and <refresh_token> with actual tokens from the API responses.

## Author

### Created By: [Maximiliano Sandoval](https://github.com/maxisandoval37) ☕
