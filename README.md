# TapComic Reader Backend

A Spring Boot backend application providing RESTful APIs for a comic reading platform and a Thymeleaf-powered administrative web dashboard. The system supports JWT-based user authentication, comic and chapter management, social interactions (comments, replies, votes, friends), and role-based access control.

## Features

### Public & User APIs (`/api/**`)
* **Authentication:** Stateless JWT (JSON Web Tokens) implementation.
* **Comic:** Search by name, filter by genres, and fetch popular/recent releases.
* **Reading:** Fetch chapters and sequenced page images. Tracks user reading history.
* **Social:**
    * Add comments and replies to comics, chapters, or specific pages.
    * Upvote or downvote chapters, comments, and replies.
    * Add friends and manage user profiles.
    * Favorite comics to a personal library.
    * Report inappropriate comments for admin review.

### Admin Web Dashboard (`/admin/**`)
* **Server-Side Rendered UI:** Built with Thymeleaf and Bootstrap 5.
* **Authentication:** Secured via HTTP Basic Authentication using the `ADMIN` database role.
* **Management Capabilities:**
    * Create, edit, and delete comics and genres.
    * Upload chapter page images in sequence via multipart file uploads.
    * Review, approve, or reject user-reported comments.
    * Manage and remove users.

## Tech Stack

* **Framework:** Java / Spring Boot 3
* **Security:** Spring Security, io.jsonwebtoken (JJWT)
* **Persistence:** Spring Data JPA, Hibernate
* **Database:** PostgreSQL (utilizes `pg_trgm` for similarity searches)
* **Template Engine:** Thymeleaf
* **Frontend (Admin):** HTML5, Bootstrap 5

## Prerequisites

* Java 17 or higher
* Maven
* PostgreSQL installed and running

## Configuration

Configure your `application.properties` or `application.yml` file with the following required environment variables:

```properties
# Server configuration
server.port=8080

# PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/tapcomic_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT Configuration
app.secret_key=YOUR_BASE64_ENCODED_SECRET_KEY
app.jwt_expiration_ms=86400000
app.jwt_refresh_expiration_ms=2592000000

# File Upload Limits (Adjust as needed for chapter images)
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=100MB

# CORS Configuration
cors.allowed-origin=http://localhost:3000

# Security admin account configuration
spring.security.user.name=your_admin_username
spring.security.user.password=your_password_in_bcrypt
spring.security.user.roles=ADMIN,USER
```

*Note: Ensure the `pg_trgm` extension is enabled in your PostgreSQL database to support the similarity search queries:*
```sql
CREATE EXTENSION IF NOT EXISTS pg_trgm;
```

## Security Architecture

This application utilizes a dual `SecurityFilterChain` architecture to handle distinct client types:

1.  **API Chain (`/api/**`):** `@Order(1)`. Configured as strictly `STATELESS`. Validates requests using a custom `JwtAuthenticationFilter`.
2.  **Admin Web Chain (`/admin/**`):** `@Order(2)`. Configured to support standard browser sessions. Secured via `httpBasic()` requiring the `ADMIN` role.

## Running the Application

1.  Clone the repository.
2.  Configure your local database and update `application.properties`.
3.  Build the project using Maven:
    ```bash
    mvn clean install
    ```
4.  Run the application:
    ```bash
    mvn spring-boot:run
    ```

## Accessing the Admin Dashboard

1.  Ensure you have at least one user in your database or setup the security admin account with the `role` column set strictly to `ADMIN`.
2.  Navigate to `http://localhost:8080/admin` in your web browser.
3.  When prompted by the browser's native authentication pop-up, enter the username and password of the admin user.