package TelegramBot.ability;
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

public class Calendar{
    private static final String APPLICATION_NAME = "Calendar";
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

    private static Map<String,Integer> selectedWeeks=new HashMap<>();
    static
    {
        selectedWeeks.put("Понедельник",1);
        selectedWeeks.put("Вторник",2);
        selectedWeeks.put("Среда",3);
        selectedWeeks.put("Четверг",4);
        selectedWeeks.put("Пятница",5);
        selectedWeeks.put("Суббота",6);
        selectedWeeks.put("Воскресенье",0);
    }
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = Calendar.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
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
        com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
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
        int statusDay=selectedWeeks.get(day);

        result="_"+simpleDateFormat.format(getPrivateCalendar(statusDay).getTime())+"_"+"\n\n";
        //Определение событий подоходящих под выбранный день
        if (items.isEmpty()) {

        } else {
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                Date date=new Date(start.getValue());
                if (start == null)
                    start = event.getStart().getDate();
                if(date.getDay()==statusDay)
                    if(date.getMinutes()<10)
                        result += "* Имя:*" +event.getSummary() + "\t *Время:*" + date.getHours()+":0"+date.getMinutes()  + "\n\n";
                    else
                        result += "* Имя:*" +event.getSummary() + "\t *Время:*" + date.getHours()+":"+date.getMinutes()  + "\n\n";
            }
        }
        if(!result.contains("* Имя:*")){
            result +="*Выходной*";
        }
        return result;
    }

    private static java.util.Calendar getPrivateCalendar(int statusDay) {
        java.util.Calendar temp= java.util.Calendar.getInstance();
        java.util.Calendar c=java.util.Calendar.getInstance();
        switch(statusDay){
            case 1:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
                break;
            case 2:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.TUESDAY);
                break;
            case 3:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.WEDNESDAY);
                break;
            case 4:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.THURSDAY);
                break;
            case 5:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.FRIDAY);
                break;
            case 6:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SATURDAY);
                break;
            case 0:
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SUNDAY);
                break;
        }
        if(temp.after(c))
            c.add(java.util.Calendar.DATE,7);
        return c;
    }
}