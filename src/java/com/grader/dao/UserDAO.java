package com.grader.dao;

import com.grader.DatabaseHelper;
import com.grader.model.User;
import com.grader.model.User.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"),
                                rs.getString("password"),
                                Role.valueOf(rs.getString("role")),
                                rs.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
        }
        return null;
    }

    public static boolean addUser(User user) {
        String sql = "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole().toString());
            pstmt.setString(4, user.getEmail());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Add user error: " + e.getMessage());
        }
        return false;
    }

    public static boolean updateUser(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, role = ?, email = ? WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole().toString());
            pstmt.setString(4, user.getEmail());
            pstmt.setInt(5, user.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update user error: " + e.getMessage());
        }
        return false;
    }

    public static boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete user error: " + e.getMessage());
        }
        return false;
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("username"),
                                   rs.getString("password"),
                                   Role.valueOf(rs.getString("role")),
                                   rs.getString("email")));
            }
        } catch (SQLException e) {
            System.err.println("Get all users error: " + e.getMessage());
        }
        return users;
    }
}
