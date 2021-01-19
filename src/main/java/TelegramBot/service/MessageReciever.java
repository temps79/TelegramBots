package src.service;


import org.telegram.telegrambots.api.objects.Update;

import org.telegram.telegrambots.exceptions.TelegramApiException;
import src.handler.defaultHandler;
import src.model.Bot;

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
                } catch (TelegramApiException | GeneralSecurityException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void checkUpdate(Object object) throws TelegramApiException, GeneralSecurityException, IOException {
        if(object instanceof Update){
            new defaultHandler(bot).operator((Update) object);

        }

    }
}
