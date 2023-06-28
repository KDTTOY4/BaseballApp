package com.fastcampus.service;

import com.fastcampus.dao.TeamDao;
import com.fastcampus.dto.TeamDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
  TeamDao teamDao;

  public TeamService(@Autowired TeamDao teamDao) {
    this.teamDao = teamDao;
  }

  public String registerTeam(Integer stadiumId, String name) {
    teamDao.insertTeam(stadiumId, name);
    return "성공";
  }

  public List<TeamDto> getTeamList() {
    return teamDao.selectAll();
  }
}
