# HRMS Console Project (Java Core + MySQL)

## Features
- Create organization and organization login
- Forgot password (demo flow)
- Employee Add / View / Update / Delete
- Attendance Create / View / Update / Delete
- Salary Create / View / Update / Delete
- Notice/Holiday Create / View / Update / Delete
- Logout returns to main menu
- Every record is scoped to the logged-in organization

## Setup in IntelliJ IDEA
1. Install/start MySQL Server and open MySQL Workbench.
2. Open `src/main/resources/schema.sql`, run the entire script.
3. Open `src/main/java/com/softmason/hrms/DB.java`.
4. Replace `YOUR_MYSQL_PASSWORD` with your MySQL root password. If your username differs, update `USER`.
5. Open the project folder in IntelliJ as a Maven project. Use JDK 17 or later.
6. Wait for Maven to download MySQL Connector/J.
7. Run `Main.java`.

## First run
Choose `1. Create New Organization`, enter organization details and create a password of at least 8 characters. Then choose login and use that email/password. The example email/password in the assignment are sample inputs only; they are not preloaded in the database.

## Important learning notes
- `PreparedStatement` safely passes user input into SQL and helps prevent SQL injection.
- `try-with-resources` closes database connections, statements, and result sets.
- `while` loops keep menus running; `switch` routes each option.
- `orgId` scopes records to the currently logged-in organization.
- Passwords are hashed with SHA-256 in this learning project, not stored as plain text. For a real production app, use a password-hashing algorithm such as Argon2id/bcrypt and a verified reset flow (email OTP/token). The demo forgot-password flow accepts email only and is not production-secure.
- This is a console learning project, not a production HR/payroll system. Add stronger validation, authorization, audit logging, backups, and privacy/security controls before real use.
