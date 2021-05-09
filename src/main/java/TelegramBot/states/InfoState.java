package TelegramBot.states;

import TelegramBot.model.Bot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class InfoState extends State{
    public InfoState(Bot bot) {
        super(bot);
    }

    @SneakyThrows
    @Override
    public void message(Update update) {
        Message message= update.getMessage();
        bot.sendQueue.add(ReferenceURL(update));
        bot.getStateMap().put(message.getChatId().toString(),new ReadyState(bot));
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
}
