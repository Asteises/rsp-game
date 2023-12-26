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
import java.util.stream.Collectors;

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

    @Override
    public List<PlayerDto> getFreePlayers() {
        List<Player> freePlayers = playerRepository.findAllByPlayingIsFalse();
        Collections.shuffle(freePlayers);
        return PlayerMapper.INSTANCE.toDto(freePlayers);
    }
}
