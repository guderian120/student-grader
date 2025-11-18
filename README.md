# Graded Application

A Java grading application with role-based access (Admin, Lecturer, Student), SQLite database, and JavaFX UI.

## Features
- User authentication (Admin, Lecturer, Student)
- SQLite database for data storage
- JavaFX UI with custom themes
- Role-based dashboards

## Prerequisites
- Java 17+
- JavaFX libraries
- SQLite JDBC driver

## Setup
1. Download JavaFX 17 SDK from GluonHQ: https://gluonhq.com/products/javafx/
2. Download SQLite JDBC jar: https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/sqlite-jdbc-3.36.0.3.jar
3. Place JavaFX jars and sqlite-jdbc.jar in a `lib` directory

## Compilation
```
javac -cp "lib/*;." -d bin src/com/grader/*.java
javac -cp "lib/*;." -d bin src/com/grader/dao/*.java
javac -cp "lib/*;." -d bin src/com/grader/model/*.java
```

## Running
```
java --module-path lib/javafx/lib --add-modules javafx.controls,javafx.fxml -cp "lib/sqlite-jdbc-3.36.0.3.jar;bin" com.grader.GradedApplication
```

## Default Credentials
- Admin: admin / admin
- Student: student / student
- Lecturer: lecturer / lecturer

## Project Structure
- src/com/grader/ - Main classes (Application.java)
- src/com/grader/model/ - Entity classes
- src/com/grader/dao/ - Data access objects
- src/com/grader/ - UI controllers

This is a basic implementation based on user stories. Further features can be added to each dashboard.
