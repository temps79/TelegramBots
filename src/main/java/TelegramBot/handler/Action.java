package TelegramBot.handler;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Action {
    public static ReplyKeyboardMarkup showReplyKeyboardMarkup(List<KeyboardRow> keyboardRows){
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;

    }

    public static List<KeyboardRow> getKeyboard(String... params){
        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow=new KeyboardRow();
        for(int i=0;i<params.length;i++){
            // Добавляем все строчки клавиатуры в список
            if(params[i].equals("\n")) {
                keyboard.add(keyboardRow);
                keyboardRow = new KeyboardRow();
            }
            else {
                keyboardRow.add(params[i]);
            }
        }
        keyboard.add(keyboardRow);
        return keyboard;
    }
}
