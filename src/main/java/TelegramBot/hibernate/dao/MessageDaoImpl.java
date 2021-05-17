package TelegramBot.hibernate.dao;

import TelegramBot.hibernate.model.Message;
import TelegramBot.hibernate.model.User;
import TelegramBot.hibernate.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class MessageDaoImpl implements MessageDao{

    @Override
    public Message findById(String chatId) {
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Message message = session.get(Message.class, chatId);
        tx1.commit();
        session.close();
        return message;
    }

    public void save(Message message) {
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(message);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Message message) {
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(message);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Message message) {
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(message);
        tx1.commit();
        session.close();
    }

    @Override
    public List<Message> findAll() {
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<Message>  message = session.createQuery("From Message ").list();
        tx1.commit();
        session.close();
        return message;
    }
}
