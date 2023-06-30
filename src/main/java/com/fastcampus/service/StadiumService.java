package com.fastcampus.service;

import com.fastcampus.dao.StadiumDao;
import com.fastcampus.dto.StadiumDto;
import com.fastcampus.exceptions.BaseballAppException;
import java.util.Collections;
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
    try {
      stadiumDao.insertStadium(name);
      return "성공";
    } catch (BaseballAppException e) {
      return e.getMessage();
    }
  }

  public List<StadiumDto> getStadiumList() {
    try {
      return stadiumDao.selectAll();
    } catch (BaseballAppException e) {
      System.out.println(e.getMessage());
      return Collections.emptyList();
    }
  }
}
