package TelegramBot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;



public class SelectedWeeks {
    public static String selected(Message message) {
        String result="";
        switch (message.getText()) {
            case "ПН":
                result+="Понедельник";
                break;
            case "ВТ":
                result+="Вторник";
                break;
            case "СР":
                result+="Среда";
                break;
            case "ЧТ":
                result+="Четверг";
                break;
            case "ПТ":
                result+="Пятница";
                break;
            case "СБ":
                result+="Суббота";
                break;
            case "ВС":
                result+="Воскресенье";
                break;
        }
        return result;
    }

}