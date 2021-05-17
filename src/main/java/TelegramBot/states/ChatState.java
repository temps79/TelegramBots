package TelegramBot.states;

import TelegramBot.hibernate.model.User;
import TelegramBot.model.Bot;
import lombok.Getter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

public class ChatState extends State{
    @Getter
    private static String chatIdConsultant="491099045";
    public ChatState(Bot bot) {
        super(bot);
    }

    @SneakyThrows
    @Override
    public void message(Update update) {
        Message message= update.getMessage();
        List<User> users=bot.getService().findAllUsers();
        String chatId="";

        for(User user:users) {
            System.out.println(user);
            if (user.getState().equals(new ChatState(bot).toString()) && !user.getChatId().equals(chatIdConsultant)) {
                chatId = user.getChatId();
                break;
            }
        }
        if(message.getText().equals("Выход")){
            if(message.getChatId().toString().equals(chatIdConsultant)){
                bot.sendQueue.add(sendChatMessage( "Консультант покинул чат",chatId));
                bot.sendQueue.add(sendMsg(entryAction,chatId));
            }else {
                bot.sendQueue.add(sendChatMessage( "Клиент покинул чат",chatIdConsultant));
                bot.sendQueue.add(sendMsg(entryAction,chatIdConsultant));
            }
            bot.getService().updateUserState(bot.getService().findUser(chatId),new ReadyState(bot).toString());
            bot.getService().updateUserState(bot.getService().findUser(chatIdConsultant),new ReadyState(bot).toString());
            bot.receiveQueue.add(update);
        }else {
            if(!message.getChatId().toString().equals(chatIdConsultant)){
                bot.sendQueue.add(sendChatMessage( message.getText(),chatIdConsultant));
            }else {
                bot.sendQueue.add(sendChatMessage( message.getText(),chatId));
            }
        }

    }
}
