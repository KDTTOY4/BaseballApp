package com.fastcampus.team.dao;

import com.fastcampus.db.DBConnection;
import com.fastcampus.team.dto.PositionRespDto;
import com.fastcampus.team.dto.TeamDto;
import com.fastcampus.exceptions.BaseballAppException;
import com.fastcampus.exceptions.code.AppErrorCode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TeamDao {

  public void insert(Integer stadiumId, String name) throws BaseballAppException {
    String sql = "INSERT INTO TEAM (stadium_id, name, created_at) VALUES (?, ?, now())";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, stadiumId);
      pstmt.setString(2, name);

      int affectedRows = pstmt.executeUpdate();

      if (affectedRows == 0) throw new SQLException();

    } catch (SQLException e) {
      throw new BaseballAppException(AppErrorCode.DB_EXCEPTION);
    } catch (Exception e) {
      throw new BaseballAppException(AppErrorCode.UNKNOWN_EXCEPTION);
    }
  }

  public List<TeamDto> selectAll() throws BaseballAppException {
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
      throw new BaseballAppException(AppErrorCode.DB_EXCEPTION);
    } catch (Exception e) {
      throw new BaseballAppException(AppErrorCode.UNKNOWN_EXCEPTION);
    }
    return teamList;
  }

  private TeamDto mapResultSetToTeamDto(ResultSet rs) throws SQLException {
    Integer id = rs.getInt("id");
    String name = rs.getString("name");
    Timestamp createdAt = rs.getTimestamp("created_at");
    String stadiumName = rs.getString("stadium_name");

    return TeamDto.of(id, name, createdAt, stadiumName);
  }

  public List<PositionRespDto> selectPlayerTablePositionRowTeamColumn()
      throws BaseballAppException {
    List<PositionRespDto> positionRespDtoList = new ArrayList<>();

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

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        PositionRespDto positionRespDto = PositionRespDto.of(rs.getString("position_name"));

        for (TeamDto teamDto : teamDtoList) {
          positionRespDto
              .getPlayerNameMapByTeam()
              .put(teamDto.getName(), rs.getString(teamDto.getName()));
        }

        positionRespDtoList.add(positionRespDto);
      }

    } catch (SQLException e) {
      throw new BaseballAppException(AppErrorCode.DB_EXCEPTION);
    } catch (Exception e) {
      throw new BaseballAppException(AppErrorCode.UNKNOWN_EXCEPTION);
    }

    return positionRespDtoList;
  }
}
