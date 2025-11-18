package com.grader.model;

public class Request {
    public enum RequestType {
        REMARK, REVIEW
    }

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    private int id;
    private int studentId;
    private int gradeId;
    private RequestType requestType;
    private Status status;

    public Request() {}

    public Request(int id, int studentId, int gradeId, RequestType requestType, Status status) {
        this.id = id;
        this.studentId = studentId;
        this.gradeId = gradeId;
        this.requestType = requestType;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getGradeId() { return gradeId; }
    public void setGradeId(int gradeId) { this.gradeId = gradeId; }

    public RequestType getRequestType() { return requestType; }
    public void setRequestType(RequestType requestType) { this.requestType = requestType; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
