package TelegramBot.service;


import TelegramBot.model.Bot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;



public class MessageSender implements Runnable{
    private Bot bot;


    public MessageSender(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        while(true){
            if(bot.sendSystemQueue.size()>=4) {
                StringBuilder stringBuilder=new StringBuilder();
                for (String string = bot.sendSystemQueue.poll(); string != null; string = bot.sendSystemQueue.poll())
                    stringBuilder.append(string);
                try {
                    sendInfo(stringBuilder.toString());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            for(Object object= bot.sendQueue.poll();object!=null;object=bot.sendQueue.poll()){
                send(object);
            }
        }
    }
    private void send(Object object){


    }
    private   void  sendInfo(String text) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId("491099045");
        sendMessage.setText(text);
        System.out.println(text);
        bot.sendMessage(sendMessage);
    }



}
