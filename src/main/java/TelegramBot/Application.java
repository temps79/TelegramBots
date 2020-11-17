package TelegramBot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;


public class Application {
    private static final Logger log = Logger.getLogger(Application.class);

    private static final String BOT_ADMIN = "321644283";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot telegram_bot = new Bot("sschedule_bot", "1451992685:AAEmF5nIGLrFGY11xQsdceyTPT_sr7j6WBw");
        telegram_bot.botConnect();

    }
}