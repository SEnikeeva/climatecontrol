package ru.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.ChatDto;
import ru.itis.model.Chat;
import ru.itis.repository.ChatRepository;
import ru.itis.security.defails.UserDetailsImpl;
import ru.itis.service.ChatService;

import java.util.HashMap;

@Controller
public class ChatController {

    @Autowired
    private ChatRepository repository;
    @Autowired
    private ChatService service;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/chats")
    public ModelAndView chats(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails == null) return new ModelAndView("redirect:/signIn");
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", userDetails.getUser());
        map.put("chats", repository.findChatByUserId(userDetails.getUserId()));
        return new ModelAndView("chats", map);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/chats")
    public ModelAndView createChats(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    ChatDto chat) {
        if (userDetails == null) return new ModelAndView("redirect:/signIn");
        service.createChat(chat, userDetails.getUser());
        return new ModelAndView("redirect:/chats");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/chat/{name}")
    public ModelAndView getChatPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PathVariable String name) {
        if (userDetails == null) return new ModelAndView("redirect:/signIn");
        Chat chat = repository.findByName(name).get();
        ModelAndView maw = new ModelAndView("chat");
        maw.addObject("history", service.history(chat.getId()));
        maw.addObject("pageId", chat.getId());
        maw.addObject("userId", userDetails.getUserId());
        return maw;
    }

}
