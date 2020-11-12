package TelegramBot;

import com.google.api.client.json.Json;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

public class MyCalendar {
    private Calendar date;
    private String Month;
    private static final String filePath = "C:\\Users\\temps\\Desktop\\TelegramBot\\src\\main\\resources\\TableView.json";



    MyCalendar(){
        Calendar date=Calendar.getInstance();
        Month=new SimpleDateFormat("MMMM").format(date.getTime());
    }

    /*public static void main(String[] args) throws IOException, ParseException {

        try{
            // считывание файла JSON
        FileReader reader = new FileReader(filePath);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

        // получение массива
        JSONArray lang=(JSONArray) jsonObject.get("День");
       // System.out.println(lang);

            String result=new String();
        JSONObject day=(JSONObject) lang.get(5);
            //System.out.println( day);
            JSONArray temp=(JSONArray) day.get("Воскресенье");
            Iterator it=temp.iterator();
            while(it.hasNext()){
                JSONObject innerObj = (JSONObject) it.next();
                result+="Имя:"+innerObj.get("Имя")+"\t Время:"+innerObj.get("Время")+"\t Локация:"+ innerObj.get("Локация")+"\n";

            }
            System.out.println(result);


    } catch (FileNotFoundException ex) {
        ex.printStackTrace();
    } catch (IOException ex) {
        ex.printStackTrace();
    } catch (ParseException ex) {
        ex.printStackTrace();
    } catch (NullPointerException ex) {
        ex.printStackTrace();
    }


    }*/

    public static String printTableOnDay(String day) throws IOException, ParseException {
        FileReader reader = new FileReader(filePath);
        String result=new String();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        // получение массива
        JSONArray lang= (JSONArray) jsonObject.get("День");
        switch(day){
            case "Воскресенье":
                JSONObject obj=(JSONObject) lang.get(5);
                JSONArray temp=(JSONArray) obj.get("Воскресенье");
                Iterator it=temp.iterator();
                while(it.hasNext()){
                    JSONObject innerObj = (JSONObject) it.next();
                    result+="Имя:"+innerObj.get("Имя")+"\t Время:"+innerObj.get("Время")+"\t Локация:"+ innerObj.get("Локация")+"\n";
                }
                break;
        }
        return result;
    }

    public String getMonth() {
        return Month;
    }



}
