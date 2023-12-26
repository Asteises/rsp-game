package ru.asteises.rspgame.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.asteises.rspgame.keyboard.MarkupKeyboard;
import ru.asteises.rspgame.mapper.PlayerMapper;
import ru.asteises.rspgame.model.Player;
import ru.asteises.rspgame.model.dto.PlayerDto;
import ru.asteises.rspgame.service.PlayerService;
import ru.asteises.rspgame.util.ButtonText;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class CallbackHandler {

    private final PlayerService playerService;
    private final MarkupKeyboard markupKeyboard;

    public SendMessage handleCallbacks(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        SendMessage result = new SendMessage();
        String data = callbackQuery.getData();
        switch (data) {
            case "INFO" -> {
                result.setText("BLA BLA BLA");
//                result.setReplyMarkup(infoKeyboard.getInfoKeyboard());
                return result;
            }
            case "REG" -> {
                try {
                    Player player = playerService.createPlayer(
                            PlayerMapper.INSTANCE.callbackQueryToDto(callbackQuery));
                    result.setText(ButtonText.THANKS);
                } catch (DataIntegrityViolationException e) {
                    result.setText(ButtonText.PLAYER_ALREADY_EXIST);
                }
                result.setReplyMarkup(markupKeyboard.getMainKeyBoard());
                result.setChatId(update.getCallbackQuery().getMessage().getChatId());
                return result;
            }
            case "FIND_OPPONENT" -> {
                List<PlayerDto> freePlayers = playerService.getFreePlayers();
//                if (PlayersStorage.storage.size() == 1) {
//                    message.setText("К сожалению, других игроков сейчас нет");
//                    result.put(userChatId, message);
//                    return result;
//                } else {
//                    searchOpponent(message, userChatId);
//                }
//                result.put(userChatId, message);
//                return result;
            }
        }
        return result;
    }
}
