package ru.asteises.rspgame.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.asteises.rspgame.command.StartCommand;
import ru.asteises.rspgame.handler.CallbackHandler;
import ru.asteises.rspgame.handler.CommandHandler;

import java.util.List;

@Slf4j
//@RequiredArgsConstructor
@Component
public class BotCore extends TelegramLongPollingCommandBot {

    private final CommandHandler commandHandler;
    private final CallbackHandler callbackHandler;
    private KafkaTemplate<String, Update> kafkaTemplate;

    //    public BotCore(@Value("${telegram.bot.token}") String botToken) {

    public BotCore(CommandHandler commandHandler, CallbackHandler callbackHandler) {
        this.commandHandler = commandHandler;
        this.callbackHandler = callbackHandler;
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
        for (var update : updates) {
            updateHandle(update);
        }
    }

    private void updateHandle(Update update) {
        SendMessage result = new SendMessage();
        if (update.hasMessage() && update.getMessage().getText().startsWith("/")) {
            result = commandHandler.handleCommands(update);
        } else if (update.hasCallbackQuery()) {
            result = callbackHandler.handleCallbacks(update);
            sendMessage(result);
        }
        sendMessage(result);
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
