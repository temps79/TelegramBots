package TelegramBot.hibernate.service;

import TelegramBot.hibernate.dao.MessageDaoImpl;
import TelegramBot.hibernate.model.Message;
import TelegramBot.hibernate.model.User;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
public class MessageService {
    private MessageDaoImpl messageDao=new MessageDaoImpl();

    public void saveMessage(Message message){
        messageDao.save(message);
    }

    public void deleteMessage(Message message){
        messageDao.delete(message);
    }

    public List<Message> findAllMessages(){
        return messageDao.findAll();
    }

    public Message findMessage(String chatid) {
        return messageDao.findById(chatid);
    }

    public void updateMessage(Message message){
        messageDao.update(message);
    }
}
