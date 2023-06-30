package com.fastcampus.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StadiumDto {
  private Integer id;
  private String name;
  private LocalDateTime createdAt;

  public static StadiumDto of(Integer id, String name, Timestamp createdAt) {
    return new StadiumDto(id, name, createdAt.toLocalDateTime());
  }

  @Override
  public String toString() {
    return id + ", " + name + ", " + createdAt.toLocalDate();
  }
}
