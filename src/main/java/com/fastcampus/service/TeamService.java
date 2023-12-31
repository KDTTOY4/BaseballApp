package com.fastcampus.service;

import com.fastcampus.dao.TeamDao;
import com.fastcampus.dto.PositionRespDto;
import com.fastcampus.dto.TeamDto;
import com.fastcampus.exceptions.BaseballAppException;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
  private final TeamDao teamDao;

  public TeamService(@Autowired TeamDao teamDao) {
    this.teamDao = teamDao;
  }

  public String registerTeam(Integer stadiumId, String name) {
    try {
      teamDao.insertTeam(stadiumId, name);
      return "성공";
    } catch (BaseballAppException e) {
      return e.getMessage();
    }
  }

  public List<TeamDto> getTeamList() {
    try {
      return teamDao.selectAll();
    } catch (BaseballAppException e) {
      return Collections.emptyList();
    }
  }

  public List<PositionRespDto> getPositionList() {
    try {
      return teamDao.selectPlayerTablePositionRowTeamColumn();
    } catch (BaseballAppException e) {
      return Collections.emptyList();
    }
  }
}
