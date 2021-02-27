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
        System.out.println(update.getMessage().getChatId());
        if(update.hasMessage()) {
            if ((message.hasText() && status == 0) || (message.getText().equals("/start"))) {
                bot.sendQueue.add(sendMsg(message,entryAction));
                status = 1;
            } else if (message.getText().equals(calendar) && status == 1) {
                bot.sendQueue.add(sendMsgWeeks(message,entryDay));
                status = 2;
            } else if (message.getText().equals(infoMe) && status == 1) {
                bot.sendQueue.add(ReferenceURL(update));
                status = 1;
            } else if (Weeks.contains(message.getText()) && status == 2) {
                bot.sendQueue.add(sendMsgWeeks(message,Calendar.printTable(Calendar.getEvents(message.getText()))));
                bot.sendSystemQueue.add(Calendar.getMoney(Calendar.getEvents(message.getText())));
                bot.sendQueue.add(sendInlineKeyBoardMessage(message.getChatId()));
                status = 2;
            } else if (message.hasText() && status == 2) {
                bot.sendQueue.add(sendMsgWeeks(message,today));
                status = 2;
            }else if(message.hasText()&&status==3){
                bot.sendSystemQueue.add(String.format("TelegramName: %s | %s\nИмя:", message.getChat().getFirstName(),
                        message.getChat().getLastName()==null?"":message.getChat().getLastName()));
                bot.sendSystemQueue.add(message.getText()+"\nВремя:");
                bot.sendQueue.add(sendMessageTxt(message,selectedTime));
                status=4;
            }else if(message.hasText()&&status==4){
                bot.sendSystemQueue.add(message.getText()+"\nКонтакты:");
                bot.sendQueue.add(sendMessageTxt(message,contactInfo));
                status=5;
            }else if(message.hasText()&&status==5){
                bot.sendSystemQueue.add(message.getText());
                bot.sendQueue.add(sendMsg(message,moderation));
                status=1;
            }

        }else if(update.hasCallbackQuery()){
                bot.execute(new SendMessage().setText(
                        "Как вас зовут?")
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
                status=3;
        }
    }
    private SendMessage sendMessageHelper(Message message) throws TelegramApiException {
        if(status<3) bot.execute(new DeleteMessage(message.getChatId(), message.getMessageId()));
        return new SendMessage().setChatId(message.getChatId().toString()).enableMarkdown(true);
    }

    private SendMessage sendMessageTxt(Message message, String text) throws TelegramApiException {
        return sendMessageHelper(message).
                setText(text);
    }

    private SendMessage ReferenceURL(Update update) throws TelegramApiException {
        bot.execute(new DeleteMessage(update.getMessage().getChatId(), update.getMessage().getMessageId()));

        SendMessage sendMessage=new SendMessage().setChatId(update.getMessage().getChatId())
                .setText("Обо мне \uD83E\uDD16 \n Селезнев Сергей Александрович \n" +
                        "Репетитор по информатике/программированию и математике \n" +
                        "Этот бот позволяет узнать мое расписание занятий\n @temps799");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List <List<InlineKeyboardButton>> rowsInline = new ArrayList< >();
        List < InlineKeyboardButton > rowInline = new ArrayList < > ();

        rowInline.add(new InlineKeyboardButton().setText("Ссылка на профиль")
                .setUrl("https://repetit.ru/repetitor.aspx?id=148297"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);
        return sendMessage;
    }

   private SendMessage sendMsg (Message message, String text) throws TelegramApiException {
        return sendMessageHelper(message).
                setText(text).
                setReplyMarkup(showReplyKeyboardMarkup(getKeyboard("Календарь \uD83D\uDCC5","\n", "Обо мне \uD83E\uDD16")));

    }


    private SendMessage sendMsgWeeks(Message message, String text) throws TelegramApiException {
        return sendMessageHelper(message).
                setText(text).
                setReplyMarkup(showReplyKeyboardMarkup(getKeyboard(Calendar.getData(),"\n","ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС")));

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
