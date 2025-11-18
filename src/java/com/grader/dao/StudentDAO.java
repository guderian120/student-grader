package com.grader.dao;

import com.grader.DatabaseHelper;
import com.grader.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public static boolean addStudent(Student student) {
        String sql = "INSERT INTO students (user_id, name, student_id, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, student.getUserId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getStudentId());
            pstmt.setString(4, student.getEmail());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Add student error: " + e.getMessage());
        }
        return false;
    }

    public static boolean updateStudent(Student student) {
        String sql = "UPDATE students SET user_id = ?, name = ?, student_id = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, student.getUserId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getStudentId());
            pstmt.setString(4, student.getEmail());
            pstmt.setInt(5, student.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update student error: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete student error: " + e.getMessage());
        }
        return false;
    }

    public static Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Student(rs.getInt("id"), rs.getInt("user_id"),
                                   rs.getString("name"), rs.getString("student_id"), rs.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("Get student by id error: " + e.getMessage());
        }
        return null;
    }

    public static Student getStudentByUserId(int userId) {
        String sql = "SELECT * FROM students WHERE user_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Student(rs.getInt("id"), rs.getInt("user_id"),
                                   rs.getString("name"), rs.getString("student_id"), rs.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("Get student by user id error: " + e.getMessage());
        }
        return null;
    }

    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                students.add(new Student(rs.getInt("id"), rs.getInt("user_id"),
                                         rs.getString("name"), rs.getString("student_id"), rs.getString("email")));
            }
        } catch (SQLException e) {
            System.err.println("Get all students error: " + e.getMessage());
        }
        return students;
    }
}
