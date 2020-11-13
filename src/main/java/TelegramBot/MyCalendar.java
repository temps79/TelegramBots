package TelegramBot;

import com.google.api.client.json.Json;
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
import java.util.Iterator;

public class MyCalendar {
    private  Calendar date;
    private  String Data;
    private static final String filePath = "C:\\Users\\temps\\Desktop\\TelegramBot\\src\\main\\resources\\TableView.json";



    MyCalendar(){
        Calendar date=Calendar.getInstance();
        Data=new SimpleDateFormat("d MMMM yyyy").format(date.getTime());
    }



    public static String printTableOnDay(String day) throws IOException, ParseException {

        FileReader reader = new FileReader(filePath);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

        String result=new String();
        Calendar c = Calendar.getInstance();


        // получение массива
        JSONArray lang= (JSONArray) jsonObject.get("День");
        JSONObject obj=new JSONObject();
        switch(day){
            case "Понедельник":
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                result="_"+new SimpleDateFormat("d MMMM yyyy").format(c.getTime())+"_"+"\n\n";
                obj=(JSONObject) lang.get(0);
                break;
            case "Вторник":

                c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                result="_"+new SimpleDateFormat("d MMMM yyyy").format(c.getTime())+"_"+"\n\n";
                obj=(JSONObject) lang.get(1);
                break;
            case "Среда":
                c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                result="_"+new SimpleDateFormat("d MMMM yyyy").format(c.getTime())+"_"+"\n\n";
                obj=(JSONObject) lang.get(2);
                break;
            case "Четверг":
                c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                result="_"+new SimpleDateFormat("d MMMM yyyy").format(c.getTime())+"_"+"\n\n";
                obj=(JSONObject) lang.get(3);
                break;
            case "Пятница":
                c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                result="_"+new SimpleDateFormat("d MMMM yyyy").format(c.getTime())+"_"+"\n\n";
                obj=(JSONObject) lang.get(4);
                break;
            case "Суббота":
                c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                result="_"+new SimpleDateFormat("d MMMM yyyy").format(c.getTime())+"_"+"\n\n";
                obj=(JSONObject) lang.get(5);
                break;
            case "Воскресенье":
                c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                result="_"+new SimpleDateFormat("d MMMM yyyy").format(c.getTime())+"_"+"\n\n";
                obj=(JSONObject) lang.get(6);
                break;

        }
        JSONArray temp=(JSONArray) obj.get(day);
        if(temp.isEmpty())
            result="*Выходной*";
        else {
            Iterator it = temp.iterator();
            while (it.hasNext()) {
                JSONObject innerObj = (JSONObject) it.next();
                result += "* Имя:*" +innerObj.get("Имя") + "\t *Время:*" + innerObj.get("Время") + "\t *Локация:*" + innerObj.get("Локация") + "\n\n";
            }
        }
        return result;
    }

    public static void addEvent(String name,String time,String location,String day) throws IOException, ParseException {
        //Делаем первый элемент id
        JSONObject addObj = new JSONObject();
        addObj.put("Имя", name);
        addObj.put("Время",time);
        addObj.put("Локация",location);

        //Добавляем в главный
        FileReader reader = new FileReader(filePath);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

        // получение массива
        JSONArray lang= (JSONArray) jsonObject.get("День");
        JSONObject obj=new JSONObject();
        JSONArray list=new JSONArray();

        switch(day){
            case "Понедельник":
                obj=(JSONObject) lang.get(0);
                list= (JSONArray) obj.get(day);
                list.add(addObj);
                jsonObject.put(day,list);
                break;
            case "Вторник":
                obj=(JSONObject) lang.get(1);
                list= (JSONArray) obj.get(day);
                list.add(addObj);
                jsonObject.put(day,list);
                break;
            case "Среда":
                obj=(JSONObject) lang.get(2);
                list= (JSONArray) obj.get(day);
                list.add(addObj);
                jsonObject.put(day,list);
                break;
            case "Четверг":
                obj=(JSONObject) lang.get(3);
                list= (JSONArray) obj.get(day);
                list.add(addObj);
                jsonObject.put(day,list);
                break;
            case "Пятница":
                obj=(JSONObject) lang.get(4);
                list= (JSONArray) obj.get(day);
                list.add(addObj);
                jsonObject.put(day,list);
                break;
            case "Суббота":
                obj=(JSONObject) lang.get(5);
                list= (JSONArray) obj.get(day);
                list.add(addObj);
                jsonObject.put(day,list);
                break;
            case "Воскресенье":
                obj=(JSONObject) lang.get(6);
                list= (JSONArray) obj.get(day);
                list.add(addObj);
                jsonObject.put(day,list);
                break;

        }
        reader.close();
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(jsonObject.toJSONString());
            writer.flush();
            writer.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }


    }
    public  String getData() {
        return Data;
    }



}
