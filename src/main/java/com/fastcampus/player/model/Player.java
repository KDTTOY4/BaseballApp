package com.fastcampus.player.model;

import java.sql.Timestamp;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Player {
  private Integer id;
  @Setter private Integer teamId;
  private String name;
  @Setter private String position;
  private Timestamp createdAt;

  private Player(Integer teamId, String name, String position) {
    this.teamId = teamId;
    this.name = name;
    this.position = position;
  }

  public static Player of(Integer teamId, String name, String position) {
    return new Player(teamId, name, position);
  }

  public static Player of(
      Integer id, Integer teamId, String name, String position, Timestamp createdAt) {
    return new Player(id, teamId, name, position, createdAt);
  }
}
