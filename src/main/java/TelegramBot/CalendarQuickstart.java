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
    private static Date nowDate=new Date();
    private static long timeInDay=nowDate.getHours()*3600000+nowDate.getMinutes()*60000+nowDate.getSeconds()*1000;
    private  static long WeeksInMills=604800000-timeInDay;

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


    public static String printTable(String day) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        DateTime now = new DateTime(System.currentTimeMillis());
        DateTime old=new DateTime(System.currentTimeMillis()+WeeksInMills);
        Events events = service.events().list("primary")
                .setTimeMax(old)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        String result=new String();
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        java.util.Calendar temp=  java.util.Calendar.getInstance();
        int statusDay=0;
        switch(day){
            case "Понедельник":
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
                if(temp.after(c)) {
                    c.add(java.util.Calendar.DATE,7);
                }
                statusDay=1;
                break;
            case "Вторник":
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.TUESDAY);
                if(temp.after(c))
                    c.add(java.util.Calendar.DATE,7);
                statusDay=2;
                break;
            case "Среда":
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.WEDNESDAY);
                if(temp.after(c))
                    c.add(java.util.Calendar.DATE,7);
                statusDay=3;
                break;
            case "Четверг":
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.THURSDAY);
                if(temp.after(c))
                    c.add(java.util.Calendar.DATE,7);
                statusDay=4;
                break;
            case "Пятница":
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.FRIDAY);
                if(temp.after(c))
                    c.add(java.util.Calendar.DATE,7);
                statusDay=5;

                break;
            case "Суббота":
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SATURDAY);
                if(temp.after(c))
                    c.add(java.util.Calendar.DATE,7);
                statusDay=6;
                break;
            case "Воскресенье":
                c.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SUNDAY);
                if(temp.after(c))
                    c.add(java.util.Calendar.DATE,7);
                statusDay=0;
                break;

        }

        result="_"+new SimpleDateFormat("d MMMM yyyy").format(c.getTime())+"_"+"\n\n";
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                Date date=new Date(start.getValue());
                java.util.Calendar calendar= new GregorianCalendar();
                calendar.setTime(date);
                if (start == null) {
                    start = event.getStart().getDate();
                }

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