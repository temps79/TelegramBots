package TelegramBot.hibernate.dao;

import TelegramBot.hibernate.model.Message;
import TelegramBot.hibernate.model.User;
import TelegramBot.hibernate.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoImpl implements UserDao{


    public User findById(String chat_id) {
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        User user = session.get(User.class, chat_id);
        tx1.commit();
        session.close();
        return user;
    }
    public void updateState(User user,String State){
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery("update User set State=:state where ChatId=:chatId").
                setParameter("state", State).
                setParameter("chatId", user.getChatId());
        query.executeUpdate();
        tx1.commit();
        session.close();
    }
    public void save(User user) {
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }
    public void update(User user) {
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();

    }
    public void delete(User user) {
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }
    public List<User> findAll() {
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<User>  users = session.createQuery("From User").list();
        tx1.commit();
        session.close();
        return users;
    }
    public boolean containsChatId(String chatId){
        boolean result = false;
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        if (session.get(User.class, chatId) != null)
            result = true;
        tx1.commit();
        session.close();
        return result;
    }


}
