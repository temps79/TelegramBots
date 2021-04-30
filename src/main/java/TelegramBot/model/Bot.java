package TelegramBot.model;

import TelegramBot.States.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;




public class Bot extends TelegramLongPollingBot {
    @Setter
    @Getter
    private HashMap<String,State> stateMap;

    @NoArgsConstructor
    @AllArgsConstructor
    public static class User{
        public User(String chatId) {
            this.chatId = chatId;
        }
        @Getter
        @Setter
        private String chatId;
        @Setter
        @Getter
        private boolean ready;

    }


    @Setter
    @Getter
    final private String botName;
    @Setter
    @Getter
    final private String token;

    public final Queue<Object> sendQueue = new ConcurrentLinkedQueue<>();
    public final Queue<String> sendSystemQueue = new ConcurrentLinkedQueue<>();
    public final Queue<Object> receiveQueue = new ConcurrentLinkedQueue<>();


    public Bot(String botName, String token) {
        stateMap =new HashMap<>();
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