package TelegramBot.service;


import TelegramBot.handler.DefaultHandler;
import TelegramBot.handler.HadlerAbstract;
import TelegramBot.model.Bot;
import TelegramBot.states.DayState;
import TelegramBot.states.ReadyState;
import TelegramBot.states.State;
import TelegramBot.states.TodayState;
import org.telegram.telegrambots.api.objects.Update;

import org.telegram.telegrambots.exceptions.TelegramApiException;



import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;


public class MessageReciever implements Runnable{
    private  Bot bot;



    public MessageReciever(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run() {
        while(true)
            for(Object object= bot.receiveQueue.poll();object!=null;object=bot.receiveQueue.poll())
                try {
                    checkUpdate(object);
                } catch (TelegramApiException | GeneralSecurityException | IOException  e) {
                    e.printStackTrace();
                }
    }
    private void checkUpdate(Object object) throws TelegramApiException, GeneralSecurityException, IOException  {
        if(object instanceof Update && object!=null) {
            HadlerAbstract handler = new DefaultHandler(bot);
            handler.operator((Update) object);
        }
    }
}
