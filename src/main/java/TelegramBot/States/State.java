package TelegramBot.States;

import TelegramBot.ability.Calendar;
import TelegramBot.model.Bot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static TelegramBot.handler.Action.getKeyboard;
import static TelegramBot.handler.Action.showReplyKeyboardMarkup;

public abstract class State {
    protected Bot bot;

    public State(Bot bot){
        this.bot=bot;
    }

    protected String entryAction="*Выберите действие*";
    protected String entryDay="Выберите день:";
    protected String infoMe="Обо мне \uD83E\uDD16";
    protected String calendar="Календарь \uD83D\uDCC5";
    protected String today="Сегодня " + Calendar.getData() + "\nВыберите день:";
    protected String selectedTime="Желаемый день/время:";
    protected String contactInfo="Ваши контакты для связи(Способ связи)?";
    protected String moderation="Ваша заявка поступила в модерацию\nВ ближайщее время с вами свяжуться\nДля продолжения введите/нажмите [/start]";

    public abstract void message(Update update);

    protected SendMessage sendMessageHelper(Message message) throws TelegramApiException {
        bot.execute(new DeleteMessage(message.getChatId(), message.getMessageId()));
        return new SendMessage().setChatId(message.getChatId().toString()).enableMarkdown(true);
    }
    protected SendMessage sendMsgWeeks(Message message, String text) throws TelegramApiException {
        return sendMessageHelper(message).
                setText(text).
                setReplyMarkup(showReplyKeyboardMarkup(getKeyboard(Calendar.getData(),"\n","ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС")));

    }
    protected SendMessage sendMessageTxt(Message message, String text) throws TelegramApiException {
        return sendMessageHelper(message).
                setText(text);
    }
    protected SendMessage sendMsg (Message message, String text) throws TelegramApiException {
        return sendMessageHelper(message).
                setText(text).
                setReplyMarkup(showReplyKeyboardMarkup(getKeyboard("Календарь \uD83D\uDCC5","\n", "Обо мне \uD83E\uDD16")));

    }
}
