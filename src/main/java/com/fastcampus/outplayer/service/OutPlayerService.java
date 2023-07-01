package com.fastcampus.outplayer.service;

import com.fastcampus.outplayer.dao.OutPlayerDao;
import com.fastcampus.player.dao.PlayerDao;
import com.fastcampus.outplayer.dto.OutPlayerRespDto;
import com.fastcampus.outplayer.enums.OutReason;
import com.fastcampus.exceptions.BaseballAppException;
import java.util.Collections;
import java.util.List;
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
    try {
      if (playerDao.selectById(playerId).getTeamName() == null) return "이미 퇴출된 선수입니다.";

      outPlayerDao.insertOutPlayer(playerId, reason);
    } catch (BaseballAppException e) {
      return e.getMessage();
    }

    return "성공";
  }

  public List<OutPlayerRespDto> getOutPlayerList() {
    try {
      return outPlayerDao.selectAll();
    } catch (BaseballAppException e) {
      System.out.println(e.getMessage());
      return Collections.emptyList();
    }
  }
}
