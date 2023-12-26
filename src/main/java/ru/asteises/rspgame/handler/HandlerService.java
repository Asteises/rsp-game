package ru.asteises.rspgame.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class HandlerService {

    private final CommandHandler commandHandler;
    private final CallbackHandler callbackHandler;

    private final HandlerSender sender;

    public void updateHandle(Update update) {
        SendMessage result = new SendMessage();
        if (update.hasMessage() && update.getMessage().getText().startsWith("/")) {
            result = commandHandler.handleCommands(update);
        } else if (update.hasCallbackQuery()) {
            result = callbackHandler.handleCallbacks(update);
            sender.sendMessage(result);
        }
        sender.sendMessage(result);
    }
}
