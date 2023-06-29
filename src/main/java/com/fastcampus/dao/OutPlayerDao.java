package com.fastcampus.dao;

import com.fastcampus.db.DBConnection;
import com.fastcampus.dto.OutPlayerRespDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OutPlayerDao {
  public void insertOutPlayer(Integer playerId, String reason) {
    String insertSQL =
        "insert into out_player (player_id, reason, created_at) values (?, ?, now());";

    String updateSQL = "update player set team_id=null where id = ?;";

    Connection conn = null;
    try {
      PreparedStatement pstmt = conn.prepareStatement(insertSQL);
      conn = DBConnection.getConnection();
      conn.setAutoCommit(false);

      pstmt.setInt(1, playerId);
      pstmt.setString(2, reason);
      pstmt.executeUpdate();

      pstmt = conn.prepareStatement(updateSQL);
      pstmt.setInt(1, playerId);

      int affectedRows = pstmt.executeUpdate();

      if (affectedRows > 0) {
        System.out.println("OutPlayer Registration Success");
      } else {
        System.out.println("OutPlayer Registration Failed");
      }

      conn.commit();
      conn.setAutoCommit(true);
      pstmt.close();
    } catch (Exception e) {
      try {
        conn.rollback();
        conn.setAutoCommit(true);
      } catch (SQLException e2) {

      }
      e.printStackTrace();
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e3) {
      }
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
        if (rs.getString("reason") == null) {
          outPlayerRespDtoList.add(
              OutPlayerRespDto.of(
                  rs.getString("team_name"),
                  rs.getInt("player_id"),
                  rs.getString("player_name"),
                  rs.getString("player_position")));
        } else {
          outPlayerRespDtoList.add(
              OutPlayerRespDto.of(
                  rs.getInt("player_id"),
                  rs.getString("player_name"),
                  rs.getString("player_position"),
                  rs.getString("reason"),
                  rs.getTimestamp("out_date")));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return outPlayerRespDtoList;
  }
}
