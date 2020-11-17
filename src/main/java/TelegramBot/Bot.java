package TelegramBot;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static TelegramBot.MyCalendar.getData;
import static TelegramBot.SelectedWeeks.selected;
import static TelegramBot.SheetsQuickstart.printTable;




public class Bot extends TelegramLongPollingBot {

    int status=0;
    @Setter
    @Getter
    String botName;
    @Setter
    @Getter
    String token;

    public Bot(String botName, String token) {
        this.botName=botName;
        this.token=token;
    }


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void botConnect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            botConnect();
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
        Message message=update.getMessage();
        MyCalendar calendar = new MyCalendar();
        if(update.hasMessage()) {
            if ((message.hasText() && status == 0) || (message.getText().equals("/start"))) {
                sendMsg(message, "*Выберите действие*");
                System.out.println(update.getMessage().getChatId());
                status = 1;
            } else if (message.getText().equals("Календарь \uD83D\uDCC5") && status == 1) {
                sendMsgWeeks(message, "Выберите день:");
                status = 2;
            } else if (message.getText().equals("Обо мне \uD83E\uDD16") && status == 1) {
                ReferenceURL(update);
                status = 1;
            } else if (message.getText().equals("ПН") || message.getText().equals("ВТ") || message.getText().equals("СР") || message.getText().equals("ЧТ") || message.getText().equals("ПТ") || message.getText().equals("СБ") || message.getText().equals("ВС") && status == 2) {
                try {
                    sendMsgWeeks(message, printTable(selected(message)));
                    execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                status = 2;
            } else if (message.hasText() && status == 2) {
                sendMsgWeeks(message, "Сегодня " + getData() + "\nВыберите день:");
                status = 2;
            }else if(message.hasText()&&status==3){
                Person.setName(message.getText());
                sendMessageTxt(message,"Желаемый день/время:");
                status=4;
            }else if(message.hasText()&&status==4){
                Person.setDate(message.getText());
                sendMessageTxt(message,"Ваши контакты для связи(Способ связи)?");
                status=5;
            }else if(message.hasText()&&status==5){
                Person.setNumber(message.getText());
                sendMsg(message,"Ваша заявка поступила в модерацию\nВ ближайщее время с вами свяжуться\nДля продолжения введите/нажмите [/start]");
                Chat chat=message.getChat();
                sendInfo(message,Person.getName()+" "+Person.getDate()+" "+Person.getNumber()+"|"+chat.getFirstName()+" "+chat.getLastName());
                status=1;
            }

        }else if(update.hasCallbackQuery()){
            try {
                execute(new SendMessage().setText(
                        "Как вас зовут?")
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
                status=3;
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }
    void sendMessageTxt(Message message,String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    void sendInfo(Message message,String text){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId("491099045");

        sendMessage.setText(text);
        System.out.println(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void ReferenceURL(Update update){
        SendMessage sendMessage=new SendMessage().setChatId(update.getMessage().getChatId()).setText("Обо мне \uD83E\uDD16 \n Селезнев Сергей Александрович \nРепетитор по информатике/программированию и математике \nЭтот бот позволяет узнать мое расписание занятий\n @temps799");
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
    }

    public void sendMsg (Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

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

    public void sendMsgWeeks(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        MyCalendar calendar = new MyCalendar();

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
        keyboardFirstRow.add("" + getData());

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        String[] Weeks = {"ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС"};
        for (int i = 0; i < 7; i++)
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

    public static SendMessage sendInlineKeyBoardMessage(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Записаться");
        inlineKeyboardButton1.setCallbackData("Запись");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setText("Нажмите кнопку \"Записаться\" и заполните форму").setReplyMarkup(inlineKeyboardMarkup);

    }

}