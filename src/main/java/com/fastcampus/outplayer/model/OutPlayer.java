package com.fastcampus.outplayer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class OutPlayer {
  Integer id;
  Integer playerId;
  String reason;
  Timestamp createdAt;
}
