package com.grader.model;

public class Student {
    private int id;
    private int userId;
    private String name;
    private String studentId;
    private String email;

    public Student() {}

    public Student(int id, int userId, String name, String studentId, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.studentId = studentId;
        this.email = email;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
