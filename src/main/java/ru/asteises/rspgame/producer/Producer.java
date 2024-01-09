package ru.asteises.rspgame.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.asteises.rspgame.util.CallBackDataParser;
import ru.asteises.rspgame.util.CallbackData;
import ru.asteises.rspgame.util.Topic;

import java.util.List;

@Slf4j
@Service
public class Producer {

    private final KafkaTemplate<String, Update> kafkaTemplate;
    private final CallBackDataParser callBackDataParser;

    public Producer(KafkaTemplate<String, Update> kafkaTemplate, CallBackDataParser callBackDataParser) {
        this.kafkaTemplate = kafkaTemplate;
        this.callBackDataParser = callBackDataParser;
    }

    public void sendMessages(List<Update> updates) {
        updates.forEach(update -> {
            if (update.hasMessage()) {
                if (update.getMessage().getText().startsWith("/")) {
                    kafkaTemplate.send(Topic.ALL, update);
                    log.info("Produce topic: {} and message: {}", Topic.ALL, update);
                }
            }
            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String data = callBackDataParser.getDataFromCallback(callbackQuery);

                switch (data) {
                    case CallbackData.REG -> {
                        kafkaTemplate.send(Topic.REGISTRATION, update);
                        log.info("Produce topic: {} and message: {}", Topic.REGISTRATION, update);
                    }
                    case CallbackData.FIND_OPPONENT -> {
                        kafkaTemplate.send(Topic.FIND_OPPONENT, update);
                        log.info("Produce topic: {} and message: {}", Topic.FIND_OPPONENT, update);
                    }
                }
            }
        });
    }
}
