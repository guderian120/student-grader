package com.grader.dao;

import com.grader.DatabaseHelper;
import com.grader.model.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    public static boolean addGrade(Grade grade) {
        String sql = "INSERT INTO grades (student_id, course_id, lecturer_id, grade, remark, mid_sem_score, exam_score, attendance_count, total_attendance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, grade.getStudentId());
            pstmt.setInt(2, grade.getCourseId());
            pstmt.setInt(3, grade.getLecturerId());
            pstmt.setDouble(4, grade.getGrade());
            pstmt.setString(5, grade.getRemark());
            pstmt.setDouble(6, grade.getMidSemScore());
            pstmt.setDouble(7, grade.getExamScore());
            pstmt.setInt(8, grade.getAttendanceCount());
            pstmt.setInt(9, grade.getTotalAttendance());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Add grade error: " + e.getMessage());
        }
        return false;
    }

    public static boolean updateGrade(Grade grade) {
        String sql = "UPDATE grades SET student_id = ?, course_id = ?, lecturer_id = ?, grade = ?, remark = ?, mid_sem_score = ?, exam_score = ?, attendance_count = ?, total_attendance = ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, grade.getStudentId());
            pstmt.setInt(2, grade.getCourseId());
            pstmt.setInt(3, grade.getLecturerId());
            pstmt.setDouble(4, grade.getGrade());
            pstmt.setString(5, grade.getRemark());
            pstmt.setDouble(6, grade.getMidSemScore());
            pstmt.setDouble(7, grade.getExamScore());
            pstmt.setInt(8, grade.getAttendanceCount());
            pstmt.setInt(9, grade.getTotalAttendance());
            pstmt.setInt(10, grade.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update grade error: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteGrade(int id) {
        String sql = "DELETE FROM grades WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete grade error: " + e.getMessage());
        }
        return false;
    }

    public static Grade getGradeById(int id) {
        String sql = "SELECT * FROM grades WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Grade(rs.getInt("id"), rs.getInt("student_id"),
                                 rs.getInt("course_id"), rs.getInt("lecturer_id"),
                                 rs.getDouble("grade"), rs.getString("remark"),
                                 rs.getDouble("mid_sem_score"), rs.getDouble("exam_score"),
                                 rs.getInt("attendance_count"), rs.getInt("total_attendance"));
            }
        } catch (SQLException e) {
            System.err.println("Get grade by id error: " + e.getMessage());
        }
        return null;
    }

    public static List<Grade> getGradesByStudentId(int studentId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE student_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                grades.add(new Grade(rs.getInt("id"), rs.getInt("student_id"),
                                     rs.getInt("course_id"), rs.getInt("lecturer_id"),
                                     rs.getDouble("grade"), rs.getString("remark"),
                                     rs.getDouble("mid_sem_score"), rs.getDouble("exam_score"),
                                     rs.getInt("attendance_count"), rs.getInt("total_attendance")));
            }
        } catch (SQLException e) {
            System.err.println("Get grades by student id error: " + e.getMessage());
        }
        return grades;
    }

    public static List<Grade> getGradesByLecturerId(int lecturerId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE lecturer_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, lecturerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                grades.add(new Grade(rs.getInt("id"), rs.getInt("student_id"),
                                     rs.getInt("course_id"), rs.getInt("lecturer_id"),
                                     rs.getDouble("grade"), rs.getString("remark"),
                                     rs.getDouble("mid_sem_score"), rs.getDouble("exam_score"),
                                     rs.getInt("attendance_count"), rs.getInt("total_attendance")));
            }
        } catch (SQLException e) {
            System.err.println("Get grades by lecturer id error: " + e.getMessage());
        }
        return grades;
    }

    public static List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int studentId = rs.getInt("student_id");
                int courseId = rs.getInt("course_id");
                int lecturerId = rs.getInt("lecturer_id");
                double gradeVal = rs.getDouble("grade");
                String remark = rs.getString("remark");
                double midSem = rs.getDouble("mid_sem_score");
                double exam = rs.getDouble("exam_score");
                int attCount = rs.getInt("attendance_count");
                int totalAtt = rs.getInt("total_attendance");
                grades.add(new Grade(id, studentId, courseId, lecturerId, gradeVal, remark, midSem, exam, attCount, totalAtt));
            }
        } catch (SQLException e) {
            System.err.println("Get all grades error: " + e.getMessage());
        }
        return grades;
    }
}
