package ru.asteises.rspgame.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.asteises.rspgame.mapper.PlayerMapper;
import ru.asteises.rspgame.model.dto.PlayerDto;
import ru.asteises.rspgame.util.CallbackData;
import ru.asteises.rspgame.util.Topic;

import java.util.List;

@Slf4j
@Service
public class Producer {

    private final KafkaTemplate<String, PlayerDto> kafkaTemplate;

    public Producer(KafkaTemplate<String, PlayerDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(List<Update> updates) {
        updates.forEach(update -> {
            if (update.hasCallbackQuery()) {
                CallbackQuery callbackQuery = update.getCallbackQuery();
                if (callbackQuery.getData().equals(CallbackData.REG)) {
                    PlayerDto playerDto = PlayerMapper.INSTANCE.callbackQueryToDto(callbackQuery);
                    kafkaTemplate.send(Topic.REGISTRATION, playerDto);
                    log.info("Produce topic: {} and message: {}", Topic.ALL, update);
                }
            }
        });
    }
}
