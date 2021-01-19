package TelegramBot;


import TelegramBot.model.Bot;
import TelegramBot.service.MessageReciever;
import TelegramBot.service.MessageSender;
import org.telegram.telegrambots.ApiContextInitializer;



public class Application {


    private static final String BOT_ADMIN = "321644283";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot telegram_bot = new Bot("TimeTablee_bot", "1491572212:AAHy-6x6yfFCkAIalyNtnNjJC9Ue10OCr0c");

        telegram_bot.botConnect();


        MessageReciever messageReciever=new MessageReciever(telegram_bot);
        Thread threadRecievr=new Thread(messageReciever);
        threadRecievr.start();

        MessageSender messageSender=new MessageSender(telegram_bot);
        Thread threadSender=new Thread(messageSender);
        threadSender.start();



    }
}
