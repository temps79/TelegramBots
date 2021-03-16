package TelegramBot.ability;

public enum Weeks {
    SUNDAY ("ВС"),
    MONDAY ("ПН"),
    TUESDAY ("ВТ"),
    WEDNESDAY ("СР"),
    THURSDAY ("ЧТ"),
    FRIDAY ("ПТ"),
    SATURDAY ("СБ");

    private String title;

    Weeks(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static boolean contains(String title){
        for(Weeks weeks:Weeks.values())
            if(weeks.title.equals(title))
                return true;
        return false;
    }

    public static int getDayOfWeeks(String title){
        if(contains(title))
            for (Weeks weeks : Weeks.values())
                if (weeks.title.equals(title))
                    return weeks.ordinal();
        return 0;

    }

}
