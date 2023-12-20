package ru.asteises.rspgame.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.asteises.rspgame.keyboard.RegisterKeyboard;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class CommandHandler {

    private final Map<String, String> commands;
    private final RegisterKeyboard registerKeyboard;

    public CommandHandler() {
        this.commands = new HashMap<>(Map.of("/start", "StartCommand"));
        registerKeyboard = new RegisterKeyboard();
    }

    public SendMessage handleCommands(Update update) {
        SendMessage message = new SendMessage();
        switch (update.getMessage().getText()) {
            case "/start" -> {
                message.setChatId(update.getMessage().getChatId());
                message.setText("Добро пожаловать!");
                message.setReplyMarkup(registerKeyboard.getRegisterKeyboard());
                return message;
            }
        }
        return null;
    }
}
