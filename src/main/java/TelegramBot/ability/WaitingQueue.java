package TelegramBot.ability;

import TelegramBot.hibernate.model.User;
import TelegramBot.model.Bot;
import TelegramBot.states.ChatState;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WaitingQueue extends Thread{
    private volatile Queue<User> waitingList=new ConcurrentLinkedQueue<>();
    private Bot bot;
    public WaitingQueue(Bot bot){
        this.bot=bot;
    }

    @Override
    public void run() {
        while(!isInterrupted()){
            if(isReady()) {
                interrupt();
            }
        }

    }


    @Override
    public String toString() {
        return waitingList.toString();
    }

    public boolean isReady(){

        for(User user:waitingList) {
            bot.sendQueue.add(new SendMessage().setChatId(user.getChatId()).setText("Пожалуйста, дождитесь ответа консультанта.\nОн совсем скоро освободиться."));
            while (true) {
                List<User> users=bot.getService().findAllUsers();
                int count=0;
                for(User user_1:users){
                    if(user_1.getState().equals(new ChatState(bot).toString())&&!user_1.getChatId().equals(ChatState.getChatIdConsultant())){
                        count++;
                    }
                }
                if(count==0) {
                    bot.getService().updateUserState(bot.getService().findUser(waitingList.poll().getChatId()), new ChatState(bot).toString());
                    bot.getService().updateUserState(bot.getService().findUser(ChatState.getChatIdConsultant()),new ChatState(bot).toString());
                    return true;
                }
            }
        }
        return false;
    }
    public void add(User user){
        waitingList.add(user);
    }
    public User poll(){
        return waitingList.poll();
    }



}
