package com.fastcampus.team.dto;

import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TeamDto {
  private Integer id;
  private String name;
  private Timestamp createdAt;
  private String stadiumName;

  public static TeamDto of(Integer id, String name, Timestamp createdAt, String stadiumName) {
    return new TeamDto(id, name, createdAt, stadiumName);
  }

  @Override
  public String toString() {
    return id + ", " + stadiumName + ", " + name + ", " + createdAt.toLocalDateTime().toLocalDate();
  }
}
