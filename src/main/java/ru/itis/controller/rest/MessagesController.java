package ru.itis.controller.rest;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

//@RestController
public class MessagesController {
    private static final Map<Long, List<Message>> messages = new HashMap<>();
    @Autowired
    private ChatService chatService;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    MessageRepository messageRepository;

    // получили сообщение от какой либо страницы - мы его разошлем во все другие страницы
//    @PostMapping("/messages")
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageDto message) {
        // если сообщений с этой или для этой страницы еще не было
        if (!messages.containsKey(message.getPageId())) {
            // добавляем эту страницу в Map-у страниц
            messages.put(message.getPageId(), new ArrayList<>());
        }
        List<Message> messages1 = messages.get(message.getPageId());

        // полученное сообщение добавляем для всех открытых страниц нашего приложения
//        for (List<Message> messages1 : messages.values()) {
//            // перед тем как положить сообщение для какой-либо страницы
//            // мы список сообщений блокируем
            synchronized (messages.get(message.getPageId())) {
                // добавляем сообщение
                if (!message.getText().equals("Login")) {
                    Message message1 = Message.builder()
                            .sender(User.builder().id(message.getUserId()).build())
                            .time(new Date(new java.util.Date().getTime()))
                            .message(message.getText())
//                            .chat_id(Chat.builder().id(message.getPageId()).build())
                            .build();
                    messageRepository.save(message1);
                    messages1.add(message1);
                    messages.put(message.getPageId(), messages1);
                    messages.get(message.getPageId()).notifyAll();
                    return ResponseEntity.ok(messages1);

                }
                messages.get(message.getPageId()).notifyAll();
                // говорим, что другие потоки могут воспользоваться этим списком
            }
//        }
        return ResponseEntity.ok().build();
    }


    // получить все сообщения для текущего запроса
    @SneakyThrows
//    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessagesForPage(@RequestParam("pageId") Long pageId,
                                                            @RequestParam("userId") Integer userId) {
        // получили список сообшений для страницы и заблокировали его
//        if (messages.get(pageId) != null) {
            synchronized (messages.get(pageId)) {
                // если нет сообщений уходим в ожидание
                while (messages.get(pageId).isEmpty()) {
                    messages.get(pageId).wait();
                }
            }
            // если сообщения есть - то кладем их в новых список
            List<Message> response = new ArrayList<>(messages.get(pageId));
            // удаляем сообщения из мапы
            messages.get(pageId).clear();
            return ResponseEntity.ok(response);
//        }
//        return ResponseEntity.ok().build();
    }

/*    @PreAuthorize("isAuthenticated()")
    @GetMapping("/admin/messages/start")
    public ResponseEntity<List<MessageDto>> start(@RequestParam("userId") Long userId) {
        List<MessageDto> messageDtos = new ArrayList<>();
        messageService.makeAllMessagesFromUserDelivered(userId);
        for (Message m : messageService.findAllForUser(userId)
        ) {
            messageDtos.add(MessageDto.fromMessage(m));
        }
        return ResponseEntity.ok(messageDtos);*/
}
