package com.fastcampus.dao;

import com.fastcampus.dbconnection.DBConnection;
import com.fastcampus.dto.TeamDto;
import com.fastcampus.model.Team;

import java.sql.*;
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

    public List<TeamDto> selectAll() {
        List<TeamDto> teamList = new ArrayList<>();

        String sql = "SELECT t.id, t.name, t.created_at, s.name AS stadium_name " +
                "FROM team t " +
                "JOIN stadium s ON t.stadium_id = s.id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                TeamDto teamDto = mapResultSetToTeamDto(rs);
                teamList.add(teamDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teamList;
    }

    private TeamDto mapResultSetToTeamDto(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        Timestamp createdAt = rs.getTimestamp("created_at");
        String stadiumName = rs.getString("stadium_name");

        return new TeamDto(id, name, createdAt, stadiumName);
    }
}
