package TelegramBot;

public class Person {
    static String Name;
    static String Date;
    static int status=0;
    static String Number;

    public static String getDate() {
        return Date;
    }

    public static String getNumber() {
        return Number;
    }

    public static void setNumber(String number) {
        Number = number;
    }

    public static void setStatus(int status) {
        Person.status = status;
    }

    public static int getStatus() {
        return status;
    }

    public static void setDate(String date) {
        Date = date;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static String getName() {
        return Name;
    }


}