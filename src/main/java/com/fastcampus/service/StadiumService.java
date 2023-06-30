package com.fastcampus.service;

import com.fastcampus.dao.StadiumDao;
import com.fastcampus.dto.StadiumDto;
import com.fastcampus.exceptions.BaseballAppException;
import java.util.ArrayList;
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
    } catch (BaseballAppException e) {
      return e.getMessage();
    }

    return "성공";
  }

  public List<StadiumDto> getStadiumList() {
    List<StadiumDto> stadiumDtoList;

    try {
      stadiumDtoList = stadiumDao.selectAll();
    } catch (BaseballAppException e) {
      System.out.println(e.getMessage());
      stadiumDtoList = new ArrayList<>();
    }

    return stadiumDtoList;
  }
}
