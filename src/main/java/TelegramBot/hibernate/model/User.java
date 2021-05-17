package TelegramBot.hibernate.model;

import TelegramBot.states.ReadyState;
import TelegramBot.states.State;

import javax.persistence.*;

@Entity
@Table (name = "users")
public class User {
    @Id
    @Column(name = "chatid")
    private String ChatId;

    @Column(name="name")
    private String Name;

    @Column(name="state")
    private String State;

    public String getState() {
        return State;
    }
    public  void setState(String State){
        this.State=State;
    }

    public User() {
    }

    public User(String chatId, String name) {
        ChatId = chatId;
        Name = name;
        State="ReadyState";
    }

    public String getChatId() {
        return ChatId;
    }
    public void setChatId(String chatId) {
        ChatId = chatId;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="chatid")
    private Message message;
}
