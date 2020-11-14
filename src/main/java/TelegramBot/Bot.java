package TelegramBot;

import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.swing.text.TabableView;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.ArrayList;
import java.util.List;


import static TelegramBot.MyCalendar.printTableOnDay;
import static TelegramBot.SheetsQuickstart.printTable;

public  class Bot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init(); // Инициализируем апи
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if(update.hasMessage()) {
            if (update.getMessage().hasText()) {
                switch (message.getText()) {
                    case "/start":
                        sendMsg(message,"Нажмите на кнопку");
                        System.out.println(message.getText());
                        break;
                    case "Календарь \uD83D\uDCC5":
                        sendMsgWeeks(message,"Выберите день");
                        System.out.println(message.getText());
                        break;
                    case "Обо мне \uD83E\uDD16":
                        SendMessage sendMessage=new SendMessage().setChatId(update.getMessage().getChatId()).setText("Обо мне \uD83E\uDD16 \nСелезнев Сергей Александрович \nРепетитор по информатике/программированию и математике \nЭтот бот позволяет узнать мое расписание занятий");
                        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                        List < List < InlineKeyboardButton >> rowsInline = new ArrayList < > ();
                        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
                        rowInline.add(new InlineKeyboardButton().setText("Ссылка на профиль").setUrl("https://repetit.ru/repetitor.aspx?id=148297"));
                        rowsInline.add(rowInline);
                        markupInline.setKeyboard(rowsInline);
                        sendMessage.setReplyMarkup(markupInline);
                        try {
                            execute(sendMessage); // Sending our message object to user
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "ПН":
                        try {
                            sendMsg(message,printTable("Понедельник"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ВТ":
                        try {
                            sendMsg(message,printTable("Вторник"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "СР":
                        try {
                            sendMsg(message,printTable("Среда"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ЧТ":
                        try {
                            sendMsg(message,printTable("Четверг"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ПТ":
                        try {
                            sendMsg(message,printTable("Пятница"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "СБ":
                        try {
                            sendMsg(message,printTable("Суббота"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "ВС":
                        try {
                            sendMsg(message,printTable("Воскресенье"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (GeneralSecurityException e) {
                            e.printStackTrace();
                        }
                        break;

                    default:
                        sendMsg(message, "Нажмите на кнопку");
                        System.out.println(message.getText());
                        break;
                }

            }

        }else if(update.hasCallbackQuery()) {
            try {
                execute(new SendMessage().setText(
                        update.getCallbackQuery().getData())
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }



    public String getBotUsername() {
        return "TableBot"; //   //возвращаем юзера
    }

    public String getBotToken() {
        return "1451992685:AAEmF5nIGLrFGY11xQsdceyTPT_sr7j6WBw"; // токен бота
    }

    @SuppressWarnings("deprecation") // Означает то, что в новых версиях метод уберут или заменят

    public void sendMsg (Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("Календарь \uD83D\uDCC5");

        KeyboardRow keyboardSecondRow = new KeyboardRow();

        keyboardSecondRow.add("Обо мне \uD83E\uDD16");



        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("deprecation") // Означает то, что в новых версиях метод уберут или заменят


    public void sendMsgWeeks (Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        MyCalendar calendar=new MyCalendar();

        // Создаем клавиуатуру

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(""+calendar.getData());

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        String[] Weeks={"ПН","ВТ","СР","ЧТ","ПТ","СБ","ВС"};
        for(int i=0;i<7;i++)
        // Добавляем кнопки в первую строчку клавиатуры
            keyboardSecondRow.add(Weeks[i]);

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message.getChatId().toString());

        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}
