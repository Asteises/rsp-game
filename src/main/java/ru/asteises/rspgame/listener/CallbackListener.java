package ru.asteises.rspgame.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.asteises.rspgame.model.dto.PlayerDto;
import ru.asteises.rspgame.util.Topic;

@Slf4j
@Service
public class CallbackListener {

    @KafkaListener(topics = Topic.ALL, groupId = "firstGroup", containerFactory = "kafkaListenerContainerFactory")
    public void listenAll(@Payload Update update, @Headers MessageHeaders headers) {
        log.debug("Received update: {}", update.toString());
    }

    @KafkaListener(topics = Topic.REGISTRATION, groupId = "firstGroup", containerFactory = "kafkaListenerContainerFactory")
    public void listenRegistration(@Payload PlayerDto playerDto, @Headers MessageHeaders headers) {
        log.debug("Received playerDto: {}", playerDto.toString());
    }
}
