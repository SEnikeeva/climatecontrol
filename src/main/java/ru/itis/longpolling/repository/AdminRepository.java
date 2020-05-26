package ru.itis.longpolling.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.longpolling.controller.rest.RestModel;
import ru.itis.model.Message;
import ru.itis.model.User;
import ru.itis.repository.MessageRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdminRepository {
    @Autowired
    MessageRepository messageRepository;

    private Messages m = Messages.getInstance();

    public List<RestModel> getAllMessages() {

        List<RestModel> msgs = new ArrayList<>();

        m.messages.forEach((userid, userMessages) -> {
            for (String s : userMessages) {
                msgs.add(new RestModel( userid, s));
            }
        });

        return msgs;
    }

    public synchronized List<RestModel> getMessage() {

        List<RestModel> msgs = new ArrayList<>();

        m.newmessage.forEach((userid, userMessage) -> {
                msgs.add(new RestModel( userid, userMessage));
        });

        m.newmessage.clear();

        return msgs;
    }

    public void sendMessage(RestModel msg, Integer userId) {
        Message message1 = Message.builder()
                .sender(User.builder().id(msg.getUserid()).build())
                .time(new Date(new java.util.Date().getTime()))
                .message(msg.getMsg())
//                .chat_id(Chat.builder().id(100L).build())
                .build();
        messageRepository.save(message1);
        synchronized (m) {
            System.out.println("in repository, in synchronized block put message");
            m.fromadminmessage.put(userId, msg);
            m.notifyAll();
        }
    }
}
