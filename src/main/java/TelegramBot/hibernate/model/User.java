package TelegramBot.hibernate.model;

import javax.persistence.*;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "chatid")
    private String ChatId;
    //можно не указывать Column name, если оно совпадает с названием столбца в таблице
    @Column(name="name")
    private String Name;



    public User() {
    }

    public User(String chatId, String name) {
        ChatId = chatId;
        Name = name;
    }

    public String getChatId() {
        return ChatId;
    }

    public void setChatId(String chatId) {
        ChatId = chatId;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return Name;
    }




    public void setName(String name) {
        Name = name;
    }


    @Override
    public String toString() {
        return "Имя:"+getName()+"\nНомер чата:"+getChatId()+"\n---------------------------------\n";
    }
}
