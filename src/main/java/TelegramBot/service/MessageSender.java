package TelegramBot.service;


import TelegramBot.States.State;
import TelegramBot.handler.DefaultHandler;
import TelegramBot.model.Bot;
import lombok.Getter;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Map;
import java.util.Set;


public class MessageSender implements Runnable{
    private Bot bot;

    @Getter
    private static Bot.User AdminChatId=new Bot.User("491099045");
    @Getter
    private static Bot.User AnnaChatId=new Bot.User("467295343");

    public static void setTrue(String id,Bot bot){
        String chatId="";
        if(id.equals(AdminChatId.getChatId()))
            AdminChatId.setReady(true);
        else if(id.equals(getAnnaChatId()))
            AnnaChatId.setReady(true);
        else return;

    }
    public static void setFalse(String id,Bot bot){
        String chatId="";
        if(id.equals(AdminChatId.getChatId()))
            AdminChatId.setReady(false);
        else if(id.equals(getAnnaChatId()))
            AnnaChatId.setReady(false);
        else return;

    }




    public MessageSender(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        while(true){
            if (bot.sendSystemQueue.size() == 1 && (AdminChatId.isReady() || AnnaChatId.isReady())) {
                String[] sysArray = bot.sendSystemQueue.poll().split(":");
                try {
                    sendMoneyInfo(sysArray[0], sysArray[1]);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if(bot.sendSystemQueue.size()==4){
                StringBuilder stringBuilder=new StringBuilder();
                for (String string = bot.sendSystemQueue.poll(); string != null; string = bot.sendSystemQueue.poll())
                    stringBuilder.append(string);
                try {
                    sendInfo(stringBuilder.toString());
                } catch (TelegramApiException e) {

                }
            }

            for(Object object= bot.sendQueue.poll();object!=null;object=bot.sendQueue.poll())
                try {
                    send(object);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
        }
    }
    private void send(Object object) throws TelegramApiException {
        BotApiMethod<Message> message = (BotApiMethod<Message>) object;
        bot.execute(message);
    }
    private   void  sendInfo(String text) throws TelegramApiException {
        System.out.println(text);
        bot.sendMessage(new SendMessage()
                .setChatId(AdminChatId.getChatId())
                .setText(text));

    }
   private void sendMoneyInfo(String text,String ChatId) throws TelegramApiException {
        bot.sendMessage(new SendMessage()
                .setChatId(ChatId)
                .setText(text));
    }



}
