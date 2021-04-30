package TelegramBot.States;

import TelegramBot.model.Bot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class ReadyState extends State{

    public ReadyState(Bot bot) {
        super(bot);
    }

    @SneakyThrows
    @Override
    public void message(Update update) {
        Message message=update.getMessage();
        if(message.getText().equals(calendar)) {
            bot.getStateMap().put(message.getChatId().toString(),new CalendarState(bot));
            bot.receiveQueue.add(update);
        }
        else if(message.getText().equals(infoMe)){
            bot.getStateMap().put(message.getChatId().toString(),new InfoState(bot));
            bot.receiveQueue.add(update);
        }else
            bot.sendQueue.add(sendMsg(message,entryAction));
    }

}
