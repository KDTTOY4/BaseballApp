package com.fastcampus.team.model;

import java.sql.Timestamp;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Team {
  private Integer id;
  private Integer stadiumId;
  private String name;
  private Timestamp createdAt;

  private Team(int stadiumId, String name) {
    this.stadiumId = stadiumId;
    this.name = name;
  }

  public static Team of(Integer stadiumId, String name) {
    return new Team(stadiumId, name);
  }

  public static Team of(Integer id, Integer stadiumId, String name, Timestamp createdAt) {
    return new Team(id, stadiumId, name, createdAt);
  }
}
