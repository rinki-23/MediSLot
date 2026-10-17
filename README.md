# MediSlot - Clinic Appointment & Patient Management System

MediSlot is a REST API built with Spring Boot that lets patients book appointments with doctors, and lets doctors manage their available time slots. It uses JWT authentication and role-based access control.

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA (Hibernate)
- Spring Security with JWT (jjwt)
- PostgreSQL
- Maven
- Tested with Postman

## Features

- Three roles: ADMIN, DOCTOR and PATIENT
- Registration and login with JWT tokens (stateless sessions)
- Passwords stored securely with BCrypt
- Role-based access control on every endpoint
- Ownership checks: a user can only view or change their own data
- Doctors can add and delete time slots (past dates are rejected)
- Patients can book and cancel appointments
- Global exception handling with a consistent JSON error format (400, 401, 403, 404)

## Getting Started

### Prerequisites

- Java 21
- PostgreSQL
- Maven

### Setup

1. Clone the repository:
   
   git clone https://github.com/rinki-23/MediSlot.git
   
2. Create a PostgreSQL database for the project.
3. Go to src/main/resources/, use application-local.properties
4. Open application-local.properties and fill in your own database password and a jwt.secret (at least 32 characters).
5. Run the application from your IDE, or with mvn spring-boot:run.


## Authentication

1. Register with POST /users/register.
2. Log in with POST /users/login and copy the token from the response.
3. Send the token with every protected request:


Authorization: Bearer <our-token>


Tokens expire after 1 hour.

## API Endpoints

| Method | URL | Access | Description |
|--------|-----|--------|-------------|
| POST | /users/register | Public | Register a new user |
| POST | /users/login | Public | Log in and get a JWT |
| GET | /users/getAll | ADMIN | List all users |
| POST | /doctor/profile/{id} | DOCTOR | Create doctor profile |
| GET | /doctor/getall | Authenticated | List all doctors |
| GET | /doctor/get/{id} | Authenticated | Get one doctor |
| POST | /patient/profile/{userId} | PATIENT | Create patient profile |
| POST | /slot/add/{id} | DOCTOR (owner) | Add a slot |
| DELETE | /slot/delete/{id} | DOCTOR (owner) | Delete a slot |
| GET | /slot/available/{id}/{date} | Authenticated | View available slots of a doctor |
| POST | /appointments/... | PATIENT (owner) | Book an appointment |
| PUT | /appointments/... | PATIENT or DOCTOR (owner) | Cancel an appointment |
| GET | /appointments/patient/{id} | PATIENT (owner) | View a patient's appointments |
| GET | /appointments/doctor/{id} | DOCTOR (owner) | View a doctor's appointments |

## Error Response Format

All errors return the same JSON shape:

json
{
  "status": 403,
  "error": "Forbidden",
  "message": "You are not allowed to access this data",
  "path": "/appointments/patient/5"
}


## Future Improvements

- Refresh tokens
- Pagination for list endpoints
- Swagger / OpenAPI documentation
- Unit and integration tests
- Email notifications for bookings

## Author

Rinki