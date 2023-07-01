package com.fastcampus.staidum.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Stadium {
  Integer id;
  @Setter String name;
  Timestamp createdAt;

  public static Stadium of(Integer id, String name, Timestamp createdAt) {
    return new Stadium(id, name, createdAt);
  }
}
