package ru.asteises.rspgame.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.asteises.rspgame.util.ButtonText;
import ru.asteises.rspgame.util.CallbackData;

import java.util.ArrayList;
import java.util.List;

@Component
public class MarkupKeyboard implements ReplyKeyboard {

    public ReplyKeyboard getRegisterKeyboard() {

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        InlineKeyboardButton infoButton = new InlineKeyboardButton();
        InlineKeyboardButton registerButton = new InlineKeyboardButton();

        infoButton.setText(ButtonText.ABOUT);
        infoButton.setCallbackData(CallbackData.INFO);

        registerButton.setText(ButtonText.BECOME_A_MEMBER);
        registerButton.setCallbackData(CallbackData.REG);

        keyboardButtonsRow.add(infoButton);
        keyboardButtonsRow.add(registerButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);
        keyboard.setKeyboard(rowList);

        return keyboard;
    }

    public ReplyKeyboard getMainKeyBoard() {

        InlineKeyboardMarkup mainKeyboard = new InlineKeyboardMarkup();

        InlineKeyboardButton findAnOpponentButton = new InlineKeyboardButton();
        findAnOpponentButton.setText(ButtonText.FIND_OPPONENT);
        findAnOpponentButton.setCallbackData(CallbackData.FIND_OPPONENT);

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(findAnOpponentButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(row);
        mainKeyboard.setKeyboard(rowList);

        return mainKeyboard;
    }
}
