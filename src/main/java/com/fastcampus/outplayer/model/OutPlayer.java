package com.fastcampus.outplayer.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OutPlayer {
  Integer id;
  Integer playerId;
  String reason;
  Timestamp createdAt;

  public static OutPlayer of(Integer id, Integer playerId, String reason, Timestamp createdAt) {
    return new OutPlayer(id, playerId, reason, createdAt);
  }
}
