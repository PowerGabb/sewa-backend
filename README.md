# Sewa Kamera Backend API

Backend REST API untuk aplikasi Sewa Kamera yang dibangun dengan Spring Boot dan MySQL.

## Fitur

- ✅ User Registration
- ✅ User Login dengan JWT Authentication
- ✅ Password Hashing dengan BCrypt
- ✅ CORS Configuration untuk Mobile App
- ✅ Input Validation
- ✅ Error Handling

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security**
- **Spring Data JPA**
- **MySQL 8.0**
- **JWT (JSON Web Token)**
- **Maven**

## Prerequisites

- Java 17 atau lebih tinggi
- MySQL 8.0
- Maven 3.6+

## Setup Database

1. Buat database MySQL:
```sql
CREATE DATABASE sewa_kamera;
```

2. Update konfigurasi database di `src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Menjalankan Aplikasi

1. Clone repository
2. Navigate ke folder sewa-backend
3. Install dependencies:
```bash
mvn clean install
```

4. Jalankan aplikasi:
```bash
mvn spring-boot:run
```

Aplikasi akan berjalan di `http://localhost:8080`

## API Endpoints

### Authentication

#### Register User
```
POST /api/auth/register
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john@example.com",
  "phone": "08123456789",
  "password": "password123",
  "confirmPassword": "password123"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Registrasi berhasil",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "user": {
      "id": 1,
      "fullName": "John Doe",
      "email": "john@example.com",
      "phone": "08123456789"
    }
  }
}
```

#### Login User
```
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Login berhasil",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "user": {
      "id": 1,
      "fullName": "John Doe",
      "email": "john@example.com",
      "phone": "08123456789"
    }
  }
}
```

#### Check Email Availability
```
GET /api/auth/check-email?email=john@example.com
```

#### Check Phone Availability
```
GET /api/auth/check-phone?phone=08123456789
```

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Security

- Password di-hash menggunakan BCrypt
- JWT token untuk authentication
- CORS dikonfigurasi untuk mobile app
- Input validation pada semua endpoint

## Error Handling

Semua error response menggunakan format:
```json
{
  "success": false,
  "message": "Error message"
}
```

## Development

Untuk development, aplikasi menggunakan:
- Auto DDL update untuk database schema
- SQL logging untuk debugging
- Debug logging untuk security

## Production Notes

Sebelum deploy ke production:
1. Ganti `jwt.secret` dengan secret key yang kuat
2. Set `spring.jpa.hibernate.ddl-auto=validate`
3. Disable SQL logging
4. Konfigurasi CORS sesuai domain production