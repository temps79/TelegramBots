package TelegramBot.states;

import TelegramBot.model.Bot;
import lombok.SneakyThrows;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class QuestionTimeInfoState extends State{
    public QuestionTimeInfoState(Bot bot) {
        super(bot);
    }

    @SneakyThrows
    @Override
    public void message(Update update) {
        Message message= update.getMessage();

        TelegramBot.hibernate.model.Message messageDb=new TelegramBot.hibernate.model.Message(message.getChatId().toString());
        messageDb.setName(message.getText());
        bot.getMessageService().saveMessage(messageDb);

        bot.sendQueue.add(sendMessageTxt(message,selectedTime));
        bot.getService().updateUserState(bot.getService().findUser(message.getChatId().toString()),new TimeInfoContact(bot).toString());
    }
}
