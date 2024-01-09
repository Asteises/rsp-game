package ru.asteises.rspgame.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Arrays;

@Slf4j
@Service
public class CallBackDataParser {

    //FIXME Problem here
    public String getDataFromCallback(CallbackQuery callbackQuery) {
        String callback = callbackQuery.getData();
        log.info("callback: {}", callback);
        if (callback.contains(CallbackData.DELIMITER)) {
            String[] dataArray = callback.split(CallbackData.DELIMITER);
            log.info("dataArray: {}", Arrays.toString(dataArray));
            log.info("data from callback: {}", dataArray[0]);
            return dataArray[0];
//            playerId = UUID.fromString(dataArray[1]);
        } else {
            return callbackQuery.getData();
        }
    }
}
