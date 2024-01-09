package ru.asteises.rspgame.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HandlerService {

    private final CommandHandler commandHandler;
    private final CallbackHandler callbackHandler;
    private final HandlerSender sender;

    public void updateHandle(Update update) {
        List<SendMessage> result = new ArrayList<>();
        if (update.hasMessage() && update.getMessage().getText().startsWith("/")) {
            result.add(commandHandler.handleCommands(update));
        } else if (update.hasCallbackQuery()) {
            result = callbackHandler.handleCallbacks(update);
            sender.sendMessages(result);
        }
        sender.sendMessages(result);
    }
}
