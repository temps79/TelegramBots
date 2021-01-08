package TelegramBot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MyCalendar {
    private Calendar date;
    private   static String Data;

    MyCalendar(){
        GregorianCalendar date= (GregorianCalendar) GregorianCalendar.getInstance();
        date.setFirstDayOfWeek(Calendar.MONDAY);
        Data=new SimpleDateFormat("d MMMM yyyy",new Locale("ru")).format(date.getTime());
    }



    public static  String getData() {
        return Data;
    }



}
