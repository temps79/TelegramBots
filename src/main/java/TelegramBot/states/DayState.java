package TelegramBot.states;

import TelegramBot.ability.Calendar;
import TelegramBot.ability.Weeks;
import TelegramBot.model.Bot;
import TelegramBot.service.MessageSender;
import lombok.SneakyThrows;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class DayState extends State{
    public DayState(Bot bot) {
        super(bot);
    }

    @SneakyThrows
    @Override
    public void message(Update update) {
        Message message=update.getMessage();
        if (Weeks.contains(message.getText())) {
            bot.sendQueue.add(sendMsgWeeks(message, Calendar.printTable(Calendar.getEvents(message.getText()))));
            if (update.getMessage().getChatId().toString().equals(MessageSender.getAdminChatId().getChatId()) ||
                    update.getMessage().getChatId().toString().equals(MessageSender.getAnnaChatId().getChatId())) {
                MessageSender.setTrue(message.getChatId().toString());
                MessageSender.setPrice(Calendar.getMoney(Calendar.getEvents(message.getText())) + ":" + message.getChatId().toString());
            }
            bot.sendQueue.add(sendInlineKeyBoardMessage(message.getChatId()));
        }
        else if(message.getText().equals(Calendar.getData().trim())){
            bot.getStateMap().put(message.getChatId().toString(),new TodayState(bot));
            bot.receiveQueue.add(update);
        }
        else {
            bot.getStateMap().put(message.getChatId().toString(),new ReadyState(bot));
            bot.receiveQueue.add(update);
        }
    }
    private SendMessage sendInlineKeyBoardMessage(long chatId) {
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
