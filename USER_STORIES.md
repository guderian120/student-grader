# Comprehensive User Stories for Java Grading Application

## Overview
This document contains comprehensive user stories for the grading application. User stories are written from the perspective of different user roles (Administrator, Lecturer, Student) and cover key functionalities while focusing on business value. Technical implementation details (such as using SQLite for testing, MongoDB for production, or JavaFX for UI) are requirements but not part of the user stories themselves. Each story follows the format: "As a [user], I want [goal] so that [benefit]."

## Administrator User Stories
### Student Management
- As an administrator, I want to add a student to the system so that I can maintain accurate student records for enrollment and tracking.
- As an administrator, I want to update student information (e.g., name, contact details, enrollment status) so that records stay current.
- As an administrator, I want to remove a student from the system so that I can handle withdrawals or housekeeping.

### Course Management
- As an administrator, I want to add a new course to the system so that lecturers can assign it to students and record grades.
- As an administrator, I want to update course details (e.g., name, description, schedule) so that information remains accurate.
- As an administrator, I want to remove a course from the system so that I can manage expired or discontinued courses.

### Lecturer Management
- As an administrator, I want to add a lecturer to the system so that they can be assigned to courses and manage grades.
- As an administrator, I want to update lecturer information (e.g., name, department, contact details) so that records are maintained.
- As an administrator, I want to remove a lecturer from the system so that I can handle resignations or reassignments.

### Administrative Features
- As an administrator, I want to view a dashboard with student, course, and lecturer statistics so that I can monitor system activity.
- As an administrator, I want to export reports of students, courses, and lecturers so that I can generate analytical data.

## Lecturer User Stories
### Grade Management
- As a lecturer, I want to add grades for students in my assigned courses so that I can record academic performance accurately.
- As a lecturer, I want to update existing grades for students so that I can correct errors or adjust for special circumstances.
- As a lecturer, I want to delete grades for students if necessary so that I can manage exceptional cases like withdrawals.
- As a lecturer, I want to view a list of all students in my courses so that I can manage my class effectively.

### Course Assignment
- As a lecturer, I want to view my assigned courses so that I know which courses I am responsible for.
- As a lecturer, I want to access student lists for each of my courses so that I can manage grades and communications.

### Grade Notification
- As a lecturer, I want to trigger an email alert to students when grades are uploaded so that they are notified of new results.
- As a lecturer, I want to update grades in real-time and notify students immediately so that they receive timely updates.

### Lecturer Tools
- As a lecturer, I want to filter and search students by name or ID so that I can quickly locate specific students.
- As a lecturer, I want to view grade history or logs for auditing purposes so that I can track changes and maintain accountability.

## Student User Stories
### Authentication and Access
- As a student, I want to log in to the grading application using my credentials so that I can securely access my personal information.
- As a student, I want to change my password securely so that I can maintain account security.

### Grade Viewing
- As a student, I want to view all my grades across my enrolled courses so that I can track my academic performance.
- As a student, I want to view detailed grade breakdowns for each course so that I can understand my scores per assignment or exam.
- As a student, I want to filter grades by course or semester so that I can focus on specific periods.

### Grade Requests and Reviews
- As a student, I want to request a remark on a specific grade so that potential errors in grading can be reviewed by a lecturer.
- As a student, I want to request a review of my overall grade in a course so that I can appeal decisions or seek clarifications.
- As a student, I want to view the status of my remark/review requests so that I can track progress and outcomes.

### Notifications
- As a student, I want to receive an email alert when new grades are uploaded so that I am promptly notified of results.
- As a student, I want to opt-in or opt-out of email notifications so that I can control my communication preferences.
- As a student, I want to view a notification history so that I can see past alerts and updates.

### Student Dashboard
- As a student, I want a personalized dashboard showing my current GPA and recent grades so that I can monitor my academic progress.
- As a student, I want to export my grades to a PDF or file so that I can keep personal records.

## Non-Functional and System User Stories
### System Reliability
- As a system administrator, I want the application to handle database errors gracefully so that user data is not corrupted during operations.
- As a user, I want the application to perform database operations efficiently so that grade management is fast even with large datasets.

### Security
- As a user, I want secure login mechanisms so that my personal and academic data is protected.
- As an administrator, I want role-based access controls so that only authorized users can perform specific actions.

### Usability
- As a user, I want an intuitive and visually appealing interface using JavaFX themes so that navigation and use are user-friendly.

### Data Management
- As a system administrator, I want to switch from SQLite (for testing) to MongoDB (for production) without rewriting the application logic so that deployment is flexible.
- As a system administrator, I want data backup and recovery options so that critical information is preserved in case of failures.

This set of user stories provides comprehensive coverage of the application's requirements, ensuring all key features are represented from the perspective of different user roles.
