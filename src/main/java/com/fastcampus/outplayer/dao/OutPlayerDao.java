package com.fastcampus.outplayer.dao;

import com.fastcampus.db.DBConnection;
import com.fastcampus.outplayer.dto.OutPlayerRespDto;
import com.fastcampus.outplayer.enums.OutReason;
import com.fastcampus.exceptions.BaseballAppException;
import com.fastcampus.exceptions.code.AppErrorCode;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OutPlayerDao {
  public void insert(Integer playerId, OutReason reason) throws BaseballAppException {
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

      if (affectedRows == 0) throw new SQLException();

      conn.commit();
      conn.setAutoCommit(true);
    } catch (SQLException e) {
      throw new BaseballAppException(AppErrorCode.DB_EXCEPTION);
    } catch (Exception e) {
      throw new BaseballAppException(AppErrorCode.UNKNOWN_EXCEPTION);
    }
  }

  public List<OutPlayerRespDto> selectAll() throws BaseballAppException {
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
        LocalDateTime outDate =
            rs.getTimestamp("out_date") == null
                ? null
                : rs.getTimestamp("out_date").toLocalDateTime();

        outPlayerRespDtoList.add(
            OutPlayerRespDto.of(teamName, playerId, playerName, playerPosition, reason, outDate));
      }
    } catch (SQLException e) {
      throw new BaseballAppException(AppErrorCode.DB_EXCEPTION);
    } catch (Exception e) {
      throw new BaseballAppException(AppErrorCode.UNKNOWN_EXCEPTION);
    }

    return outPlayerRespDtoList;
  }
}
