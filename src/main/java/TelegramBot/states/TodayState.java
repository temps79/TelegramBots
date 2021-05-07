package TelegramBot.states;

import TelegramBot.model.Bot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class TodayState extends State{
    public TodayState(Bot bot) {
        super(bot);
    }

    @SneakyThrows
    @Override
    public void message(Update update) {
        Message message= update.getMessage();
        bot.sendQueue.add(sendMsgWeeks(message,today));
        bot.getStateMap().put(message.getChatId().toString(),new DayState(bot));
    }
}
