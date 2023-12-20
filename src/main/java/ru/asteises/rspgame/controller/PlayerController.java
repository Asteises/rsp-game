package ru.asteises.rspgame.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.asteises.rspgame.mapper.PlayerMapper;
import ru.asteises.rspgame.model.Player;
import ru.asteises.rspgame.model.dto.PlayerDto;
import ru.asteises.rspgame.service.PlayerService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/player")
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto playerDto) {
        Player player = playerService.createPlayer(playerDto);
        return new ResponseEntity<>(PlayerMapper.INSTANCE.toDto(player), HttpStatus.CREATED);
    }
}
