package TelegramBot.hibernate.dao;

import TelegramBot.hibernate.model.User;

import java.util.List;

public interface UserDao {
    public User findById(String chatId) ;
    public void save(User user);
    public void update(User user);
    public void delete(User user) ;
    public List<User> findAll() ;
}
