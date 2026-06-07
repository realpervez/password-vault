# Password Vault 🔐

A secure, zero-knowledge password manager built with Java and Spring Boot. Passwords are encrypted with AES-256 before being stored — the server never has access to your plaintext credentials.

Built as a college cybersecurity project and portfolio piece.

---

## What it does

- Register and log in with a master password
- Store passwords for any website or service
- Passwords are AES-256 encrypted before hitting the database
- Master password is hashed with PBKDF2 — never stored in plaintext
- JWT-based authentication with secure HttpOnly cookies
- View, add, edit, and delete saved credentials

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4 |
| Security | Spring Security, JWT |
| Encryption | AES-256, PBKDF2 |
| Database | MySQL 8 |
| ORM | Hibernate / JPA |
| Build Tool | Maven |

---

## Architecture

```
[ Client / Browser ]
        ↓
  [ Controller ]     → handles HTTP requests
        ↓
  [ Service ]        → business logic + encryption
        ↓
  [ Repository ]     → database access
        ↓
  [ MySQL Database ] → stores only encrypted data
```

---

## Security Design

**Zero-Knowledge Architecture**
The server never stores or sees your plaintext passwords. Every password is encrypted with AES-256 before being saved to the database. Even if the database is fully compromised, an attacker only gets encrypted ciphertext.

**Master Password Hashing**
The master password is hashed using PBKDF2 with a unique per-user salt. It is never stored in plaintext. Even the developer cannot recover it.

**JWT Authentication**
Sessions are managed using JWT tokens stored in HttpOnly cookies — inaccessible to JavaScript, protecting against XSS attacks.

---

## Running Locally

### Prerequisites
- Java 17+
- MySQL 8
- Maven

### Setup

**1. Clone the repository**
```bash
git clone https://github.com/realpervez/password-vault.git
cd password-vault
```

**2. Create the database**
```sql
CREATE DATABASE passwordvault;
```

**3. Set environment variables**

The app reads database credentials from environment variables — never hardcoded.

```bash
export DB_USERNAME=your_mysql_username
export DB_PASSWORD=your_mysql_password
```

Or set them in your IDE run configuration (IntelliJ: Edit Configurations → Environment Variables).

**4. Run the app**
```bash
./mvnw spring-boot:run
```

Or run `PasswordVaultApplication.java` directly from IntelliJ.

**5. Open in browser**
```
http://localhost:8080
```

---

## Project Structure

```
src/main/java/com/pervez/password_vault/
├── PasswordVaultApplication.java   # Entry point
├── controller/                     # HTTP request handlers
├── service/                        # Business logic + encryption
├── repository/                     # Database queries
└── model/                          # Entity classes (DB tables)
```

---

## Environment Variables

| Variable | Description |
|---|---|
| `DB_USERNAME` | MySQL username |
| `DB_PASSWORD` | MySQL password |

---

## Status

🚧 **In active development** — built step by step with full understanding of each component.

| Feature | Status |
|---|---|
| Project setup + DB connection | ✅ Done |
| User entity + registration | ✅ Done |
| AES-256 encryption service | ✅ Done |
| Password vault CRUD | ✅ Done |
| REST API endpoints (Auth + Vault) | ✅ Done |
| JWT authentication | ⏳ Upcoming |
| Frontend UI | ⏳ Upcoming |

---

## Author

**Syed Pervez Mohiuddin**
[linkedin.com/in/realpervez](https://linkedin.com/in/realpervez) · [github.com/realpervez](https://github.com/realpervez)
