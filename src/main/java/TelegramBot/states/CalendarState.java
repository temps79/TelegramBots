package TelegramBot.states;

import TelegramBot.model.Bot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class CalendarState extends State{
    public CalendarState(Bot bot) {
        super(bot);
    }

    @SneakyThrows
    @Override
    public void message(Update update) {
        Message message= update.getMessage();
        bot.sendQueue.add(sendMsgWeeks(message, entryDay));
        bot.getService().updateUserState(bot.getService().findUser(message.getChatId().toString()),new DayState(bot).toString());

    }

}
