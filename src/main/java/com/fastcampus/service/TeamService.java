package com.fastcampus.service;

import com.fastcampus.model.Team;
import com.fastcampus.repository.PlayerRepository;
import com.fastcampus.repository.StadiumRepository;
import com.fastcampus.repository.TeamRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    StadiumRepository stadiumRepository;
    TeamRepository teamRepository;
    PlayerRepository playerRepository;

    public MainService(
            @Autowired TeamRepository teamRepository,
            @Autowired StadiumRepository stadiumRepository,
            @Autowired PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.stadiumRepository = stadiumRepository;
        this.playerRepository = playerRepository;
    }

    public void execute(String[] args) {
        switch (args[0]) {
            // 야구장등록?name=잠실야구장
            case "야구장등록" -> {
                String name = args[1];
            }
            // 야구장목록
            case "야구장목록" -> {}
            case "팀등록" -> {
                System.out.println(registerTeam(Integer.parseInt(args[1]), args[2]));
            }
            case "팀목록" -> {
                getTeamList().forEach(System.out::println);
            }
            // 선수등록?teamId=1&name=이대호&position=1루수
            case "선수등록" -> {
                Integer teamId = Integer.parseInt(args[1]);
                String name = args[2];
                String position = args[3];
            }
            // 선수목록?teamId=1
            case "선수목록" -> {
                Integer teamId = Integer.parseInt(args[1]);
            }
            // 퇴출등록?playerId=1&reason=도박
            case "퇴출등록" -> {
                Integer playerId = Integer.parseInt(args[1]);
                String reason = args[2];
            }
            case "포지션별목록" -> {}
        }
    }

    private String registerTeam(Integer stadiumId, String name) {
        Team team = new Team(stadiumId, name);
        teamRepository.save(team);
        return "성공";
    }

    private List<Team> getTeamList() {
        return teamRepository.findAll();
    }
}
