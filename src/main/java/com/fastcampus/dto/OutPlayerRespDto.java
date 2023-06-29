package com.fastcampus.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class OutPlayerRespDto {
  String teamName;
  Integer playerId;
  String playerName;
  String playerPosition;
  String reason;
  LocalDateTime outDate;

  public static OutPlayerRespDto of(
      String teamName, Integer playerId, String playerName, String playerPosition) {
    return new OutPlayerRespDto(teamName, playerId, playerName, playerPosition, null, null);
  }

  public static OutPlayerRespDto of(
      Integer playerId,
      String playerName,
      String playerPosition,
      String reason,
      Timestamp outDate) {
    return new OutPlayerRespDto(
        null, playerId, playerName, playerPosition, reason, outDate.toLocalDateTime());
  }
}
