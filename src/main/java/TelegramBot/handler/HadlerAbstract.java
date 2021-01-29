package TelegramBot.handler;

import TelegramBot.ability.Calendar;
import TelegramBot.model.Bot;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;


import java.io.IOException;
import java.security.GeneralSecurityException;

public abstract class HadlerAbstract {
    protected    String entryAction="*Выберите действие*";
    protected String entryDay="Выберите день:";
    protected  String infoMe="Обо мне \uD83E\uDD16";
    protected  String calendar="Календарь \uD83D\uDCC5";
    protected String today="Сегодня " + Calendar.getData() + "\nВыберите день:";
    protected String selectedTime="Желаемый день/время:";
    protected  String contactInfo="Ваши контакты для связи(Способ связи)?";
    protected  String moderation="Ваша заявка поступила в модерацию\nВ ближайщее время с вами свяжуться\nДля продолжения введите/нажмите [/start]";

    Bot bot;
    public HadlerAbstract(Bot bot){
        this.bot=bot;
    }
    public abstract void operator(Update update) throws TelegramApiException, GeneralSecurityException, IOException;
}
