package ru.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.RestModel;
import ru.itis.dto.Messages;
import ru.itis.model.Message;
import ru.itis.model.User;
import ru.itis.repository.MessageRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatUserService {

    @Autowired
    MessageRepository messageRepository;

    private Messages m = Messages.getInstance();



    public synchronized List<RestModel> getMessage(Integer userId) {

        List<RestModel> msgs = new ArrayList<>();

        m.fromadminmessage.forEach((userid, userMessage) -> {
            if (userId.equals(userid)) {
                msgs.add(userMessage);
            }

        });

        m.fromadminmessage.clear();

        return msgs;
    }

    public void sendMessage(RestModel msg) {
        Message message1 = Message.builder()
                .sender(User.builder().id(msg.getUserid()).build())
                .time(new Date(new java.util.Date().getTime()))
                .message(msg.getMsg())
//                .chat_id(Chat.builder().id(100L).build())
                .build();
        messageRepository.save(message1);
        synchronized (m) {
            System.out.println("in repository, in synchronized block put message");
            m.newmessage.put(msg.getUserid(), msg.getMsg());
            m.notifyAll();
        }
    }
}
