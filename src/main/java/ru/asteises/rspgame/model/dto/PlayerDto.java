package ru.asteises.rspgame.model.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlayerDto {

    private UUID id;

    private String name;

    private String chatId;
}
