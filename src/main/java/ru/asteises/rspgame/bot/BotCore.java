package ru.asteises.rspgame.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.asteises.rspgame.command.StartCommand;

import java.util.List;

@Slf4j
//@RequiredArgsConstructor
@Component
public class BotCore extends TelegramLongPollingCommandBot {

    //    public BotCore(@Value("${telegram.bot.token}") String botToken) {

    public BotCore() {
//        super(botToken);

        register(new StartCommand("/start", "Start command"));
    }

    @Override
    public String getBotUsername() {
        return "ROCK-SCISSORS-PAPER-GAME";
    }

    @Override
    public String getBotToken() {
        return "6524265011:AAHs7hScYcMcswKdi6YXT5JvHSxeRgCc-NI";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void processNonCommandUpdate(Update update) {

    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        super.processInvalidCommandUpdate(update);
    }

    @Override
    public boolean filter(Message message) {
        return super.filter(message);
    }

    // принимать апдейты в kafka
    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (var up : updates) {
            log.info("update: {}", up);
        }
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.getStackTrace();
            log.error(e.getMessage());
        }
    }
}
