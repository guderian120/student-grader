# System Architecture

## Overview
The Graded Application is a desktop-based grading system built using **Java** and **JavaFX** for the frontend, with **SQLite** as the local database. It follows a layered architecture separating the UI, Data Access, and Model layers.

## Architectural Pattern
The application uses a **DAO (Data Access Object)** pattern to separate business logic from data persistence.

![Architectural Diagram](media/architectural_digram_grader.png)

## Class Structure

### Core Components
- **GradedApplication**: Main entry point and UI controller. Manages scene transitions and dashboard logic.
- **DatabaseHelper**: Manages database connection and table creation.

### Models
- **User**: Base entity for authentication (Roles: ADMIN, STUDENT, LECTURER).
- **Student**: Extends user info, links to `users` table.
- **Lecturer**: Extends user info, links to `users` table.
- **Course**: Represents a subject/course.
- **Grade**: Links Student, Course, and Lecturer with score data.
- **Request**: Represents student requests (e.g., remarking).

### DAOs
- **UserDAO**: Handles authentication and user management.
- **StudentDAO / LecturerDAO**: Manage specific profile data.
- **CourseDAO**: Manages course catalog.
- **GradeDAO**: Handles grade recording and retrieval.
- **RequestDAO**: Manages workflow for grade disputes/requests.

## Database Schema

```mermaid
erDiagram
    USERS ||--o| STUDENTS : "has profile"
    USERS ||--o| LECTURERS : "has profile"
    STUDENTS ||--o{ GRADES : "receives"
    COURSES ||--o{ GRADES : "subject of"
    LECTURERS ||--o{ GRADES : "assigns"
    STUDENTS ||--o{ REQUESTS : "makes"
    GRADES ||--o{ REQUESTS : "related to"

    USERS {
        int id PK
        string username
        string password
        string role
        string email
    }

    STUDENTS {
        int id PK
        int user_id FK
        string name
        string student_id
    }

    LECTURERS {
        int id PK
        int user_id FK
        string name
        string lecturer_id
    }

    COURSES {
        int id PK
        string course_code
        string name
        string description
    }

    GRADES {
        int id PK
        int student_id FK
        int course_id FK
        int lecturer_id FK
        double grade
        string remark
    }

    REQUESTS {
        int id PK
        int student_id FK
        int grade_id FK
        string request_type
        string status
    }
```

## Application Flows

![Flowchart](media/flowchart_grader.png)
