package com.fastcampus.service;

import com.fastcampus.dao.PlayerDao;
import com.fastcampus.dto.PlayerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerDao playerDao;

    public PlayerService(@Autowired PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public String registerPlayer(String name, String position, Integer teamId) {
        playerDao.insertPlayer(teamId, name, position);
        return "성공";
    }

    public List<PlayerDto> getPlayerList(Integer teamId) {return playerDao.selectAll(teamId);
    }
}
