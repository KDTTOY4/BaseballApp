package com.fastcampus.staidum.model;

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
