# Game Renting Service

A RESTful web service for managing a game renting system.

## Features

* Add a game
* Remove a game
* Loan a game to a member
* Return a game
* Ensure only one copy of a game exists based on the combination of title and studio
* Ensure a game can only be loaned to one member at a time
* Limit members to a maximum of 3 active game loans

## Technology Stack

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* H2 Database
* Maven
* Lombok

## API Endpoints

| Method | Endpoint                 | Description    |
| ------ | ------------------------ | -------------- |
| POST   | `/games`                 | Add a new game |
| DELETE | `/games/{gameId}`        | Remove a game  |
| POST   | `/games/{gameId}/loan`   | Loan a game    |
| POST   | `/games/{gameId}/return` | Return a game  |

### Member Identification

For this assignment, authentication is stubbed using the `X-Member-Id` request header.

Example:

```text
X-Member-Id: 101
```

---

# Scaling and Data Integrity

The current implementation uses an embedded H2 database, which is suitable for the scope of this take-home assignment.

For a production system handling high traffic, I would move to a scalable relational database such as PostgreSQL or MySQL.

The application could be scaled horizontally by running multiple instances behind a load balancer. The application should remain stateless so that any request can be handled by any instance.

To ensure data integrity under high concurrent load:

* Use database-level unique constraints for `(title, studio)` to prevent duplicate games.
* Use transactions for loan and return operations.
* Use database locking or optimistic locking to prevent multiple members from loaning the same game simultaneously.
* Enforce the maximum of three active loans per member within a transaction.
* Add appropriate database indexes for frequently queried fields.
* Use connection pooling to efficiently manage database connections.
* For very high traffic, consider caching read-heavy data while keeping loan operations strongly consistent through the database.

The database should be considered the final source of truth for enforcing critical business rules.

---

# AI Coding Assistant Usage

An AI coding assistant was used as a development aid during this project.

It was used to help:

* Generate the initial project structure.
* Suggest Spring Boot configuration and dependencies.
* Draft entity, repository, DTO, service, controller, and exception handling code.
* Explain Spring Boot and JPA concepts.
* Suggest API testing scenarios.

All generated code was reviewed and integrated manually. The final implementation was checked against the assignment requirements, including:

* Game uniqueness based on title and studio.
* A maximum of three active loans per member.
* Preventing the same game from being loaned to multiple members at the same time.
* Proper validation and exception handling.

AI-generated suggestions were not accepted blindly. The code and project structure were reviewed and adjusted to match the requirements of the assignment.

---

# Running the Application

## Prerequisites

* Java 17
* Maven

## Run

Clone the repository and run:

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

# H2 Database Console

The H2 console is available at:

```text
http://localhost:8080/h2-console
```

Use the following configuration:

```text
JDBC URL: jdbc:h2:mem:gamedb
Username: sa
Password:
```

---

# Example API Request

## Add Game

### Request

```text
POST /games
```

```json
{
  "title": "GTA V",
  "studio": "Rockstar Games",
  "genres": [
    "Action",
    "Adventure"
  ]
}
```

## Loan Game

```text
POST /games/1/loan
```

Header:

```text
X-Member-Id: 101
```

## Return Game

```text
POST /games/1/return
```

Header:

```text
X-Member-Id: 101
```
