package com.fastcampus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Stadium {
  Integer id;
  String name;
  Timestamp createdAt;
}
