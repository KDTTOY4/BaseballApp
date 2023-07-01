package com.fastcampus.player.service;

import com.fastcampus.player.dao.PlayerDao;
import com.fastcampus.player.dto.PlayerDto;
import com.fastcampus.exceptions.BaseballAppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PlayerService {
    private final PlayerDao playerDao;

    public PlayerService(@Autowired PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public String registerPlayer(String name, String position, Integer teamId) {
        try {
            playerDao.insertPlayer(teamId, name, position);
            return "성공";
        } catch (BaseballAppException e) {
            return e.getMessage();
        }
    }

    public List<PlayerDto> getPlayerList(Integer teamId) {
        try {
            return playerDao.selectAll(teamId);
        } catch (BaseballAppException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
