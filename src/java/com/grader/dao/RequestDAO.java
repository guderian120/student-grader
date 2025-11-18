package com.grader.dao;

import com.grader.DatabaseHelper;
import com.grader.model.Request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {

    public static boolean addRequest(Request request) {
        String sql = "INSERT INTO requests (student_id, grade_id, request_type, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, request.getStudentId());
            pstmt.setInt(2, request.getGradeId());
            pstmt.setString(3, request.getRequestType().toString());
            pstmt.setString(4, request.getStatus().toString());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Add request error: " + e.getMessage());
        }
        return false;
    }

    public static boolean updateRequestStatus(int id, Request.Status status) {
        String sql = "UPDATE requests SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.toString());
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update request status error: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteRequest(int id) {
        String sql = "DELETE FROM requests WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete request error: " + e.getMessage());
        }
        return false;
    }

    public static Request getRequestById(int id) {
        String sql = "SELECT * FROM requests WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Request(rs.getInt("id"), rs.getInt("student_id"),
                                   rs.getInt("grade_id"),
                                   Request.RequestType.valueOf(rs.getString("request_type")),
                                   Request.Status.valueOf(rs.getString("status")));
            }
        } catch (SQLException e) {
            System.err.println("Get request by id error: " + e.getMessage());
        }
        return null;
    }

    public static List<Request> getRequestsByStudentId(int studentId) {
        List<Request> requests = new ArrayList<>();
        String sql = "SELECT * FROM requests WHERE student_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                requests.add(new Request(rs.getInt("id"), rs.getInt("student_id"),
                                         rs.getInt("grade_id"),
                                         Request.RequestType.valueOf(rs.getString("request_type")),
                                         Request.Status.valueOf(rs.getString("status"))));
            }
        } catch (SQLException e) {
            System.err.println("Get requests by student id error: " + e.getMessage());
        }
        return requests;
    }

    public static List<Request> getAllRequests() {
        List<Request> requests = new ArrayList<>();
        String sql = "SELECT * FROM requests";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                requests.add(new Request(rs.getInt("id"), rs.getInt("student_id"),
                                         rs.getInt("grade_id"),
                                         Request.RequestType.valueOf(rs.getString("request_type")),
                                         Request.Status.valueOf(rs.getString("status"))));
            }
        } catch (SQLException e) {
            System.err.println("Get all requests error: " + e.getMessage());
        }
        return requests;
    }
}
