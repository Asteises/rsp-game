package ru.asteises.rspgame.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.asteises.rspgame.mapper.PlayerMapper;
import ru.asteises.rspgame.model.Player;
import ru.asteises.rspgame.model.dto.PlayerDto;
import ru.asteises.rspgame.repository.PlayerRepository;
import ru.asteises.rspgame.service.PlayerService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public Player createPlayer(PlayerDto playerDto) {
        Player player = PlayerMapper.INSTANCE.toEntity(playerDto);
        playerRepository.save(player);
        return player;
    }

    public Map<UUID, Player> getOpponents(UUID playerId) {
        List<Player> freePlayers = getFreePlayers();
        Map<UUID, Player> freePlayersById = freePlayers.stream()
                .collect(toMap(Player::getId, Function.identity()));
        Player player = freePlayersById.get(playerId);
        setPlayerSearchingIsOn(player);
        return null;
    }

    public List<Player> getFreePlayers() {
        List<Player> freePlayers = playerRepository.findAllByPlayingIsFalseAndSearchingIsFalse();
        Collections.shuffle(freePlayers);
        return freePlayers;
    }

    private void setPlayerSearchingIsOn(Player player) {
        player.setSearching(true);
        playerRepository.save(player);
    }
}
