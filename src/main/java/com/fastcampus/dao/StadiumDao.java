package com.fastcampus.dao;

import com.fastcampus.db.DBConnection;
import com.fastcampus.dto.StadiumDto;
import com.fastcampus.exceptions.BaseballAppException;
import com.fastcampus.exceptions.code.AppErrorCode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StadiumDao {
  public void insertStadium(String name) {
    String sql = "INSERT INTO stadium (name, created_at) VALUES (?, now());";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, name);

      int affectedRows = pstmt.executeUpdate();

      if (affectedRows == 0) throw new SQLException();

    } catch (SQLException e) {
      throw new BaseballAppException(AppErrorCode.DB_EXCEPTION);
    } catch (Exception e) {
      throw new BaseballAppException(AppErrorCode.UNKNOWN_EXCEPTION);
    }
  }

  public List<StadiumDto> selectAll() {
    List<StadiumDto> stadiumDtoList = new ArrayList<>();

    String sql = "SELECT * FROM stadium;";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        stadiumDtoList.add(
            StadiumDto.of(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("created_at")));
      }
    } catch (SQLException e) {
      throw new BaseballAppException(AppErrorCode.DB_EXCEPTION);
    } catch (Exception e) {
      throw new BaseballAppException(AppErrorCode.UNKNOWN_EXCEPTION);
    }

    return stadiumDtoList;
  }
}
