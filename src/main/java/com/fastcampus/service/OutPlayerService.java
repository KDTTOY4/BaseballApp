package com.fastcampus.service;

import com.fastcampus.dao.OutPlayerDao;
import com.fastcampus.dao.PlayerDao;
import com.fastcampus.dto.OutPlayerRespDto;
import com.fastcampus.enums.OutReason;

import java.util.ArrayList;
import java.util.List;

import com.fastcampus.exceptions.BaseballAppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutPlayerService {
  private final OutPlayerDao outPlayerDao;
  private final PlayerDao playerDao;

  public OutPlayerService(@Autowired OutPlayerDao outPlayerDao, @Autowired PlayerDao playerDao) {
    this.outPlayerDao = outPlayerDao;
    this.playerDao = playerDao;
  }

  public String registerOutPlayer(Integer playerId, OutReason reason) {
    if (playerDao.selectById(playerId).getTeamName() == null) {
      return "이미 퇴출된 선수입니다.";
    }

    try {
      outPlayerDao.insertOutPlayer(playerId, reason);
    } catch (BaseballAppException e) {
      return e.getMessage();
    }

    return "성공";
  }

  public List<OutPlayerRespDto> getOutPlayerList() {
    List<OutPlayerRespDto> outPlayerRespDtoList = new ArrayList<>();

    try {
      outPlayerRespDtoList = outPlayerDao.selectAll();
    } catch (BaseballAppException e) {
      System.out.println(e.getMessage());
    }

    return outPlayerRespDtoList;
  }
}
