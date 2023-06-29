package com.fastcampus.dao;

import com.fastcampus.db.DBConnection;
import com.fastcampus.dto.OutPlayerRespDto;
import com.fastcampus.enums.OutReason;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OutPlayerDao {
  public void insertOutPlayer(Integer playerId, OutReason reason) {
    String insertSQL =
        "INSERT INTO out_player (player_id, reason, created_at) VALUES (?, ?, now());";
    String updateSQL = "UPDATE player SET team_id = null WHERE id = ?;";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(insertSQL);
        PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
      conn.setAutoCommit(false);

      pstmt.setInt(1, playerId);
      pstmt.setString(2, reason.getReason());
      pstmt.executeUpdate();

      updateStmt.setInt(1, playerId);
      int affectedRows = updateStmt.executeUpdate();

      if (affectedRows > 0) {
        System.out.println("OutPlayer Registration Success");
      } else {
        System.out.println("OutPlayer Registration Failed");
      }

      conn.commit();
      conn.setAutoCommit(true);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<OutPlayerRespDto> selectAll() {
    List<OutPlayerRespDto> outPlayerRespDtoList = new ArrayList<>();

    String sql =
        "select "
            + "t.name team_name, "
            + "p.id player_id, "
            + "p.name player_name, "
            + "p.position player_position, "
            + "o.reason reason, "
            + "o.created_at out_date "
            + "from out_player o "
            + "right outer join player p on p.id = o.player_id "
            + "left outer join team t on t.id = p.team_id;";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        String teamName = rs.getString("team_name");
        Integer playerId = rs.getInt("player_id");
        String playerName = rs.getString("player_name");
        String playerPosition = rs.getString("player_position");
        String reason = rs.getString("reason");
        Timestamp outDate = rs.getTimestamp("out_date");

        OutPlayerRespDto outPlayerRespDto;
        if (rs.getString("reason") == null) {
          outPlayerRespDto = OutPlayerRespDto.of(teamName, playerId, playerName, playerPosition);
        } else {
          outPlayerRespDto =
              OutPlayerRespDto.of(playerId, playerName, playerPosition, reason, outDate);
        }
        outPlayerRespDtoList.add(outPlayerRespDto);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return outPlayerRespDtoList;
  }
}
