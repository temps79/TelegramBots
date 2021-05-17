package TelegramBot.service;


import TelegramBot.model.Bot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;


public class MessageSender implements Runnable{
    private Bot bot;

    @NoArgsConstructor
    @AllArgsConstructor
    public static class User{
        public User(String chatId) {
            this.chatId = chatId;
        }
        @Getter
        @Setter
        private String chatId;
        @Setter
        @Getter
        private boolean ready;
    }
    @Getter
    @Setter
    private static String price="";
    @Getter
    private static User AdminChatId=new User("491099045");
    @Getter
    private static User AnnaChatId=new User("467295343");

    public static void setTrue(String id){
        if(id.equals(AdminChatId.getChatId()))
            AdminChatId.setReady(true);
        else if(id.equals(AnnaChatId.getChatId()))
            AnnaChatId.setReady(true);
        else return;
    }

    public static void setFalse(String id){
        if(id.equals(AdminChatId.getChatId()))
            AdminChatId.setReady(false);
        else if(id.equals(AnnaChatId.getChatId()))
            AnnaChatId.setReady(false);
        else return;
    }




    public MessageSender(Bot bot) {
        this.bot = bot;
    }


    @Override
    public void run() {
        while(true){
            if (price.matches("\\d+:\\d+") && (AdminChatId.isReady() || AnnaChatId.isReady())) {
                String[] sysArray = price.split(":");
                try {
                    sendMoneyInfo(sysArray[0], sysArray[1]);
                    price="";
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            for(Object object= bot.sendQueue.poll();object!=null;object=bot.sendQueue.poll())
                try {
                    send(object);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            if(bot.getMessageService().findAllMessages().size()>0){
                List<TelegramBot.hibernate.model.Message> messageList=bot.getMessageService().findAllMessages();
                for (TelegramBot.hibernate.model.Message message:messageList){
                    try {
                        if(message.getContact()!=null) {
                            sendInfo(message.toString());
                            bot.getMessageService().deleteMessage(message);
                        }
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
    private void send(Object object) throws TelegramApiException {
        BotApiMethod<Message> message = (BotApiMethod<Message>) object;
        bot.execute(message);


    }
    private   void  sendInfo(String text) throws TelegramApiException {
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
