package com.fastcampus.service;

import com.fastcampus.dao.StadiumDao;
import com.fastcampus.dto.StadiumDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StadiumService {
  StadiumDao stadiumDao;

  public StadiumService(@Autowired StadiumDao stadiumDao) {
    this.stadiumDao = stadiumDao;
  }

  public String registerStadium(String name) {
    stadiumDao.insertStadium(name);

    return "성공";
  }

  public List<StadiumDto> getStadiumList() {
    return stadiumDao.selectAll();
  }
}
