package TelegramBot.handler;

import TelegramBot.model.Bot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import TelegramBot.ability.Calendar;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;


public class defaultHandler extends HadlerAbstract {
    private static int status=0;
    public defaultHandler(Bot bot) {
        super(bot);
    }

    private final String entryAction="*Выберите действие*";
    private final String entryDay="Выберите день:";
    private final String infoMe="Обо мне \uD83E\uDD16";
    private final String calendar="Календарь \uD83D\uDCC5";
    private final String today="Сегодня " + Calendar.getData() + "\nВыберите день:";
    private final String selectedTime="Желаемый день/время:";
    private final String contactInfo="Ваши контакты для связи(Способ связи)?";
    private final String moderation="Ваша заявка поступила в модерацию\nВ ближайщее время с вами свяжуться\nДля продолжения введите/нажмите [/start]";
    private  Map<String,String> dayOfWeeks= new HashMap<>();
    {
        dayOfWeeks.put("ПН","Понедельник");
        dayOfWeeks.put("ВТ","Вторник");
        dayOfWeeks.put("СР","Среда");
        dayOfWeeks.put("ЧТ","Четверг");
        dayOfWeeks.put("ПТ","Пятница");
        dayOfWeeks.put("СБ","Суббота");
        dayOfWeeks.put("ВС","Воскресенье");
    }

    @Override
    public void operator(Update update) throws TelegramApiException, GeneralSecurityException, IOException {
        Message message=update.getMessage();
        if(update.hasMessage()) {
            if ((message.hasText() && status == 0) || (message.getText().equals("/start"))) {
                sendMsg(message, entryAction);
                status = 1;
            } else if (message.getText().equals(calendar) && status == 1) {
                sendMsgWeeks(message, entryDay);
                status = 2;
            } else if (message.getText().equals(infoMe) && status == 1) {
                ReferenceURL(update);
                status = 1;
            } else if (dayOfWeeks.containsKey(message.getText())&& status == 2) {
                sendMsgWeeks(message, Calendar.printTable(dayOfWeeks.get(message.getText())));
                bot.execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                status = 2;
            } else if (message.hasText() && status == 2) {
                sendMsgWeeks(message, today);
                status = 2;
            }else if(message.hasText()&&status==3){
                bot.sendSystemQueue.add(String.format("TelegramName:%s|%s\n",message.getChat().getFirstName(),message.getChat().getLastName()));
                bot.sendSystemQueue.add(message.getText());
                sendMessageTxt(message,selectedTime);
                status=4;
            }else if(message.hasText()&&status==4){
                bot.sendSystemQueue.add(message.getText());
                sendMessageTxt(message,contactInfo);
                status=5;
            }else if(message.hasText()&&status==5){
                bot.sendSystemQueue.add(message.getText());
                sendMsg(message,moderation);
                status=1;
            }

        }else if(update.hasCallbackQuery()){
                bot.execute(new SendMessage().setText(
                        "Как вас зовут?")
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
                status=3;


        }
    }
    private void sendMessageTxt(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            bot.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }



    private void ReferenceURL(Update update){
        SendMessage sendMessage=new SendMessage().setChatId(update.getMessage().getChatId()).setText("Обо мне \uD83E\uDD16 \n Селезнев Сергей Александрович \nРепетитор по информатике/программированию и математике \nЭтот бот позволяет узнать мое расписание занятий\n @temps799");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List <List<InlineKeyboardButton>> rowsInline = new ArrayList< >();
        List < InlineKeyboardButton > rowInline = new ArrayList < > ();
        rowInline.add(new InlineKeyboardButton().setText("Ссылка на профиль").setUrl("https://repetit.ru/repetitor.aspx?id=148297"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);
        try {
            bot.sendMessage(sendMessage); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg (Message message, String text) {
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
            bot.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsgWeeks(Message message, String text) {
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
        keyboardFirstRow.add("" + Calendar.getData());

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
            bot.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }

    private static SendMessage sendInlineKeyBoardMessage(long chatId) {
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
