package TelegramBot;

import com.google.api.client.json.Json;
import com.google.api.client.json.JsonString;
import org.json.JSONString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class MyCalendar {
    private  Calendar date;
    private   static String Data;

    MyCalendar(){
        GregorianCalendar date= (GregorianCalendar) GregorianCalendar.getInstance();
        date.setFirstDayOfWeek(Calendar.MONDAY);
        Data=new SimpleDateFormat("d MMMM yyyy").format(date.getTime());
    }

  

    public static  String getData() {

        return Data;
    }



}
