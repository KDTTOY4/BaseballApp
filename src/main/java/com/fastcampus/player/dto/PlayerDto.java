package com.fastcampus.player.dto;

import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerDto {
  private Integer id;
  private String name;
  private String position;
  private Timestamp createdAt;
  private String teamName;

  public static PlayerDto of(
      Integer id, String name, String position, Timestamp createdAt, String teamName) {
    return new PlayerDto(id, name, position, createdAt, teamName);
  }

  @Override
  public String toString() {
    return id
        + ", "
        + name
        + ", "
        + position
        + ", "
        + teamName
        + ", "
        + createdAt.toLocalDateTime().toLocalDate();
  }
}
