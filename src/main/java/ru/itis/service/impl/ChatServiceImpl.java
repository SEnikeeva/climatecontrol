package ru.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.ChatDto;
import ru.itis.model.Chat;
import ru.itis.model.Message;
import ru.itis.model.User;
import ru.itis.repository.ChatRepository;
import ru.itis.repository.MessageRepository;
import ru.itis.repository.UserRepository;
import ru.itis.service.ChatService;

import java.util.List;

@Component
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Override
    public List<Message> history(Integer id) {
        return messageRepository.findByUser(id);
    }

    @Override
    public void createChat(Integer id) {
        repository.save(Chat.builder()
                .user_id(userRepository.find(id).get()).build());
    }
}
