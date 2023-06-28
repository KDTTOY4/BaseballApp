package com.fastcampus.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
  public static void createTables() {
    String createStadium =
        "create table stadium ( id int primary key auto_increment, name varchar(20), created_at timestamp, index name (name))";

    String createTeam =
        "create table team ( id int primary key auto_increment, name varchar(20), created_at timestamp, stadium_id int, index name (name), foreign key (stadium_id) references stadium (id))";

    String createPlayer =
        "create table player ( id int primary key auto_increment, team_id int, position varchar(20), name varchar(20), created_at timestamp, index name (name), index team_id (team_id), index team_position (team_id, position), unique (team_id, position), foreign key (team_id) references team (id))";

    String createOutPlayer =
        "create table out_player (id int primary key auto_increment, player_id  int, reason varchar(200), created_at timestamp, foreign key (player_id) references player (id))";

    try (Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement()) {

      stmt.executeUpdate(createStadium);
      stmt.executeUpdate(createTeam);
      stmt.executeUpdate(createPlayer);
      stmt.executeUpdate(createOutPlayer);

      System.out.println("Tables created successfully.");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
