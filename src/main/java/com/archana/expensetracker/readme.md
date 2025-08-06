# Expense Tracker

A backend application for managing shared group expenses and peer-to-peer settlements — built using **Spring Boot**, **JPA**, and **H2 Database**.

It supports group expenses, equal/exact/percentage splits, balance computation, and debt simplification.

---

## Features

- Create groups and add users
- Add expenses in a group or individually
- Supports:
    - Equal split
    - Exact amount split
    - Percentage-based split
- View balances for:
    - Each user
    - Each group
- Simplify and settle debts using optimized transactions

## Tech Stack

- Java 17

- Spring Boot

- Spring Data JPA

- H2 Database (in file mode for persistence)

- JUnit, Mockito (for testing)

## How to Run Locally

✅ Prerequisites

Java 17+

Maven

⚡ Clone and Run

- git clone https://github.com/pandeyarchana/expense-tracker.git
- cd expense-tracker
- mvn spring-boot:run
- App runs on: http://localhost:8080

🔧 Access H2 Console

- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:file:./data/expense-db
- Username: sa
- Password: (leave blank)

## REST API Endpoints

**User APIs**

POST /users
GET  /users/{id}

**Group APIs**

POST  /groups
GET  /groups/{id}
POST /{id}/users

**Expense APIs**

POST /expenses
GET  /expenses/group/{groupId}
GET  /expenses/user/{userId}

**Sample request:**

Add an Expense

POST http://localhost:8080/expenses
Content-Type: application/json

json
{
  "description": "Dinner",
  "amount": 1200,
  "paidBy": 1,
  "groupId": 1,
  "splitType": "EQUAL",
  "participants": [1, 2, 3]
}

Balance APIs

GET /balances/all                // User-wide net balances
GET /balances/group/{groupId}   // Group-wise balances
GET /balances/settle        `   // Simplified transactions

**Sample request:**

Get Balances

GET http://localhost:8080/balances/group/1

json
[
{
"userId": 1,
"amount": 0.0
},
{
"userId": 2,
"amount": -700.0
},
{
"userId": 3,
"amount": 700.0
}
]


## Running Tests
- mvn test
- Unit and integration tests are included for:

- ExpenseService
- BalanceService
- GroupService
- UserService
- SplitStrategyFactory

## Folder Structure

src/main/java/com/archana/expensetracker
|
|-- controller      # API Endpoints
|-- service         # Business Logic
|-- model           # JPA Entities
|-- dto             # Request & Response DTOs
|-- splitStrategy   # Strategy Pattern for Splitting
|-- repository      # Spring Data JPA Interfaces

## Access Swagger UI
http://localhost:8080/swagger-ui/index.html
