# Store & Inventory Management API

A REST API built with Java and Spring Boot for managing products, stock and customer orders. Stock is reserved when items are added to an order and restored when an order is cancelled. I built it as a student project to practice backend development with Spring Boot, JPA, MySQL and Docker.

> **Status:** work in progress. See the [Roadmap](#roadmap) for what is done and what is coming next.

## Tech stack

- **Language:** Java 17+
- **Framework:** Spring Boot, Spring Web, Spring Data JPA (Hibernate)
- **Database:** MySQL
- **Security:** Spring Security (registration and login)
- **Validation:** Jakarta Bean Validation
- **Mapping:** MapStruct
- **Build:** Maven
- **Planned:** Flyway, JUnit 5, Mockito, Testcontainers, Docker, GitHub Actions, Swagger/OpenAPI

## Features

**Implemented**
- User registration and login
- Product management (name, price, stock quantity, availability)
- Orders with order items
  - Price is saved at the time of purchase (`priceAtPurchase`), so later price changes do not affect old orders
  - Adding an item reserves stock
  - Cancelling an order restores stock
- Pagination on list endpoints
- DTOs and mappers between the API and the entities
- Custom exception handling (`ResourceNotFoundException`)

**In progress / planned**
- Stock movement history (an audit log of every stock change)
- Optimistic locking to prevent overselling when two requests arrive at the same time
- Low-stock alerts based on a reorder point
- Suppliers and purchase orders (receiving an order increases stock)
- Reports: inventory valuation, slow-moving stock, ABC classification
- Role-based access (Admin, Manager, Warehouse)
- CSV import for products

## Architecture

```
Controller  ->  Service  ->  Repository  ->  MySQL
   (DTOs)     (business       (Spring Data
               logic,          JPA)
               transactions)
```

- **Entities** contain their own business rules (for example `Product.reserveStock()` refuses to go below zero) and have no public setters for critical fields.
- **Services** are transactional, so an order and its stock changes succeed or fail together.
- **DTOs** keep the API separate from the database model.

### Main entities

- `User` (username, email, password hash, role)
- `Product` (name, current price, stock quantity, availability)
- `Order` (user, total price, items, status)
- `OrderItem` (product, quantity, price at purchase)

## Getting started

### Prerequisites

- Java 17 or newer
- Maven
- MySQL 8 (local install or Docker)

### 1. Start MySQL

With Docker:

```bash
docker run --name store-mysql \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=store \
  -p 3306:3306 \
  -d mysql:8
```

### 2. Configure the application

In `src/main/resources/application.properties` (or `application.yml`):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/store
spring.datasource.username=root
spring.datasource.password=rootpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

Do not commit real passwords. Use environment variables for anything beyond local development.

### 3. Run the app

```bash
mvn spring-boot:run
```

The API starts on `http://localhost:8080`.

### 4. Run the tests

```bash
mvn test
```

## Design decisions

- **Price snapshot:** `OrderItem` stores `priceAtPurchase` so historical orders stay correct.
- **Business rules in entities:** validation happens in constructors and domain methods, so invalid objects cannot be created.
- **No bidirectional `Product -> OrderItem` collection:** it would grow without limit and is rarely needed. Such data is loaded with a repository query instead.
- **Transactions at the service level:** stock reservation and order changes are committed together.

## Roadmap

- [x] User registration and login
- [x] Products, orders and order items
- [x] Reserve and restore stock
- [x] Pagination
- [ ] Optimistic locking (`@Version`)
- [ ] Stock movement history
- [ ] Low-stock alerts and reorder points
- [ ] Suppliers and purchase orders
- [ ] Reports (valuation, slow-moving, ABC)
- [ ] Role-based access control
- [ ] Flyway database migrations
- [ ] Unit and integration tests
- [ ] Swagger / OpenAPI documentation
- [x] Dockerfile and docker-compose
- [ ] CI with GitHub Actions
- [ ] Live deployment

## Author

**Abeer**
Software engineering student focused on Java backend development.
- LinkedIn: `https://www.linkedin.com/in/abeer-yanek-43536a333/`
