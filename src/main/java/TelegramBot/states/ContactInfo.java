package TelegramBot.states;

import TelegramBot.ability.WaitingQueue;
import TelegramBot.hibernate.model.User;
import TelegramBot.model.Bot;
import lombok.SneakyThrows;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;


import java.util.List;


public class ContactInfo extends State{
    public ContactInfo(Bot bot) {
        super(bot);
    }

    @SneakyThrows
    @Override
    public  void message(Update update) {
        Message message= update.getMessage();
        try {
            TelegramBot.hibernate.model.Message messageDB=bot.getMessageService().findMessage(message.getChatId().toString());
            messageDB.setContact(message.getText());
            bot.getMessageService().updateMessage(messageDB);
        }catch (NullPointerException e){
            System.out.println(e);
        }
        if(message.getText().equals("Выход")){
            bot.getService().updateUserState(bot.getService().findUser(message.getChatId().toString()),new ReadyState(bot).toString());
            bot.getService().updateUserState(bot.getService().findUser(ChatState.getChatIdConsultant()),new ReadyState(bot).toString());
            bot.receiveQueue.add(update);
        }

        bot.sendQueue.add(sendChatMessage("Переводим вас на консультанта\nДля выхода нажмите кнопку \"Выход\"",message.getChatId().toString()));

        List<User> users=bot.getService().findAllUsers();
        WaitingQueue waitingList=new WaitingQueue(bot);
        int count=0;
        for(User user:users){
            if(user.getState().equals(new ChatState(bot).toString())&&!user.getChatId().equals(ChatState.getChatIdConsultant())){
                count++;
                waitingList.add(user);
            }
        }

        waitingList.add(bot.getService().findUser(message.getChatId().toString()));
        waitingList.poll();
        if(count==0) {
            bot.getService().updateUserState(bot.getService().findUser(message.getChatId().toString()), new ChatState(bot).toString());
            bot.getService().updateUserState(bot.getService().findUser(ChatState.getChatIdConsultant()),new ChatState(bot).toString());
            bot.sendQueue.add(sendChatMessage( "Поступила новая заявка",ChatState.getChatIdConsultant()));

        }else {
             waitingList.start();

        }
    }
}
