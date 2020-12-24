package TelegramBot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.apache.http.client.protocol.RequestAddCookies;

public class Application {
    

    private static final String BOT_ADMIN = "321644283";

    public static void main(String[] args) {
        ApiContextInitializer.init() ;
        Bot telegram_bot = new Bot("TimeTablee_bot", "1491572212:AAHy-6x6yfFCkAIalyNtnNjJC9Ue10OCr0c");
        telegram_bot.botConnect();
    }
}

