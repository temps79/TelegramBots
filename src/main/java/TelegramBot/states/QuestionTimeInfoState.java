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
        bot.sendSystemQueue.add(String.format("TelegramName: %s | %s\nИмя:", message.getChat().getFirstName(),
                message.getChat().getLastName()==null?"":message.getChat().getLastName()));
        bot.sendSystemQueue.add(message.getText()+"\nВремя:");
        bot.sendQueue.add(sendMessageTxt(message,selectedTime));
        bot.getStateMap().put(message.getChatId().toString(),new TimeInfoContact(bot));
    }
}
