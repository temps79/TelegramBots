package TelegramBot.states;

import TelegramBot.model.Bot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class TimeInfoContact extends State{

    public TimeInfoContact(Bot bot) {
        super(bot);
    }

    @SneakyThrows
    @Override
    public void message(Update update) {
        Message message= update.getMessage();

        TelegramBot.hibernate.model.Message messageDB=bot.getMessageService().findMessage(message.getChatId().toString());
        messageDB.setTime(message.getText());
        bot.getMessageService().updateMessage(messageDB);

        bot.sendQueue.add(sendMessageTxt(message,contactInfo));
        bot.getService().updateUserState(bot.getService().findUser(message.getChatId().toString()),new ContactInfo(bot).toString());
    }
}
