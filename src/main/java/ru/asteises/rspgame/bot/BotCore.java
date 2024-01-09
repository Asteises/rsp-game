package ru.asteises.rspgame.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.asteises.rspgame.command.StartCommand;
import ru.asteises.rspgame.producer.Producer;

import java.util.List;

@Slf4j
@Component
public class BotCore extends TelegramLongPollingCommandBot {

    private final Producer producer;

    public BotCore(
            @Value("${telegram.bot.token}") String botToken,
            Producer producer) {

        super(botToken);
        this.producer = producer;

        register(new StartCommand("/start", "Start command"));
    }

    @Override
    public String getBotUsername() {
        return "ROCK-SCISSORS-PAPER-GAME";
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

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        log.info("update get 0: {}", updates.get(0));
        producer.sendMessages(updates);
    }

    public void sendMessages(List<SendMessage> messages) {
        try {
            for (var message : messages) {
                execute(message);
            }
        } catch (TelegramApiException e) {
            e.getStackTrace();
            log.error(e.getMessage());
        }
    }
}
