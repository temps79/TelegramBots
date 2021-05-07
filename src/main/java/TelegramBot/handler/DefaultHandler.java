package TelegramBot.handler;

import TelegramBot.states.QuestionTimeInfoState;
import TelegramBot.states.ReadyState;
import TelegramBot.model.Bot;
import TelegramBot.service.MessageSender;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class DefaultHandler extends HadlerAbstract {
    @Getter
    @Setter
    private static boolean ready;


    public DefaultHandler(Bot bot){
        super(bot);
    }

    @Override
    public void operator(Update update) throws TelegramApiException{

        MessageSender.setFalse(MessageSender.getAdminChatId().getChatId());
        MessageSender.setFalse(MessageSender.getAnnaChatId().getChatId());

        Message message=update.getMessage();
        String chatId;
        chatId=update.hasCallbackQuery()?update.getCallbackQuery().getMessage().getChatId().toString():update.getMessage().getChatId().toString();
        if(bot.getStateMap().containsKey(chatId) ) {
            if (update.hasMessage() && message.hasText()) {
                bot.getStateMap().get(message.getChatId().toString()).message(update);
            } else if (update.hasCallbackQuery()) {
                bot.execute(new SendMessage().setText("Как вас зовут?")
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
                bot.getStateMap().put(update.getCallbackQuery().getMessage().getChatId().toString(),new QuestionTimeInfoState(bot));
            }
        }
        else {
            bot.getStateMap().put(message.getChatId().toString(),new ReadyState(bot));
            bot.receiveQueue.add(update);
        }
    }

}
