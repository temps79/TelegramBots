package TelegramBot;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarQuickstart {
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";


    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    //Текущая дата
    private static Date nowDate=new Date();
    // Время в дне(миллесек)
    private static long timeInDay=nowDate.getHours()*3600000+nowDate.getMinutes()*60000+nowDate.getSeconds()*1000;
    //Миллисекунд в неделе не включая один день
    private  static long WeeksInMills=604800000-timeInDay;
    //Формат даты
    private static final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("d MMMM yyyy",new Locale("ru"));
    public static String  getData(){
        return simpleDateFormat.format(nowDate);
    }
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    // Печать расписания
    public static String printTable(String day) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        // Вычисление начала текущего дня
        DateTime now = new DateTime(System.currentTimeMillis()-timeInDay);
        // Вычисление недели до текущего дня(не включая)
        DateTime old=new DateTime(System.currentTimeMillis()+WeeksInMills);
        // Получение событий из календаря с текущего дня(00:00) по следующую неделю
        Events events = service.events().list("primary")
                .setTimeMax(old)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        //результирующая строка
        String result=new String();
        //Определения дня в зависисмсоти от недели
        SelectedWeeks selectedWeeks=new SelectedWeeks();
        int statusDay=selectedWeeks.getStatusDay(day);

        result="_"+simpleDateFormat.format(selectedWeeks.getCalendar().getTime())+"_"+"\n\n";
        //Определение событий подоходящих под выбранный день
        if (items.isEmpty()) {

        } else {
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                Date date=new Date(start.getValue());
                java.util.Calendar calendar= new GregorianCalendar();
                calendar.setTime(date);
                if (start == null)
                    start = event.getStart().getDate();
                if(date.getDay()==statusDay){
                    if(date.getMinutes()<10)
                        result += "* Имя:*" +event.getSummary() + "\t *Время:*" + date.getHours()+":0"+date.getMinutes()  + "\n\n";
                    else
                        result += "* Имя:*" +event.getSummary() + "\t *Время:*" + date.getHours()+":"+date.getMinutes()  + "\n\n";
                }
            }
        }
        if(!result.contains("* Имя:*")){
            result +="*Выходной*";
        }
        return result;
    }
}