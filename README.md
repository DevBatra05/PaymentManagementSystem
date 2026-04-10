# SmartPaySystem — Payment Management System
### Java | Swing GUI | JDBC | MySQL | OOP

A secure digital payment application built using Java and Object-Oriented Programming principles. Features real-time transaction processing, user authentication, refund management, and transaction history — all through an intuitive Swing-based GUI connected to a MySQL backend via JDBC.

---

## Demo Screenshots

| Login | Dashboard | Payment |
|---|---|---|
| Email + Password auth | Balance, transactions overview | Send payment to recipient |

| Transaction History | Refund Request | Profile Settings |
|---|---|---|
| Filter by type/amount | Select failed transaction | Update name, email, password |

---

## Features

- **Secure Authentication** — Login and registration with email/password validation
- **Real-time Payments** — Send money to any registered user by email
- **Balance Management** — Automatic debit/credit on successful transactions
- **Transaction History** — Filter logs by type, min/max amount
- **Refund System** — Request refunds on failed transactions
- **Profile Settings** — Update personal info and password
- **Admin Panel** — Monitor and manage all transactions
- **DAO Pattern** — Clean separation of database logic via UserDAO, TransactionDAO

---

## Tech Stack

| Category | Technology |
|---|---|
| Language | Java |
| GUI | Java Swing (JFrame, JPanel, JButton) |
| Database | MySQL |
| DB Connectivity | JDBC (PreparedStatement API) |
| IDE | VS Code / IntelliJ IDEA |
| Version Control | Git + GitHub |

---

## Project Structure

```
SmartPaySystem/
├── src/
│   ├── LoginPage.java          # Login + Registration UI
│   ├── Dashboard.java          # Main dashboard with navigation
│   ├── PaymentPage.java        # Send payment to recipient
│   ├── TransactionHistory.java # View and filter transaction logs
│   ├── RefundRequest.java      # Request refund on failed payments
│   ├── ProfileSettings.java    # Update user profile
│   ├── DatabaseManager.java    # JDBC connection manager
│   ├── UserDAO.java            # User database operations
│   └── TransactionDAO.java     # Transaction database operations
├── lib/                        # External JARs (MySQL connector)
├── out/                        # Compiled .class files
├── SmartPaySystem.jar          # Executable JAR
└── README.md
```

---

## Architecture

```
┌─────────────────────────────────────────┐
│              GUI Layer                  │
│  LoginPage → Dashboard → PaymentPage   │
│  TransactionHistory, Refund, Profile   │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│              DAO Layer                  │
│      UserDAO    TransactionDAO          │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│         DatabaseManager (JDBC)          │
│     jdbc:mysql://localhost:3306/        │
│            smartpay_db                  │
└─────────────────────────────────────────┘
```

---

## Database Schema

```sql
CREATE TABLE users (
    user_id   INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(100),
    email     VARCHAR(100) UNIQUE,
    password  VARCHAR(100),
    balance   DOUBLE DEFAULT 0.0,
    role      VARCHAR(20)
);

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id        INT,
    amount         DOUBLE,
    description    VARCHAR(255),
    status         VARCHAR(20),
    timestamp      DATETIME DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

---

## Setup & Running

### Prerequisites
- Java JDK 17+
- MySQL Server
- MySQL JDBC Connector JAR

### Step 1 — Clone the repo
```bash
git clone https://github.com/DevBatra05/PaymentManagementSystem.git
cd PaymentManagementSystem
```

### Step 2 — Set up the database
```sql
CREATE DATABASE smartpay_db;
USE smartpay_db;
-- Run the schema queries above
```

### Step 3 — Update DB credentials in DatabaseManager.java
```java
private static final String URL      = "jdbc:mysql://localhost:3306/smartpay_db";
private static final String USER     = "your_mysql_username";
private static final String PASSWORD = "your_mysql_password";
```

### Step 4 — Run the JAR directly
```bash
java -jar SmartPaySystem.jar
```

### Or compile and run from source
```bash
javac -cp lib/mysql-connector.jar src/*.java -d out/
java -cp out/:lib/mysql-connector.jar LoginPage
```

---

## OOP Concepts Used

| Concept | Where Applied |
|---|---|
| **Encapsulation** | Private fields in User, Transaction, Payment classes |
| **Inheritance** | All GUI frames extend JFrame |
| **Abstraction** | PaymentMethod abstract class with CreditCard, BankTransfer subclasses |
| **Polymorphism** | authorizePayment() behaves differently per payment method |
| **DAO Pattern** | UserDAO, TransactionDAO separate DB logic from UI |

---

## UML Class Diagram

Key classes and relationships:

```
User ──────────── Payment ──────────── Transaction
 │                    │                     │
 │                    │                     └── Report
 │               PaymentMethod (Abstract)
 │                 /         \
 │          CreditCard    BankTransfer
 │
 └── UserDAO, TransactionDAO (DB operations)
```

---

## Team

| Name | Role |
|---|---|
| **Dev Batra** | Frontend Developer — All GUI interfaces (Login, Dashboard, Payment, Transactions, Refund, Profile) |
| Divyansh Rana | Backend Development |
| Rishabh Bhardwaj | Database Design & JDBC Integration |
| Nayan Bansal | Testing & Documentation |

**Course:** B.Tech CSE 4th Semester — Object-Oriented Programming (2024–25), UPES

---

## Future Enhancements

- Blockchain-based transaction verification
- AI-driven fraud detection
- Mobile app version
- Email notifications on payment success/failure
