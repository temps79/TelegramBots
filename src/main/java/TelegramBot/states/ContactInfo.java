package TelegramBot.states;

import TelegramBot.model.Bot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class ContactInfo extends State{
    public ContactInfo(Bot bot) {
        super(bot);
    }

    @SneakyThrows
    @Override
    public void message(Update update) {
        Message message= update.getMessage();
        bot.sendSystemQueue.add(message.getText());
        bot.sendQueue.add(sendMsg(message,moderation));
        bot.getStateMap().put(message.getChatId().toString(),new ReadyState(bot));
        bot.receiveQueue.add(update);
    }
}
