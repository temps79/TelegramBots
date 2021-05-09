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
        bot.sendSystemQueue.add(message.getText()+"\nКонтакты:");
        bot.sendQueue.add(sendMessageTxt(message,contactInfo));
        bot.getStateMap().put(message.getChatId().toString(),new ContactInfo(bot));
    }
}
