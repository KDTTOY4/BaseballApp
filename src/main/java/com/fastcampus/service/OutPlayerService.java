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
    String formattedReason = formatReason(reason);
    OutPlayerDao.Reason enumReason = OutPlayerDao.Reason.valueOf(formattedReason);
    outPlayerDao.insertOutPlayer(playerId, enumReason);

    return "성공";
  }

  private String formatReason(String reason) {
    if (reason.equalsIgnoreCase("도박")) {
      return "GAMBLING";
    } else if (reason.equalsIgnoreCase("개인사유")) {
      return "VIOLENCE";
    } else {
      return "ETC";
    }
  }

  public List<OutPlayerRespDto> getOutPlayerList() {
    return outPlayerDao.selectAll();
  }
}
