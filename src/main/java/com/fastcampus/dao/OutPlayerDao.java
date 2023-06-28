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
    String sql =
        "insert into out_player (player_id, reason, created_at) values (?, ?, now());"
            + "update player set team_id=null where id = ?;";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      conn.setAutoCommit(false);

      pstmt.setInt(1, playerId);
      pstmt.setString(2, reason);
      pstmt.setInt(3, playerId);
      int affectedRows = pstmt.executeUpdate();
      if (affectedRows > 0) {
        System.out.println("OutPlayer Registration Success");
      } else {
        System.out.println("OutPlayer Registration Failed");
      }
      conn.commit();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<OutPlayerRespDto> selectAll() {
    List<OutPlayerRespDto> outPlayerRespDtoList = new ArrayList<>();

    String sql =
        "select "
            + "p.id player_id, "
            + "p.name player_name, "
            + "p.position player_position, "
            + "o.reason reason, "
            + "o.created_at out_date "
            + "from out_player o "
            + "right outer join player p on p.id = o.player_id;";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        if (rs.getString("reason") == null) {
          outPlayerRespDtoList.add(
              OutPlayerRespDto.of(
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
