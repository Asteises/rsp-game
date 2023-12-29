package ru.asteises.rspgame.service;

import ru.asteises.rspgame.model.Player;
import ru.asteises.rspgame.model.dto.PlayerDto;

import java.util.Map;
import java.util.UUID;

public interface PlayerService {

    Player createPlayer(PlayerDto playerDto);

    Map<UUID, Player> getOpponents(UUID playerId);
}
