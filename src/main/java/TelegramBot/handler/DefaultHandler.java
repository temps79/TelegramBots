package TelegramBot.handler;

import TelegramBot.hibernate.model.User;
import TelegramBot.states.*;
import TelegramBot.model.Bot;
import TelegramBot.service.MessageSender;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.lang.reflect.Constructor;


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
        String chatId="";
        if(update.hasCallbackQuery())
            chatId=update.getCallbackQuery().getMessage().getChatId().toString();
        else if(update.hasMessage())
            chatId=update.getMessage().getChatId().toString();
        if(bot.getService().containsChatId(chatId)) {
            if (update.hasMessage() && message.hasText()) {
                getState(bot,bot.getService().findUser(chatId)).message(update);
            } else if (update.hasCallbackQuery()) {
                bot.execute(new SendMessage().setText("Как вас зовут?")
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
                bot.getService().updateUserState(bot.getService().findUser(chatId),
                        new QuestionTimeInfoState(bot).toString());
            }
        }
        else {
            try {
                bot.getService().saveUser(new User(chatId, update.getMessage().getChat().getFirstName()+" "
                        +(update.getMessage().getChat().getLastName()==null?"":update.getMessage().getChat().getLastName())));
                bot.receiveQueue.add(update);
            }catch (Exception e){
                System.out.println(e);
            }

        }
    }

    @SneakyThrows
    private static State getState(Bot bot, User user){
        Class<?> clazz = Class.forName(State.class.getPackage().getName()+"."+user.getState());
        Constructor<?> constructor = clazz.getConstructor(Bot.class);
        //System.out.println(clazz.getSimpleName());
        return (State) constructor.newInstance(bot);
    }

}
