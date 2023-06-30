package com.fastcampus.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PositionRespDto {
  String position;

  // Key : 팀이름 , Value : 선수명
  Map<String, String> playerNameMapByTeam;

  public static PositionRespDto of(String position) {
    return new PositionRespDto(position, new HashMap<>());
  }
}
