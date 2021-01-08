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
import java.util.Calendar;
import java.util.List;


//Класс выбора недели
public class SelectedWeeks {
    //Определения дня недели для вывода в сообщении
    private  java.util.Calendar c=java.util.Calendar.getInstance();
    private  int statusDay=0;
    public  Calendar getCalendar(){
        return getPrivateCalendar(statusDay);
    }
    private  Calendar getPrivateCalendar(int statusDay) {
        java.util.Calendar temp= java.util.Calendar.getInstance();
        c=java.util.Calendar.getInstance();
        switch(statusDay){
            case 1:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
                break;
            case 2:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.TUESDAY);
                break;
            case 3:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.WEDNESDAY);
                break;
            case 4:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.THURSDAY);
                break;
            case 5:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.FRIDAY);
                break;
            case 6:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SATURDAY);
                break;
            case 0:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SUNDAY);
                break;
        }
        if(temp.after(c))
            c.add(java.util.Calendar.DATE,7);
        return c;
    }

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
    public  int getStatusDay(String day){
        switch(day){
            case "Понедельник":
                statusDay=1;
                break;
            case "Вторник":
                statusDay=2;
                break;
            case "Среда":
                statusDay=3;
                break;
            case "Четверг":
                statusDay=4;
                break;
            case "Пятница":
                statusDay=5;
                break;
            case "Суббота":
                statusDay=6;
                break;
            case "Воскресенье":
                statusDay=0;
                break;

        }
        return statusDay;
    }

}