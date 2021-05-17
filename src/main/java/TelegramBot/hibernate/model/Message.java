package TelegramBot.hibernate.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @Column
    @Getter
    @Setter
    private String chatId;

    public Message(String chatId) {
        this.chatId = chatId;
    }
    public Message() {

    }
    @Column
    @Getter
    @Setter
    private String name;
    @Column
    @Getter
    @Setter
    private String time;
    @Column
    @Getter
    @Setter
    private String contact;

    @OneToOne(mappedBy = "message")
    private User user;

    @Override
    public String toString() {
        return String.format("Telegram_Name:%s|\nИмя:%s\nВремя:%s\nКонтакты:%s",user.getName(),name,time,contact);
    }
}
