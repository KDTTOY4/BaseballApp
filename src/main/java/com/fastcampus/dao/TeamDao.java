package com.fastcampus.dao;

import com.fastcampus.db.DBConnection;
import com.fastcampus.dto.PositionRespDto;
import com.fastcampus.dto.TeamDto;
import com.fastcampus.exceptions.BaseballAppException;
import com.fastcampus.exceptions.code.AppErrorCode;

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
      handleSQLException(e);
    }
  }

  public List<TeamDto> selectAll() {
    List<TeamDto> teamList = new ArrayList<>();

    String sql =
            "SELECT t.id, t.name, t.created_at, s.name as stadium_name FROM team t JOIN stadium s ON t.stadium_id = s.id";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        teamList.add(mapResultSetToTeamDto(rs));
      }
    } catch (SQLException e) {
      handleSQLException(e);
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

  public List<PositionRespDto> selectPlayerTablePositionRowTeamColumn() {
    List<TeamDto> teamDtoList = selectAll();

    StringBuilder sql = new StringBuilder("select p.position position_name");

    for (TeamDto teamDto : teamDtoList) {
      sql.append(", ")
              .append("max(case when t.name = '")
              .append(teamDto.getName())
              .append("' then p.name end) ")
              .append(teamDto.getName());
    }
    sql.append(" from team t right outer join player p on p.team_id = t.id group by p.position;");

    List<PositionRespDto> positionRespDtoList = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        PositionRespDto positionRespDto = new PositionRespDto(rs.getString("position_name"));

        for (TeamDto teamDto : teamDtoList) {
          positionRespDto
                  .getPlayerNameMapByTeam()
                  .put(teamDto.getName(), rs.getString(teamDto.getName()));
        }

        positionRespDtoList.add(positionRespDto);
      }

    } catch (SQLException e) {
      handleSQLException(e);
    }

    return positionRespDtoList;
  }

  private void handleSQLException(SQLException e) {
    throw new BaseballAppException(AppErrorCode.DB_EXCEPTION);
  }
}
