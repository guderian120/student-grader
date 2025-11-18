package com.grader.dao;

import com.grader.DatabaseHelper;
import com.grader.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public static boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (course_code, name, description) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseCode());
            pstmt.setString(2, course.getName());
            pstmt.setString(3, course.getDescription());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Add course error: " + e.getMessage());
        }
        return false;
    }

    public static boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET course_code = ?, name = ?, description = ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getCourseCode());
            pstmt.setString(2, course.getName());
            pstmt.setString(3, course.getDescription());
            pstmt.setInt(4, course.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update course error: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteCourse(int id) {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete course error: " + e.getMessage());
        }
        return false;
    }

    public static Course getCourseById(int id) {
        String sql = "SELECT * FROM courses WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Course(rs.getInt("id"), rs.getString("course_code"),
                                  rs.getString("name"), rs.getString("description"));
            }
        } catch (SQLException e) {
            System.err.println("Get course by id error: " + e.getMessage());
        }
        return null;
    }

    public static List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                courses.add(new Course(rs.getInt("id"), rs.getString("course_code"),
                                       rs.getString("name"), rs.getString("description")));
            }
        } catch (SQLException e) {
            System.err.println("Get all courses error: " + e.getMessage());
        }
        return courses;
    }
}
