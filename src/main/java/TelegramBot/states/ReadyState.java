package TelegramBot.states;

import TelegramBot.model.Bot;
import TelegramBot.service.MessageSender;
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
            bot.getService().updateUserState(bot.getService().findUser(message.getChatId().toString()),new CalendarState(bot).toString());
            bot.receiveQueue.add(update);
        }
        else if(message.getText().equals(infoMe)){
            bot.getService().updateUserState(bot.getService().findUser(message.getChatId().toString()),new InfoState(bot).toString());
            bot.receiveQueue.add(update);
        } else if(message.getText().equals("info") && (message.getChatId().toString().equals(MessageSender.getAdminChatId().getChatId())||
                message.getChatId().toString().equals(MessageSender.getAnnaChatId().getChatId()))) {
            bot.getService().updateUserState(bot.getService().findUser(message.getChatId().toString()),new UsersInfoState(bot).toString());
            bot.receiveQueue.add(update);
        } else
            bot.sendQueue.add(sendMsg(message,entryAction));

    }

}
