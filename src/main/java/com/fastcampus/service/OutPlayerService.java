package com.fastcampus.service;

import com.fastcampus.dao.OutPlayerDao;
import com.fastcampus.dto.OutPlayerRespDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutPlayerService {
  private final OutPlayerDao outPlayerDao;

  public OutPlayerService(@Autowired OutPlayerDao outPlayerDao) {
    this.outPlayerDao = outPlayerDao;
  }

  public String registerOutPlayer(Integer playerId, String reason) {
    outPlayerDao.insertOutPlayer(playerId, reason);

    return "성공";
  }

  public List<OutPlayerRespDto> getOutPlayerList() {
    return outPlayerDao.selectAll();
  }
}
