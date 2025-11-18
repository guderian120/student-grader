package com.grader.model;

public class Grade {
    private int id;
    private int studentId;
    private int courseId;
    private int lecturerId;
    private double grade;
    private String remark;

    public Grade() {}

    public Grade(int id, int studentId, int courseId, int lecturerId, double grade, String remark) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.lecturerId = lecturerId;
        this.grade = grade;
        this.remark = remark;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getLecturerId() { return lecturerId; }
    public void setLecturerId(int lecturerId) { this.lecturerId = lecturerId; }

    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
