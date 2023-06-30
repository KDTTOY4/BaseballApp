package com.fastcampus.exceptions;

import com.fastcampus.exceptions.code.AppErrorCode;

public class BaseballAppException extends RuntimeException {
  public BaseballAppException(AppErrorCode appErrorCode) {
    super(appErrorCode.getDetailMessage());
  }
}
