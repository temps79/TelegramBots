package TelegramBot.service;


import TelegramBot.handler.defaultHandler;
import TelegramBot.model.Bot;
import org.telegram.telegrambots.api.objects.Update;

import org.telegram.telegrambots.exceptions.TelegramApiException;



import java.io.IOException;
import java.security.GeneralSecurityException;


public class MessageReciever implements Runnable{
    private Bot bot;


    public MessageReciever(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        while(true){
            for(Object object= bot.receiveQueue.poll();object!=null;object=bot.receiveQueue.poll()){
                try {
                    checkUpdate(object);
                } catch (TelegramApiException | GeneralSecurityException | IOException | NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void checkUpdate(Object object) throws TelegramApiException, GeneralSecurityException, IOException, NoSuchFieldException, IllegalAccessException {
        if(object instanceof Update){
            new defaultHandler(bot).operator((Update) object);

        }

    }
}
