package com.grader.model;

public class Lecturer {
    private int id;
    private int userId;
    private String name;
    private String lecturerId;
    private String email;

    public Lecturer() {}

    public Lecturer(int id, int userId, String name, String lecturerId, String email) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.lecturerId = lecturerId;
        this.email = email;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLecturerId() { return lecturerId; }
    public void setLecturerId(String lecturerId) { this.lecturerId = lecturerId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
