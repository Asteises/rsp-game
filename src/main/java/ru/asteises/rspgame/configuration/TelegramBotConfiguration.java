package ru.asteises.rspgame.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.asteises.rspgame.bot.BotCore;
import ru.asteises.rspgame.producer.Producer;

@Configuration
public class TelegramBotConfiguration {

    @Bean
    public BotCore bot(@Value("${telegram.bot.token}") String botToken,
                       Producer producer) {
        return new BotCore(producer);
    }

    @Bean
    public TelegramBotsApi botsApi(BotCore botCore) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(botCore);
        return telegramBotsApi;
    }

    @Bean
    ObjectMapper customObjectMapper() {
        return new ObjectMapper();
    }
}
