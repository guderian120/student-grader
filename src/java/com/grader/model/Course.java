package com.grader.model;

public class Course {
    private int id;
    private String courseCode;
    private String name;
    private String description;

    public Course() {}

    public Course(int id, String courseCode, String name, String description) {
        this.id = id;
        this.courseCode = courseCode;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
