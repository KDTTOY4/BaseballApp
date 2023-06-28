package com.fastcampus.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class StadiumDto {
  private Integer id;
  private String name;
  private LocalDateTime createdAt;

  public static StadiumDto of(Integer id, String name, Timestamp createdAt) {
    return new StadiumDto(id, name, createdAt.toLocalDateTime());
  }
}
