package com.fastcampus.dao;

import com.fastcampus.dbconnection.DBConnection;
import com.fastcampus.model.Team;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TeamDao {
    public void insertTeam(Integer stadiumId, String name) {
        String sql = "INSERT INTO TEAM (stadium_id, name) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, stadiumId);
            pstmt.setString(2, name);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Team Registration Success");
            } else {
                System.out.println("Team Registration Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Team> selectAll() {
        List<Team> teamList = new ArrayList<>();

        String sql = "SELECT * FROM team t";

        ResultSet rs = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            rs = pstmt.executeQuery();

            while (rs.next()) {
                teamList.add(
                        new Team(
                                rs.getInt("id"),
                                rs.getInt("stadium_id"),
                                rs.getString("name"),
                                rs.getTimestamp("created_at")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teamList;
    }
}
