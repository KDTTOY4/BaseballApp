package com.fastcampus.dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeamDto {
  private Integer id;
  private String name;
  private Timestamp createdAt;
  private String stadiumName;

  public TeamDto(Integer id, String name, Timestamp createdAt, String stadiumName) {
    this.id = id;
    this.name = name;
    this.createdAt = createdAt;
    this.stadiumName = stadiumName;
  }
}
