package ru.asteises.rspgame.handler;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.asteises.rspgame.bot.BotCore;

@Service
@RequiredArgsConstructor
public class HandlerSender {

    private final BotCore botCore;

    public void sendMessage(SendMessage sendMessage) {
        botCore.sendMessage(sendMessage);
    }
}
