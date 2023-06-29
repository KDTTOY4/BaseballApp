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
  public enum Reason {
    GAMBLING("도박"),
    VIOLENCE("개인사유"),
    ETC("기타");

    private final String reason;

    Reason(String reason) {
      this.reason = reason;
    }

    public String getReason() {
      return reason;
    }
  }
  public void insertOutPlayer(Integer playerId, Reason reason) {
    String insertSQL = "INSERT INTO out_player (player_id, reason, created_at) VALUES (?, ?, now());";
    String updateSQL = "UPDATE player SET team_id = null WHERE id = ?;";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(insertSQL);
         PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
      conn.setAutoCommit(false);

      pstmt.setInt(1, playerId);
      pstmt.setString(2, reason.name()); // 열거형의 이름을 사용하여 문자열 값을 설정합니다.
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