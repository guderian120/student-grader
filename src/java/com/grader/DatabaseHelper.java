package com.grader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:grader.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTables() {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                role TEXT NOT NULL,
                email TEXT
            );
        """;

        String createStudentsTable = """
            CREATE TABLE IF NOT EXISTS students (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                name TEXT NOT NULL,
                student_id TEXT UNIQUE,
                email TEXT,
                FOREIGN KEY (user_id) REFERENCES users (id)
            );
        """;

        String createLecturersTable = """
            CREATE TABLE IF NOT EXISTS lecturers (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                name TEXT NOT NULL,
                lecturer_id TEXT UNIQUE,
                email TEXT,
                FOREIGN KEY (user_id) REFERENCES users (id)
            );
        """;

        String createCoursesTable = """
            CREATE TABLE IF NOT EXISTS courses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                course_code TEXT UNIQUE NOT NULL,
                name TEXT NOT NULL,
                description TEXT
            );
        """;

        String createGradesTable = """
            CREATE TABLE IF NOT EXISTS grades (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                student_id INTEGER,
                course_id INTEGER,
                lecturer_id INTEGER,
                grade REAL,
                remark TEXT,
                mid_sem_score REAL,
                exam_score REAL,
                attendance_count INTEGER,
                total_attendance INTEGER,
                FOREIGN KEY (student_id) REFERENCES students (id),
                FOREIGN KEY (course_id) REFERENCES courses (id),
                FOREIGN KEY (lecturer_id) REFERENCES lecturers (id)
            );
        """;

        String createRequestsTable = """
            CREATE TABLE IF NOT EXISTS requests (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                student_id INTEGER,
                grade_id INTEGER,
                request_type TEXT,  -- 'remark' or 'review'
                status TEXT DEFAULT 'pending',  -- 'pending', 'approved', 'rejected'
                FOREIGN KEY (student_id) REFERENCES students (id),
                FOREIGN KEY (grade_id) REFERENCES grades (id)
            );
        """;

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createStudentsTable);
            stmt.execute(createLecturersTable);
            stmt.execute(createCoursesTable);
            stmt.execute(createGradesTable);
            stmt.execute(createRequestsTable);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
}
