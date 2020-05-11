package ru.itis.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.MessageDto;
import ru.itis.model.Chat;
import ru.itis.model.Message;
import ru.itis.model.User;
import ru.itis.repository.ChatRepository;
import ru.itis.repository.MessageRepository;
import ru.itis.service.ChatService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessagesController {
    private static final Map<Long, List<Message>> messages = new HashMap<>();
    @Autowired
    private ChatService chatService;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    MessageRepository messageRepository;

    // получили сообщение от какой либо страницы - мы его разошлем во все другие страницы
    @PostMapping("/messages")
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageDto message) {
        // если сообщений с этой или для этой страницы еще не было
        if (!messages.containsKey(message.getPageId())) {
            // добавляем эту страницу в Map-у страниц
            messages.put(message.getPageId(), new ArrayList<>());
        }
        // полученное сообщение добавляем для всех открытых страниц нашего приложения
        for (List<Message> messages : messages.values()) {
            // перед тем как положить сообщение для какой-либо страницы
            // мы список сообщений блокируем
            synchronized (messages) {
                // добавляем сообщение
                if (!message.getText().equals("Login")) {
                    Message message1 = Message.builder()
                            .sender(User.builder().id(message.getUserId()).build())
                            .time(new Date(new java.util.Date().getTime()))
                            .message(message.getText())
                            .chat_id(Chat.builder().id(message.getPageId()).build())
                            .build();
                    messageRepository.save(message1);
                    messages.add(message1);
                    messages.notifyAll();
                }
                // говорим, что другие потоки могут воспользоваться этим списком
            }
        }
        return ResponseEntity.ok().build();
    }


    // получить все сообщения для текущего запроса
    @SneakyThrows
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessagesForPage(@RequestParam("pageId") Long pageId) {
        // получили список сообшений для страницы и заблокировали его
        if (messages.get(pageId) != null) {
            synchronized (messages.get(pageId)) {
                // если нет сообщений уходим в ожидание
                if (messages.get(pageId).isEmpty()) {
                    messages.get(pageId).wait();
                }
            }
            // если сообщения есть - то кладем их в новых список
            List<Message> response = new ArrayList<>(messages.get(pageId));
            // удаляем сообщения из мапы
            messages.get(pageId).clear();
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok().build();
    }
}
