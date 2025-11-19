# Graded Application

A Java grading application with role-based access (Admin, Lecturer, Student), SQLite database, and JavaFX UI.

## Documentation
- **[Architecture & Design](ARCHITECTURE.md)**: System overview, database schema, and flow diagrams.
- **[Installation & Compilation](INSTALL.md)**: Step-by-step guide to build and run the application.

## Quick Start
1. **Compile**:
   ```powershell
   javac -d bin -cp "lib/sqlite-jdbc-3.36.0.3.jar;lib/javafx-sdk-17.0.2/lib/*" src/java/com/grader/*.java src/java/com/grader/dao/*.java src/java/com/grader/model/*.java
   ```
2. **Run**:
   ```powershell
   java -cp "bin;lib/sqlite-jdbc-3.36.0.3.jar" --module-path "lib/javafx-sdk-17.0.2/lib" --add-modules javafx.controls,javafx.fxml com.grader.GradedApplication
   ```

## Features
- **Role-based Access**: Admin, Lecturer, Student dashboards.
- **Data Persistence**: SQLite database.
- **Modern UI**: JavaFX with custom CSS styling.

## Default Credentials
- **Admin**: admin / admin
- **Student**: student / student
- **Lecturer**: lecturer / lecturer
