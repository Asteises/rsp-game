package ru.asteises.rspgame.service;

import ru.asteises.rspgame.model.Player;
import ru.asteises.rspgame.model.dto.PlayerDto;

import java.util.List;

public interface PlayerService {

    Player createPlayer(PlayerDto playerDto);

    List<PlayerDto> getFreePlayers();
}
