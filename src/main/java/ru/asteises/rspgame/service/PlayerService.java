package ru.asteises.rspgame.service;

import ru.asteises.rspgame.model.Player;
import ru.asteises.rspgame.model.dto.PlayerDto;

public interface PlayerService {

    Player createPlayer(PlayerDto playerDto);
}
