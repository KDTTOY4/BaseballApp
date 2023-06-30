package com.fastcampus.dao;

import com.fastcampus.db.DBConnection;
import com.fastcampus.dto.PlayerDto;
import com.fastcampus.exceptions.BaseballAppException;
import com.fastcampus.exceptions.code.AppErrorCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PlayerDao {
    public void insertPlayer(Integer teamId, String name, String position) {
        String sql = "INSERT INTO player (team_id, name, position, created_at) VALUES (?, ?, ?, now())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, teamId);
            pstmt.setString(2, name);
            pstmt.setString(3, position);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Player Registration Success");
            } else {
                System.out.println("Player Registration Failed");
            }
        } catch (SQLException e) {
            throw new BaseballAppException(AppErrorCode.DB_EXCEPTION);
        } catch (Exception e) {
            throw new BaseballAppException(AppErrorCode.UNKNOWN_EXCEPTION);
        }
    }

    public List<PlayerDto> selectAll(Integer teamId) {
        List<PlayerDto> playerList = new ArrayList<>();

        String sql =
                "SELECT p.id, p.name, p.position, p.created_at, t.name as team_name "
                        + "FROM player p JOIN team t ON p.team_id = t.id WHERE t.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, teamId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                playerList.add(mapResultSetToPlayerDto(rs));
            }
        } catch (SQLException e) {
            throw new BaseballAppException(AppErrorCode.DB_EXCEPTION);
        } catch (Exception e) {
            throw new BaseballAppException(AppErrorCode.UNKNOWN_EXCEPTION);
        }

        return playerList;
    }


    private PlayerDto mapResultSetToPlayerDto(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        String position = rs.getString("position");
        Timestamp createdAt = rs.getTimestamp("created_at");
        String teamName = rs.getString("team_name");

        return new PlayerDto(id, name, position, createdAt, teamName);
    }

    public PlayerDto selectById(Integer playerId) {
        PlayerDto player = null;
        String sql =
                "SELECT p.id, p.name, p.position, p.created_at, t.name AS team_name "
                        + "FROM player p "
                        + "LEFT OUTER JOIN team t ON p.team_id = t.id "
                        + "WHERE p.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, playerId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                player = mapResultSetToPlayerDto(rs);
            }
        } catch (SQLException e) {
            throw new BaseballAppException(AppErrorCode.DB_EXCEPTION);
        } catch (Exception e) {
            throw new BaseballAppException(AppErrorCode.UNKNOWN_EXCEPTION);
        }

        return player;
    }
}
