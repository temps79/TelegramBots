package TelegramBot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyCalendar {
    private Calendar date;
    private String Month;

    private int ArrayOfDay[][]=new int[6][7];

    MyCalendar(){
        Calendar date=Calendar.getInstance();
        Month=new SimpleDateFormat("MMMM").format(date.getTime());

        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                ArrayOfDay[i][j]=0;
            }
        }
    }

    public static void main(String[] args) {
        MyCalendar date1=new MyCalendar();

    }
    public String getMonth() {
        return Month;
    }


}
