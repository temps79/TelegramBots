package TelegramBot.hibernate.service;

import TelegramBot.hibernate.dao.MessageDaoImpl;
import TelegramBot.hibernate.dao.UserDao;
import TelegramBot.hibernate.dao.UserDaoImpl;
import TelegramBot.hibernate.model.Message;
import TelegramBot.hibernate.model.User;

import java.util.List;

public class UserService {
    private UserDaoImpl usersDao = new UserDaoImpl();


    public UserService() {
    }

    public User findUser(String chatid) {
        return usersDao.findById(chatid);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }
    public void updateUserState(User user,String state){
        usersDao.updateState(user,state);
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }
    public boolean containsChatId(String chatId){
        return usersDao.containsChatId(chatId);
    }





}
