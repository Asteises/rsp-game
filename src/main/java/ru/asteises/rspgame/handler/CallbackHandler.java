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
import ru.asteises.rspgame.service.PlayerService;
import ru.asteises.rspgame.util.ButtonText;
import ru.asteises.rspgame.util.CallbackData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class CallbackHandler {

    private final PlayerService playerService;
    private final MarkupKeyboard markupKeyboard;

    public List<SendMessage> handleCallbacks(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        List<SendMessage> result = new ArrayList<>();
        SendMessage message = new SendMessage();
        String data = callbackQuery.getData();
        UUID playerId = UUID.randomUUID();
        if (data.contains(CallbackData.DELIMITER)) {
            String[] dataArray = data.split(CallbackData.DELIMITER);
            data = dataArray[0];
            playerId = UUID.fromString(dataArray[1]);
        }
        log.info("data: {}", data);
        log.info("playerId: {}", playerId);
        switch (data) {
            case "INFO" -> {
                message.setText("BLA BLA BLA");
//                message.setReplyMarkup(infoKeyboard.getInfoKeyboard());
                result.add(message);
                return result;
            }
            case "REG" -> {
                try {
                    Player player = playerService.createPlayer(
                            PlayerMapper.INSTANCE.callbackQueryToDto(callbackQuery));
                    message.setText(ButtonText.THANKS);
                    message.setReplyMarkup(markupKeyboard.getMainKeyBoard(player));
                    message.setChatId(update.getCallbackQuery().getMessage().getChatId());

                } catch (DataIntegrityViolationException e) {
                    message.setText(ButtonText.PLAYER_ALREADY_EXIST);
                }
                result.add(message);
                return result;
            }
            case "FIND_OPPONENT" -> {
                Map<UUID, Player> opponents = playerService.getOpponents(playerId);

                for (var opponent : opponents.values()) {
                    log.info("opponent: {}", opponent);
                }

                if (opponents.size() == 1) {
                    message.setText("К сожалению, других игроков сейчас нет");
                    message.setChatId(opponents.get(playerId).getChatId());
                    result.add(message);
                    return result;
                } else {
                    result = getMessagesToOpponents(playerId, opponents);
                }
                return result;
            }
        }
        return result;
    }

    private List<SendMessage> getMessagesToOpponents(UUID playerId, Map<UUID, Player> opponents) {
        List<SendMessage> result = new ArrayList<>();
        Player player = opponents.get(playerId);
        for (var opponentId : opponents.keySet()) {
            Player opponent = opponents.get(opponentId);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(opponent.getChatId());
            sendMessage.setText("Желаете сыграть?");
            sendMessage.setReplyMarkup(markupKeyboard.getYesOrNoKeyboard(player, opponent));
            result.add(sendMessage);
        }
        return result;
    }
}
