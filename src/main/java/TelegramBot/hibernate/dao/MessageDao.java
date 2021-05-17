package TelegramBot.hibernate.dao;

import TelegramBot.hibernate.model.Message;
import TelegramBot.hibernate.model.User;

import java.util.List;

public interface MessageDao {
    public Message findById(String chatId) ;
    public void save(Message message);
    public void update(Message message);
    public void delete(Message message) ;
    public List<Message> findAll() ;
}
