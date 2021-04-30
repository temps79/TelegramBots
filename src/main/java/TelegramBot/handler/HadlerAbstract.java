package TelegramBot.handler;

import TelegramBot.ability.Calendar;
import TelegramBot.model.Bot;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;


import java.io.IOException;
import java.security.GeneralSecurityException;

public abstract class HadlerAbstract {

    Bot bot;
    public HadlerAbstract(Bot bot){
        this.bot=bot;
    }
    public abstract void operator(Update update) throws TelegramApiException, GeneralSecurityException, IOException;
}
