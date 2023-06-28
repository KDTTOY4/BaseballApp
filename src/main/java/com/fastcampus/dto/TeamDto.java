package com.fastcampus.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter@Setter
public class TeamDto {
    private Integer id;
    private String name;
    private Timestamp createdAt;
    private String stadiumName;

    public TeamDto(Integer id, String name, Timestamp createdAt, String stadiumName) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.stadiumName = stadiumName;
    }
}