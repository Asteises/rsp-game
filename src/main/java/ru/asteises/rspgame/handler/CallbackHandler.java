package ru.asteises.rspgame.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.asteises.rspgame.mapper.PlayerMapper;
import ru.asteises.rspgame.model.Player;
import ru.asteises.rspgame.service.PlayerService;

@Slf4j
@RequiredArgsConstructor
@Component
public class CallbackHandler {

    private final PlayerService playerService;

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
                Player player;
                try {
                    player = playerService.createPlayer(
                            PlayerMapper.INSTANCE.callbackQueryToDto(callbackQuery));
                    result.setText("Благодарим за регистрацию");
                } catch (DataIntegrityViolationException e) {
                    result.setText("Такой игрок уже существует");
                }
//                result.setReplyMarkup(mainBoard.getMainKeyBoard());
                result.setChatId(update.getCallbackQuery().getMessage().getChatId());
                return result;
            }
            case "FIND_OPPONENT" -> {
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
