package com.fastcampus.enums;

public enum OutReason {
    GAMBLING("도박"),
    VIOLENCE("폭행"),
    ETC("기타");

    private final String reason;

    OutReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public static OutReason fromStr(String reason) {
        if ("도박".equals(reason)) return GAMBLING;
        else if ("폭행".equals(reason)) return GAMBLING;
        else return ETC;
    }
}