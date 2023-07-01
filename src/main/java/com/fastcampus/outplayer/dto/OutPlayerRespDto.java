package com.fastcampus.outplayer.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OutPlayerRespDto {
  String teamName;
  Integer playerId;
  String playerName;
  String playerPosition;
  String reason;
  LocalDateTime outDate;

  public static OutPlayerRespDto of(
      String teamName,
      Integer playerId,
      String playerName,
      String playerPosition,
      String reason,
      LocalDateTime outDate) {
    return new OutPlayerRespDto(teamName, playerId, playerName, playerPosition, reason, outDate);
  }

  @Override
  public String toString() {
    return playerId
        + ", "
        + playerName
        + ", "
        + playerPosition
        + ", "
        + (teamName == null ? "없음" : teamName)
        + ", "
        + (reason == null ? "" : reason)
        + ", "
        + (outDate == null ? "" : outDate.toLocalDate());
  }
}
