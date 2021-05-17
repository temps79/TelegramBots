package TelegramBot.states;

import TelegramBot.model.Bot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;


public class UsersInfoState extends State{
    public UsersInfoState(Bot bot) {
        super(bot);
    }

    @SneakyThrows
    @Override
    public void message(Update update) {
        Message message= update.getMessage();
        bot.sendQueue.add(sendMsgWeeks(message,bot.getService().findAllUsers().toString()));
        bot.getService().updateUserState(bot.getService().findUser(message.getChatId().toString()),new ReadyState(bot).toString());
    }
}
