package com.codecool.student_scores;

import java.sql.*;

public class StudentScores {
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private final Connection CONN;

    public StudentScores(String URL, String USER, String PASSWORD) {
        this.URL = URL;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
        this.CONN = getConnection();
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database not reachable.");
        }
        return connection;
    }
    public String getCityWithHighestScore() {
        try (this.CONN) {
            String query = "SELECT city, SUM(score) as highest_score FROM student_scores " +
                    "GROUP BY city " +
                    "ORDER BY highest_score DESC LIMIT 1";
            PreparedStatement ps = CONN.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public String getMostActiveStudent() {
        try (this.CONN) {
            String query = "SELECT student_name, COUNT(score) as most_scores FROM student_scores " +
                    "GROUP BY student_name " +
                    "ORDER BY most_scores DESC LIMIT 1";
            PreparedStatement ps = CONN.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
