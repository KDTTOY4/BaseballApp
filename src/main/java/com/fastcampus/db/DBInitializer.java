package com.fastcampus.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
  public static void createTables() {
    String createStadiumTable =
        "CREATE TABLE IF NOT EXISTS stadium (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL)";

    String createTeamTable =
        "CREATE TABLE IF NOT EXISTS team (id INT AUTO_INCREMENT PRIMARY KEY, stadium_id INT NOT NULL, name VARCHAR(100) NOT NULL, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, deleted_at TIMESTAMP)";

    try (Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement()) {

      stmt.executeUpdate(createStadiumTable);
      stmt.executeUpdate(createTeamTable);

      System.out.println("Tables created successfully.");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
