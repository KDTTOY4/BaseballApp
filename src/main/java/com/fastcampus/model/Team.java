package com.fastcampus.model;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Team {
    private Integer id;
    private Integer stadiumId;
    private String name;
    private Timestamp createdAt;

    public Team(int stadiumId, String name) {
        this.stadiumId = stadiumId;
        this.name = name;
    }
}
