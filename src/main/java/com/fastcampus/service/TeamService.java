package com.fastcampus.service;

import com.fastcampus.dao.TeamDao;
import com.fastcampus.dto.PositionRespDto;
import com.fastcampus.dto.TeamDto;
import com.fastcampus.exceptions.BaseballAppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
  private final TeamDao teamDao;

  public TeamService(@Autowired TeamDao teamDao) {
    this.teamDao = teamDao;
  }

  public String registerTeam(Integer stadiumId, String name) {
    try {
      teamDao.insertTeam(stadiumId, name);
    } catch (BaseballAppException e) {
      return e.getMessage();
    }

    return "성공";
  }

  public List<TeamDto> getTeamList() {
    return teamDao.selectAll();
  }

  public List<PositionRespDto> getPositionList() {
    return teamDao.selectPlayerTablePositionRowTeamColumn();
  }
}
