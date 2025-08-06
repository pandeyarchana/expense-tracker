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


1.  git clone https://github.com/your-username/expense-tracker.git
cd expense-tracker
2. Build the project
./mvnw clean install
3. Run the application
./mvnw spring-boot:run
App runs on: http://localhost:8080

🔗 H2 Console
For database inspection:

URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:file:./data/demo

Username: sa

Password: (leave blank)

API Endpoints:
User APIs

POST /users
GET  /users/{id}

Group APIs

POST  /groups
GET  /groups/{id}
POST /{id}/users

Expense APIs

POST /expenses
GET  /expenses/group/{groupId}
GET  /expenses/user/{userId}

Sample request:

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

Running Tests
./mvnw test
Unit and integration tests are included for:

ExpenseService

BalanceService

Controllers

Technologies Used
Spring Boot 3.x

Java 17+

H2 Database (file-based persistence)

Spring Data JPA

JUnit + Mockito (testing)