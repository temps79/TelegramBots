package TelegramBot.handler;

import TelegramBot.ability.Weeks;
import TelegramBot.model.Bot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import TelegramBot.ability.Calendar;



import java.io.IOException;
import java.security.GeneralSecurityException;

import java.util.*;

import static TelegramBot.handler.Action.getKeyboard;
import static TelegramBot.handler.Action.showReplyKeyboardMarkup;


public class defaultHandler extends HadlerAbstract {
    private static int status=0;

    public defaultHandler(Bot bot){ super(bot); }

    @Override
    public void operator(Update update) throws TelegramApiException, GeneralSecurityException, IOException {
        Message message=update.getMessage();
        if(update.hasMessage()) {
            if ((message.hasText() && status == 0) || (message.getText().equals("/start"))) {
                bot.execute(sendMsg(message, entryAction));
                status = 1;
            } else if (message.getText().equals(calendar) && status == 1) {
                bot.execute(sendMsgWeeks(message, entryDay));
                status = 2;
            } else if (message.getText().equals(infoMe) && status == 1) {
                bot.execute(ReferenceURL(update));
                status = 1;
            } else if (Weeks.contains(message.getText()) && status == 2) {
                bot.execute(sendMsgWeeks(message, Calendar.printTable(message.getText())));
                bot.execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
                status = 2;
            } else if (message.hasText() && status == 2) {
                bot.execute(sendMsgWeeks(message, today));
                status = 2;
            }else if(message.hasText()&&status==3){
                bot.sendSystemQueue.add(String.format("TelegramName:%s|%s\n",message.getChat().getFirstName(),message.getChat().getLastName()));
                bot.sendSystemQueue.add(message.getText());
                bot.execute(sendMessageTxt(message,selectedTime));
                status=4;
            }else if(message.hasText()&&status==4){
                bot.sendSystemQueue.add(message.getText());
                bot.execute(sendMessageTxt(message,contactInfo));
                status=5;
            }else if(message.hasText()&&status==5){
                bot.sendSystemQueue.add(message.getText());
                bot.execute(sendMsg(message,moderation));
                status=1;
            }

        }else if(update.hasCallbackQuery()){
                bot.execute(new SendMessage().setText(
                        "Как вас зовут?")
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
                status=3;
        }
    }
    private SendMessage sendMessageTxt(Message message, String text)  {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        return sendMessage;
    }

    private SendMessage ReferenceURL(Update update)  {
        SendMessage sendMessage=new SendMessage().setChatId(update.getMessage().getChatId()).setText("Обо мне \uD83E\uDD16 \n Селезнев Сергей Александрович \nРепетитор по информатике/программированию и математике \nЭтот бот позволяет узнать мое расписание занятий\n @temps799");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List <List<InlineKeyboardButton>> rowsInline = new ArrayList< >();
        List < InlineKeyboardButton > rowInline = new ArrayList < > ();

        rowInline.add(new InlineKeyboardButton().setText("Ссылка на профиль").setUrl("https://repetit.ru/repetitor.aspx?id=148297"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);
        return sendMessage;
    }

    private SendMessage sendMsg (Message message, String text)  {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(showReplyKeyboardMarkup(getKeyboard("Календарь \uD83D\uDCC5","\n","Обо мне \uD83E\uDD16")));
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        return sendMessage;

    }

    private SendMessage sendMsgWeeks(Message message, String text) throws TelegramApiException {
        DeleteMessage deleteMessage = new DeleteMessage(message.getChatId(), message.getMessageId());
        bot.execute(deleteMessage);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(showReplyKeyboardMarkup(getKeyboard(Calendar.getData(),"\n","ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС")));
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        return sendMessage;

    }

    private  SendMessage sendInlineKeyBoardMessage(long chatId) {
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
