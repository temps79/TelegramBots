package src.model;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import java.text.SimpleDateFormat;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;




public class Bot extends TelegramLongPollingBot {

    @Setter
    @Getter
    final private String botName;
    @Setter
    @Getter
    final private String token;

    public final Queue<Object> sendQueue = new ConcurrentLinkedQueue<>();
    public final Queue<String> sendSystemQueue = new ConcurrentLinkedQueue<>();
    public final Queue<Object> receiveQueue = new ConcurrentLinkedQueue<>();

    private int status=0;// Стату сообщения

    public Bot(String botName, String token) {
        this.botName=botName;
        this.token=token;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void botConnect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            botConnect();
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
        receiveQueue.add(update);
    }

}