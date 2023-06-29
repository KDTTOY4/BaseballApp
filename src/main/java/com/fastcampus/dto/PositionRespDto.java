package com.fastcampus.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PositionRespDto {
  String position;

  // Key : 팀이름 , Value : 선수명
  Map<String, String> playerNameMapByTeam;

  public PositionRespDto(String position) {
    this.position = position;
    this.playerNameMapByTeam = new HashMap<>();
  }
}
