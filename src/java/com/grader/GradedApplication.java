package com.grader;

import com.grader.dao.*;
import com.grader.model.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GradedApplication extends Application {

    private Stage primaryStage;
    private User loggedInUser;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginScreen();
        primaryStage.setTitle("Graded Application");
        primaryStage.show();
    }

    private void showLoginScreen() {
        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label titleLabel = new Label("Graded Application Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");

        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            loggedInUser = UserDAO.authenticate(username, password);
            if (loggedInUser != null) {
                switch (loggedInUser.getRole()) {
                    case ADMIN: showAdminDashboard(); break;
                    case STUDENT: showStudentDashboard(); break;
                    case LECTURER: showLecturerDashboard(); break;
                }
            } else {
                messageLabel.setText("Invalid credentials");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        root.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, messageLabel);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
    }

    private void showAdminDashboard() {
        VBox root = new VBox(10);
        root.setSpacing(20);
        root.setStyle("-fx-padding: 20;");

        Label titleLabel = new Label("Admin Dashboard - Logged in as " + loggedInUser.getUsername());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        HBox buttonBox = new HBox(10);
        Button manageStudentsBtn = new Button("Manage Students");
        Button manageLecturersBtn = new Button("Manage Lecturers");
        Button manageCoursesBtn = new Button("Manage Courses");
        Button logoutButton = new Button("Logout");

        buttonBox.getChildren().addAll(manageStudentsBtn, manageLecturersBtn, manageCoursesBtn);

        manageStudentsBtn.setOnAction(e -> showManageStudents());
        manageLecturersBtn.setOnAction(e -> showManageLecturers());
        manageCoursesBtn.setOnAction(e -> showManageCourses());
        logoutButton.setOnAction(e -> showLoginScreen());

        root.getChildren().addAll(titleLabel, buttonBox, logoutButton);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
    }

    private void showStudentDashboard() {
        VBox root = new VBox(10);
        root.setSpacing(20);
        root.setStyle("-fx-padding: 20;");

        Label titleLabel = new Label("Student Dashboard - Logged in as " + loggedInUser.getUsername());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        HBox buttonBox = new HBox(10);
        Button viewGradesBtn = new Button("View Grades");
        Button makeRequestBtn = new Button("Make Request");
        Button viewRequestsBtn = new Button("View My Requests");
        Button logoutButton = new Button("Logout");

        buttonBox.getChildren().addAll(viewGradesBtn, makeRequestBtn, viewRequestsBtn);

        viewGradesBtn.setOnAction(e -> showViewGrades());
        makeRequestBtn.setOnAction(e -> showMakeRequest());
        viewRequestsBtn.setOnAction(e -> showViewMyRequests());
        logoutButton.setOnAction(e -> showLoginScreen());

        root.getChildren().addAll(titleLabel, buttonBox, logoutButton);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
    }

    private void showLecturerDashboard() {
        VBox root = new VBox(10);
        root.setSpacing(20);
        root.setStyle("-fx-padding: 20;");

        Label titleLabel = new Label("Lecturer Dashboard - Logged in as " + loggedInUser.getUsername());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        HBox buttonBox = new HBox(10);
        Button manageGradesBtn = new Button("Manage Grades");
        Button viewRequestsBtn = new Button("View Requests");
        Button logoutButton = new Button("Logout");

        buttonBox.getChildren().addAll(manageGradesBtn, viewRequestsBtn);

        manageGradesBtn.setOnAction(e -> showManageGrades());
        viewRequestsBtn.setOnAction(e -> showViewRequests());
        logoutButton.setOnAction(e -> showLoginScreen());

        root.getChildren().addAll(titleLabel, buttonBox, logoutButton);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
    }

    // Admin Management Methods
    private void showManageStudents() {
        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Manage Students");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView<Student> table = new TableView<>();
        table.setItems(javafx.collections.FXCollections.observableArrayList(StudentDAO.getAllStudents()));

        TableColumn<Student, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> studentIdCol = new TableColumn<>("Student ID");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.getColumns().addAll(idCol, nameCol, studentIdCol, emailCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox buttonBox = new HBox(10);
        Button addBtn = new Button("Add Student");
        Button editBtn = new Button("Edit Selected");
        Button deleteBtn = new Button("Delete Selected");
        Button backBtn = new Button("Back");

        buttonBox.getChildren().addAll(addBtn, editBtn, deleteBtn, backBtn);

        table.setPrefHeight(300);

        addBtn.setOnAction(e -> showAddEditStudent(null)); // null for add
        editBtn.setOnAction(e -> {
            Student selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showAddEditStudent(selected);
            }
        });
        deleteBtn.setOnAction(e -> {
            Student selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                StudentDAO.deleteStudent(selected.getId());
                table.setItems(javafx.collections.FXCollections.observableArrayList(StudentDAO.getAllStudents()));
                UserDAO.deleteUser(selected.getUserId()); // also delete user
            }
        });
        backBtn.setOnAction(e -> showAdminDashboard());

        root.getChildren().addAll(titleLabel, table, buttonBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }

    private void showAddEditStudent(Student student) {
        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label(student == null ? "Add Student" : "Edit Student");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField studentIdField = new TextField();
        studentIdField.setPromptText("Student ID");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        if (student != null) {
            // Populate for edit
            User user = UserDAO.getAllUsers().stream().filter(u -> u.getId() == student.getUserId()).findFirst().orElse(null);
            if (user != null) {
                usernameField.setText(user.getUsername());
                passwordField.setText(user.getPassword());
                nameField.setText(student.getName());
                studentIdField.setText(student.getStudentId());
                emailField.setText(student.getEmail());
            }
        }

        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");

        saveBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String name = nameField.getText();
            String studentId = studentIdField.getText();
            String email = emailField.getText();

            if (student == null) {
                // Add new
                User newUser = new User(0, username, password, User.Role.STUDENT, email);
                if (UserDAO.addUser(newUser)) {
                    User addedUser = UserDAO.authenticate(username, password);
                    Student newStudent = new Student(0, addedUser.getId(), name, studentId, email);
                    StudentDAO.addStudent(newStudent);
                }
            } else {
                // Update
                User user = new User(student.getUserId(), username, password, User.Role.STUDENT, email);
                UserDAO.updateUser(user);
                Student updatedStudent = new Student(student.getId(), student.getUserId(), name, studentId, email);
                StudentDAO.updateStudent(updatedStudent);
            }
            showManageStudents(); // Refresh list
        });
        cancelBtn.setOnAction(e -> showManageStudents());

        HBox buttonBox = new HBox(10, saveBtn, cancelBtn);

        root.getChildren().addAll(titleLabel, new Label("Username:"), usernameField, new Label("Password:"), passwordField,
                                  new Label("Name:"), nameField, new Label("Student ID:"), studentIdField,
                                  new Label("Email:"), emailField, buttonBox);

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
    }

    private void showManageLecturers() {
        // Similar to showManageStudents, but for lecturers
        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Manage Lecturers");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView<Lecturer> table = new TableView<>();
        table.setItems(javafx.collections.FXCollections.observableArrayList(LecturerDAO.getAllLecturers()));

        TableColumn<Lecturer, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Lecturer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Lecturer, String> lecturerIdCol = new TableColumn<>("Lecturer ID");
        lecturerIdCol.setCellValueFactory(new PropertyValueFactory<>("lecturerId"));

        TableColumn<Lecturer, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.getColumns().addAll(idCol, nameCol, lecturerIdCol, emailCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox buttonBox = new HBox(10);
        Button addBtn = new Button("Add Lecturer");
        Button editBtn = new Button("Edit Selected");
        Button deleteBtn = new Button("Delete Selected");
        Button backBtn = new Button("Back");

        buttonBox.getChildren().addAll(addBtn, editBtn, deleteBtn, backBtn);

        table.setPrefHeight(300);

        addBtn.setOnAction(e -> showAddEditLecturer(null));
        editBtn.setOnAction(e -> {
            Lecturer selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showAddEditLecturer(selected);
            }
        });
        deleteBtn.setOnAction(e -> {
            Lecturer selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                LecturerDAO.deleteLecturer(selected.getId());
                table.setItems(javafx.collections.FXCollections.observableArrayList(LecturerDAO.getAllLecturers()));
                UserDAO.deleteUser(selected.getUserId());
            }
        });
        backBtn.setOnAction(e -> showAdminDashboard());

        root.getChildren().addAll(titleLabel, table, buttonBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }

    private void showAddEditLecturer(Lecturer lecturer) {
        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label(lecturer == null ? "Add Lecturer" : "Edit Lecturer");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField lecturerIdField = new TextField();
        lecturerIdField.setPromptText("Lecturer ID");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        if (lecturer != null) {
            User user = UserDAO.getAllUsers().stream().filter(u -> u.getId() == lecturer.getUserId()).findFirst().orElse(null);
            if (user != null) {
                usernameField.setText(user.getUsername());
                passwordField.setText(user.getPassword());
                nameField.setText(lecturer.getName());
                lecturerIdField.setText(lecturer.getLecturerId());
                emailField.setText(lecturer.getEmail());
            }
        }

        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");

        saveBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String name = nameField.getText();
            String lecturerId = lecturerIdField.getText();
            String email = emailField.getText();

            if (lecturer == null) {
                User newUser = new User(0, username, password, User.Role.LECTURER, email);
                if (UserDAO.addUser(newUser)) {
                    User addedUser = UserDAO.authenticate(username, password);
                    Lecturer newLecturer = new Lecturer(0, addedUser.getId(), name, lecturerId, email);
                    LecturerDAO.addLecturer(newLecturer);
                }
            } else {
                User user = new User(lecturer.getUserId(), username, password, User.Role.LECTURER, email);
                UserDAO.updateUser(user);
                Lecturer updatedLecturer = new Lecturer(lecturer.getId(), lecturer.getUserId(), name, lecturerId, email);
                LecturerDAO.updateLecturer(updatedLecturer);
            }
            showManageLecturers();
        });
        cancelBtn.setOnAction(e -> showManageLecturers());

        HBox buttonBox = new HBox(10, saveBtn, cancelBtn);

        root.getChildren().addAll(titleLabel, new Label("Username:"), usernameField, new Label("Password:"), passwordField,
                                  new Label("Name:"), nameField, new Label("Lecturer ID:"), lecturerIdField,
                                  new Label("Email:"), emailField, buttonBox);

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
    }

    private void showManageCourses() {
        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Manage Courses");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView<Course> table = new TableView<>();
        table.setItems(javafx.collections.FXCollections.observableArrayList(CourseDAO.getAllCourses()));

        TableColumn<Course, String> courseCodeCol = new TableColumn<>("Course Code");
        courseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));

        TableColumn<Course, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Course, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().addAll(courseCodeCol, nameCol, descriptionCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox buttonBox = new HBox(10);
        Button addBtn = new Button("Add Course");
        Button editBtn = new Button("Edit Selected");
        Button deleteBtn = new Button("Delete Selected");
        Button backBtn = new Button("Back");

        buttonBox.getChildren().addAll(addBtn, editBtn, deleteBtn, backBtn);

        table.setPrefHeight(300);

        addBtn.setOnAction(e -> showAddEditCourse(null));
        editBtn.setOnAction(e -> {
            Course selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showAddEditCourse(selected);
            }
        });
        deleteBtn.setOnAction(e -> {
            Course selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                CourseDAO.deleteCourse(selected.getId());
                table.setItems(javafx.collections.FXCollections.observableArrayList(CourseDAO.getAllCourses()));
            }
        });
        backBtn.setOnAction(e -> showAdminDashboard());

        root.getChildren().addAll(titleLabel, table, buttonBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }

    private void showAddEditCourse(Course course) {
        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label(course == null ? "Add Course" : "Edit Course");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField courseCodeField = new TextField();
        courseCodeField.setPromptText("Course Code");
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");
        descriptionArea.setPrefRowCount(3);

        if (course != null) {
            courseCodeField.setText(course.getCourseCode());
            nameField.setText(course.getName());
            descriptionArea.setText(course.getDescription());
        }

        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");

        saveBtn.setOnAction(e -> {
            String courseCode = courseCodeField.getText();
            String name = nameField.getText();
            String description = descriptionArea.getText();

            if (course == null) {
                Course newCourse = new Course(0, courseCode, name, description);
                CourseDAO.addCourse(newCourse);
            } else {
                Course updatedCourse = new Course(course.getId(), courseCode, name, description);
                CourseDAO.updateCourse(updatedCourse);
            }
            showManageCourses();
        });
        cancelBtn.setOnAction(e -> showManageCourses());

        HBox buttonBox = new HBox(10, saveBtn, cancelBtn);

        root.getChildren().addAll(titleLabel, new Label("Course Code:"), courseCodeField,
                                  new Label("Name:"), nameField, new Label("Description:"), descriptionArea, buttonBox);

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
    }

    // Helper classes for UI
    private static class GradeInfo {
        private final Grade grade;
        private final String studentName;
        private final String courseName;

        public GradeInfo(Grade grade, String studentName, String courseName) {
            this.grade = grade;
            this.studentName = studentName;
            this.courseName = courseName;
        }

        public String getStudentName() { return studentName; }
        public String getCourseName() { return courseName; }
        public double getGradeValue() { return grade.getGrade(); }
        public String getRemark() { return grade.getRemark(); }
    }

    private static class RequestInfo {
        private final Request request;
        private final String studentName;
        private final String courseName;

        public RequestInfo(Request request, String studentName, String courseName) {
            this.request = request;
            this.studentName = studentName;
            this.courseName = courseName;
        }

        public Request getRequest() { return request; }
        public String getStudentName() { return studentName; }
        public String getCourseName() { return courseName; }
    }

    // Lecturer Methods
    private void showManageGrades() {
        Lecturer lecturer = LecturerDAO.getLecturerByUserId(loggedInUser.getId());
        if (lecturer == null) return; // Error, but for simplicity

        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Manage Grades");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView<GradeInfo> table = new TableView<>();
        table.setItems(javafx.collections.FXCollections.observableArrayList(getGradeInfos(lecturer.getId())));

        TableColumn<GradeInfo, String> studentNameCol = new TableColumn<>("Student Name");
        studentNameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStudentName()));

        TableColumn<GradeInfo, String> courseNameCol = new TableColumn<>("Course Name");
        courseNameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCourseName()));

        TableColumn<GradeInfo, Double> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getGradeValue()));

        TableColumn<GradeInfo, String> remarkCol = new TableColumn<>("Remark");
        remarkCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRemark()));

        table.getColumns().addAll(studentNameCol, courseNameCol, gradeCol, remarkCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox buttonBox = new HBox(10);
        Button addBtn = new Button("Add Grade");
        Button editBtn = new Button("Edit Selected");
        Button deleteBtn = new Button("Delete Selected");
        Button backBtn = new Button("Back");

        buttonBox.getChildren().addAll(addBtn, editBtn, deleteBtn, backBtn);

        table.setPrefHeight(300);

        addBtn.setOnAction(e -> showAddEditGrade(null));
        editBtn.setOnAction(e -> {
            GradeInfo selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showAddEditGrade(selected.grade);
            }
        });
        deleteBtn.setOnAction(e -> {
            GradeInfo selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                GradeDAO.deleteGrade(selected.grade.getId());
                table.setItems(javafx.collections.FXCollections.observableArrayList(getGradeInfos(lecturer.getId())));
            }
        });
        backBtn.setOnAction(e -> showLecturerDashboard());

        root.getChildren().addAll(titleLabel, table, buttonBox);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
    }

    private void showAddEditGrade(Grade grade) {
        Lecturer lecturer = LecturerDAO.getLecturerByUserId(loggedInUser.getId());
        if (lecturer == null) return;

        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label(grade == null ? "Add Grade" : "Edit Grade");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ComboBox<Student> studentCombo = new ComboBox<>();
        studentCombo.setItems(javafx.collections.FXCollections.observableArrayList(StudentDAO.getAllStudents()));
        studentCombo.setPromptText("Select Student");

        ComboBox<Course> courseCombo = new ComboBox<>();
        courseCombo.setItems(javafx.collections.FXCollections.observableArrayList(CourseDAO.getAllCourses()));
        courseCombo.setPromptText("Select Course");

        TextField gradeField = new TextField();
        gradeField.setPromptText("Grade (e.g., 85.5)");
        TextArea remarkArea = new TextArea();
        remarkArea.setPromptText("Remark");
        remarkArea.setPrefRowCount(3);

        if (grade != null) {
            Student student = StudentDAO.getStudentById(grade.getStudentId());
            if (student != null) studentCombo.setValue(student);
            Course course = CourseDAO.getCourseById(grade.getCourseId());
            if (course != null) courseCombo.setValue(course);
            gradeField.setText(String.valueOf(grade.getGrade()));
            remarkArea.setText(grade.getRemark());
        }

        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");

        saveBtn.setOnAction(e -> {
            Student selectedStudent = studentCombo.getValue();
            Course selectedCourse = courseCombo.getValue();
            double grd;
            try {
                grd = Double.parseDouble(gradeField.getText());
            } catch (NumberFormatException ex) {
                return; // Error, but ignore for simplicity
            }
            String rmk = remarkArea.getText();

            if (selectedStudent != null && selectedCourse != null) {
                if (grade == null) {
                    Grade newGrade = new Grade(0, selectedStudent.getId(), selectedCourse.getId(), lecturer.getId(), grd, rmk);
                    GradeDAO.addGrade(newGrade);
                } else {
                    Grade updatedGrade = new Grade(grade.getId(), selectedStudent.getId(), selectedCourse.getId(), lecturer.getId(), grd, rmk);
                    GradeDAO.updateGrade(updatedGrade);
                }
                showManageGrades();
            }
        });
        cancelBtn.setOnAction(e -> showManageGrades());

        HBox buttonBox = new HBox(10, saveBtn, cancelBtn);

        root.getChildren().addAll(titleLabel, new Label("Student:"), studentCombo, new Label("Course:"), courseCombo,
                                  new Label("Grade:"), gradeField, new Label("Remark:"), remarkArea, buttonBox);

        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
    }

    private void showViewRequests() {
        Lecturer lecturer = LecturerDAO.getLecturerByUserId(loggedInUser.getId());
        if (lecturer == null) return;

        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("View Requests");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Table for requests related to grades by this lecturer
        TableView<RequestInfo> table = new TableView<>();
        table.setItems(javafx.collections.FXCollections.observableArrayList(getRequestInfos(lecturer.getId())));

        TableColumn<RequestInfo, String> studentNameCol = new TableColumn<>("Student Name");
        studentNameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStudentName()));

        TableColumn<RequestInfo, String> courseNameCol = new TableColumn<>("Course Name");
        courseNameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCourseName()));

        TableColumn<RequestInfo, String> typeCol = new TableColumn<>("Request Type");
        typeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRequest().getRequestType().toString()));

        TableColumn<RequestInfo, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRequest().getStatus().toString()));

        table.getColumns().addAll(studentNameCol, courseNameCol, typeCol, statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox buttonBox = new HBox(10);
        Button approveBtn = new Button("Approve Selected");
        Button rejectBtn = new Button("Reject Selected");
        Button backBtn = new Button("Back");

        buttonBox.getChildren().addAll(approveBtn, rejectBtn, backBtn);

        table.setPrefHeight(300);

        approveBtn.setOnAction(e -> {
            RequestInfo selected = table.getSelectionModel().getSelectedItem();
            if (selected != null && selected.getRequest().getStatus() == Request.Status.PENDING) {
                RequestDAO.updateRequestStatus(selected.getRequest().getId(), Request.Status.APPROVED);
                table.setItems(javafx.collections.FXCollections.observableArrayList(getRequestInfos(lecturer.getId())));
            }
        });
        rejectBtn.setOnAction(e -> {
            RequestInfo selected = table.getSelectionModel().getSelectedItem();
            if (selected != null && selected.getRequest().getStatus() == Request.Status.PENDING) {
                RequestDAO.updateRequestStatus(selected.getRequest().getId(), Request.Status.REJECTED);
                table.setItems(javafx.collections.FXCollections.observableArrayList(getRequestInfos(lecturer.getId())));
            }
        });
        backBtn.setOnAction(e -> showLecturerDashboard());

        root.getChildren().addAll(titleLabel, table, buttonBox);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        DatabaseHelper.createTables();
        // Add sample data
        if (UserDAO.getAllUsers().isEmpty()) {
            UserDAO.addUser(new User(0, "admin", "admin", User.Role.ADMIN, "admin@example.com"));
            UserDAO.addUser(new User(0, "student", "student", User.Role.STUDENT, "student@example.com"));
            UserDAO.addUser(new User(0, "lecturer", "lecturer", User.Role.LECTURER, "lecturer@example.com"));
        }
        launch(args);
    }

    // Helper Methods
    private List<GradeInfo> getGradeInfos(int lecturerId) {
        List<GradeInfo> infos = new ArrayList<>();
        List<Grade> grades = GradeDAO.getGradesByLecturerId(lecturerId);
        for (Grade grade : grades) {
            Student student = StudentDAO.getStudentById(grade.getStudentId());
            Course course = CourseDAO.getCourseById(grade.getCourseId());
            String sName = (student != null) ? student.getName() : "Unknown";
            String cName = (course != null) ? course.getName() : "Unknown";
            infos.add(new GradeInfo(grade, sName, cName));
        }
        return infos;
    }

    private List<RequestInfo> getRequestInfos(int lecturerId) {
        List<RequestInfo> infos = new ArrayList<>();
        List<Grade> grades = GradeDAO.getGradesByLecturerId(lecturerId);
        for (Grade grade : grades) {
            // Find requests for this grade
            List<Request> requests = RequestDAO.getRequestsByStudentId(grade.getStudentId()).stream()
                .filter(r -> r.getGradeId() == grade.getId()).collect(java.util.stream.Collectors.toList());
            for (Request req : requests) {
                Student student = StudentDAO.getStudentById(req.getStudentId());
                Course course = CourseDAO.getCourseById(grade.getCourseId());
                String sName = (student != null) ? student.getName() : "Unknown";
                String cName = (course != null) ? course.getName() : "Unknown";
                infos.add(new RequestInfo(req, sName, cName));
            }
        }
        return infos;
    }

    // Student Methods
    private void showViewGrades() {
        Student student = StudentDAO.getStudentByUserId(loggedInUser.getId());
        if (student == null) return;

        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("My Grades");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView<StudentGradeInfo> table = new TableView<>();
        table.setItems(javafx.collections.FXCollections.observableArrayList(getStudentGradeInfos(student.getId())));

        TableColumn<StudentGradeInfo, String> courseNameCol = new TableColumn<>("Course Name");
        courseNameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCourseName()));

        TableColumn<StudentGradeInfo, Double> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getGradeValue()));

        TableColumn<StudentGradeInfo, String> remarkCol = new TableColumn<>("Remark");
        remarkCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRemark()));

        table.getColumns().addAll(courseNameCol, gradeCol, remarkCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button backBtn = new Button("Back");

        table.setPrefHeight(300);

        backBtn.setOnAction(e -> showStudentDashboard());

        root.getChildren().addAll(titleLabel, table, backBtn);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }

    private void showMakeRequest() {
        Student student = StudentDAO.getStudentByUserId(loggedInUser.getId());
        if (student == null) return;

        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("Make Request");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        List<StudentGradeInfo> gradeInfos = getStudentGradeInfos(student.getId());
        ComboBox<StudentGradeInfo> gradeCombo = new ComboBox<>();
        gradeCombo.setItems(javafx.collections.FXCollections.observableArrayList(gradeInfos));
        gradeCombo.setPromptText("Select Grade");

        ComboBox<Request.RequestType> typeCombo = new ComboBox<>();
        typeCombo.setItems(javafx.collections.FXCollections.observableArrayList(Request.RequestType.values()));
        typeCombo.setPromptText("Select Request Type");

        Button submitBtn = new Button("Submit");
        Button cancelBtn = new Button("Cancel");

        submitBtn.setOnAction(e -> {
            StudentGradeInfo selected = gradeCombo.getValue();
            Request.RequestType type = typeCombo.getValue();
            if (selected != null && type != null) {
                Request request = new Request(0, student.getId(), selected.getGrade().getId(), type, Request.Status.PENDING);
                RequestDAO.addRequest(request);
                showStudentDashboard();
            }
        });
        cancelBtn.setOnAction(e -> showStudentDashboard());

        HBox buttonBox = new HBox(10, submitBtn, cancelBtn);

        root.getChildren().addAll(titleLabel, new Label("Select Grade:"), gradeCombo, new Label("Request Type:"), typeCombo, buttonBox);

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
    }

    private void showViewMyRequests() {
        Student student = StudentDAO.getStudentByUserId(loggedInUser.getId());
        if (student == null) return;

        VBox root = new VBox(10);
        root.setSpacing(10);
        root.setPadding(new Insets(20));

        Label titleLabel = new Label("My Requests");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView<StudentRequestInfo> table = new TableView<>();
        table.setItems(javafx.collections.FXCollections.observableArrayList(getStudentRequestInfos(student.getId())));

        TableColumn<StudentRequestInfo, String> courseNameCol = new TableColumn<>("Course Name");
        courseNameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCourseName()));

        TableColumn<StudentRequestInfo, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRequest().getRequestType().toString()));

        TableColumn<StudentRequestInfo, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRequest().getStatus().toString()));

        table.getColumns().addAll(courseNameCol, typeCol, statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button backBtn = new Button("Back");

        table.setPrefHeight(300);

        backBtn.setOnAction(e -> showStudentDashboard());

        root.getChildren().addAll(titleLabel, table, backBtn);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }

    // Additional helper classes
    private static class StudentGradeInfo {
        private final Grade grade;
        private final String courseName;

        public StudentGradeInfo(Grade grade, String courseName) {
            this.grade = grade;
            this.courseName = courseName;
        }

        public Grade getGrade() { return grade; }
        public String getCourseName() { return courseName; }
        public double getGradeValue() { return grade.getGrade(); }
        public String getRemark() { return grade.getRemark(); }
    }

    private static class StudentRequestInfo {
        private final Request request;
        private final String courseName;

        public StudentRequestInfo(Request request, String courseName) {
            this.request = request;
            this.courseName = courseName;
        }

        public Request getRequest() { return request; }
        public String getCourseName() { return courseName; }
    }

    // Additional helper methods
    private List<StudentGradeInfo> getStudentGradeInfos(int studentId) {
        List<StudentGradeInfo> infos = new ArrayList<>();
        List<Grade> grades = GradeDAO.getGradesByStudentId(studentId);
        for (Grade grade : grades) {
            Course course = CourseDAO.getCourseById(grade.getCourseId());
            String cName = (course != null) ? course.getName() : "Unknown";
            infos.add(new StudentGradeInfo(grade, cName));
        }
        return infos;
    }

    private List<StudentRequestInfo> getStudentRequestInfos(int studentId) {
        List<StudentRequestInfo> infos = new ArrayList<>();
        List<Request> requests = RequestDAO.getRequestsByStudentId(studentId);
        for (Request req : requests) {
            Grade grade = GradeDAO.getGradeById(req.getGradeId());
            Course course = (grade != null) ? CourseDAO.getCourseById(grade.getCourseId()) : null;
            String cName = (course != null) ? course.getName() : "Unknown";
            infos.add(new StudentRequestInfo(req, cName));
        }
        return infos;
    }
}
