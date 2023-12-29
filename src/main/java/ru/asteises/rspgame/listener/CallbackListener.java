package ru.asteises.rspgame.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.asteises.rspgame.handler.HandlerService;
import ru.asteises.rspgame.model.dto.PlayerDto;
import ru.asteises.rspgame.util.Topic;

@Slf4j
@RequiredArgsConstructor
@Service
public class CallbackListener {

    private final HandlerService handlerService;

    @KafkaListener(topics = Topic.ALL, groupId = "firstGroup", containerFactory = "kafkaListenerContainerFactory")
    public void listenAll(@Payload Update update, @Headers MessageHeaders headers) {
        log.info("Received update: {}", update.toString());
        log.info("Received headers: {}", headers.toString());
        handlerService.updateHandle(update);
    }

    @KafkaListener(topics = Topic.REGISTRATION, groupId = "firstGroup", containerFactory = "kafkaListenerContainerFactory")
    public void listenRegistration(@Payload Update update, @Headers MessageHeaders headers) {
        log.info("Received update: {}", update.toString());
        log.info("Received headers: {}", headers.toString());
        handlerService.updateHandle(update);
    }

    @KafkaListener(topics = Topic.FIND_OPPONENT, groupId = "firstGroup", containerFactory = "kafkaListenerContainerFactory")
    public void listenFindOpponent(@Payload Update update, @Headers MessageHeaders headers) {
        log.info("Received update: {}", update.toString());
        log.info("Received headers: {}", headers.toString());
        handlerService.updateHandle(update);
    }
}
