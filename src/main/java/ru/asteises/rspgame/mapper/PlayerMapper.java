package ru.asteises.rspgame.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.asteises.rspgame.model.Player;
import ru.asteises.rspgame.model.dto.PlayerDto;

import java.util.UUID;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD,
        imports = {UUID.class})
public interface PlayerMapper {

    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    Player toEntity(PlayerDto playerDto);

    PlayerDto toDto(Player player);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "callbackQuery.from.userName")
    @Mapping(target = "chatId", source = "callbackQuery.message.chat.id")
    PlayerDto callbackQueryToDto(CallbackQuery callbackQuery);
}
