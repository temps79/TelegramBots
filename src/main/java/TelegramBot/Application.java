package TelegramBot;


import TelegramBot.model.Bot;
import TelegramBot.service.MessageReciever;
import TelegramBot.service.MessageSender;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.ApiContextInitializer;

import java.io.File;
import java.util.Arrays;


public class Application {


    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot telegram_bot = new Bot(args[0], args[1]);
        telegram_bot.botConnect();

        MessageReciever messageReciever=new MessageReciever(telegram_bot);
        Thread threadRecievr=new Thread(messageReciever);
        threadRecievr.start();

        MessageSender messageSender=new MessageSender(telegram_bot);
        Thread threadSender=new Thread(messageSender);
        threadSender.start();



    }
}
