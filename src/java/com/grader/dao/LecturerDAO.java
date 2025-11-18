package com.grader.dao;

import com.grader.DatabaseHelper;
import com.grader.model.Lecturer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LecturerDAO {

    public static boolean addLecturer(Lecturer lecturer) {
        String sql = "INSERT INTO lecturers (user_id, name, lecturer_id, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, lecturer.getUserId());
            pstmt.setString(2, lecturer.getName());
            pstmt.setString(3, lecturer.getLecturerId());
            pstmt.setString(4, lecturer.getEmail());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Add lecturer error: " + e.getMessage());
        }
        return false;
    }

    public static boolean updateLecturer(Lecturer lecturer) {
        String sql = "UPDATE lecturers SET user_id = ?, name = ?, lecturer_id = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, lecturer.getUserId());
            pstmt.setString(2, lecturer.getName());
            pstmt.setString(3, lecturer.getLecturerId());
            pstmt.setString(4, lecturer.getEmail());
            pstmt.setInt(5, lecturer.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update lecturer error: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteLecturer(int id) {
        String sql = "DELETE FROM lecturers WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete lecturer error: " + e.getMessage());
        }
        return false;
    }

    public static Lecturer getLecturerById(int id) {
        String sql = "SELECT * FROM lecturers WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Lecturer(rs.getInt("id"), rs.getInt("user_id"),
                                   rs.getString("name"), rs.getString("lecturer_id"), rs.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("Get lecturer by id error: " + e.getMessage());
        }
        return null;
    }

    public static Lecturer getLecturerByUserId(int userId) {
        String sql = "SELECT * FROM lecturers WHERE user_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Lecturer(rs.getInt("id"), rs.getInt("user_id"),
                                   rs.getString("name"), rs.getString("lecturer_id"), rs.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("Get lecturer by user id error: " + e.getMessage());
        }
        return null;
    }

    public static List<Lecturer> getAllLecturers() {
        List<Lecturer> lecturers = new ArrayList<>();
        String sql = "SELECT * FROM lecturers";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lecturers.add(new Lecturer(rs.getInt("id"), rs.getInt("user_id"),
                                         rs.getString("name"), rs.getString("lecturer_id"), rs.getString("email")));
            }
        } catch (SQLException e) {
            System.err.println("Get all lecturers error: " + e.getMessage());
        }
        return lecturers;
    }
}
