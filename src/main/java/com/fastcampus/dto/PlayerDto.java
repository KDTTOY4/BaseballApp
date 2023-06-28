package com.fastcampus.dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PlayerDto {
    private Integer id;
    private String name;
    private String position;
    private Timestamp createdAt;
    private String teamName;

    public PlayerDto(Integer id, String name, String position, Timestamp createdAt, String teamName) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.createdAt = createdAt;
        this.teamName = teamName;
    }
}
