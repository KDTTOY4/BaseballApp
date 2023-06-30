package com.fastcampus.exceptions.code;

import lombok.Getter;

@Getter
public enum AppErrorCode {
  INVALID_REQUEST_FORMAT("잘못된 요청 형식"),
  DB_EXCEPTION("Database 오류"),
  UNKNOWN_EXCEPTION("알 수 없는 오류"),
  UNEXPECTED_NULL("Null 은 허용하지 않습니다");

  private final String detailMessage;

  AppErrorCode(String detailMessage) {
    this.detailMessage = detailMessage;
  }
}
