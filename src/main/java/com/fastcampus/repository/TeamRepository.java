package com.fastcampus.repository;

import com.fastcampus.dao.TeamDao;
import com.fastcampus.model.Team;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeamRepository {
    private final TeamDao teamDao;

    public TeamRepository(@Autowired TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    public void save(Team team) {
        teamDao.insertTeam(team.getStadiumId(), team.getName());
    }

    public List<Team> findAll() {
        return teamDao.selectAll();
    }
}
