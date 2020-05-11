package ru.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.ChatDto;
import ru.itis.model.Chat;
import ru.itis.model.Message;
import ru.itis.model.User;
import ru.itis.repository.ChatRepository;
import ru.itis.repository.MessageRepository;
import ru.itis.service.ChatService;

import java.util.List;

@Component
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository repository;

    @Autowired
    MessageRepository messageRepository;

    @Override
    public List<Message> history(Long id) {
        return messageRepository.findByChat(id);
    }

    @Override
    public void createChat(ChatDto chat, User user) {
        repository.save(Chat.builder()
                .name(chat.getName()).user_id(user).build());
    }
}
