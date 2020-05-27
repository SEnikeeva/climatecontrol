package ru.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.ChatDto;
import ru.itis.model.Chat;
import ru.itis.repository.ChatRepository;
import ru.itis.security.defails.UserDetailsImpl;
import ru.itis.service.ChatService;

import java.util.Collections;
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

        if (userDetails.getRole().equals("ADMIN")) {
            map.put("chats", repository.findAll());
        } else {
            map.put("chats",
                    Collections.singletonList(repository
                            .findChatByUserId(userDetails.getUserId()).orElse(null)));

        }

        return new ModelAndView("chats", map);
    }

    @PreAuthorize("permitAll()")
//    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/chats")
    public ModelAndView createChats(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(userDetails.getAuthorities());
        if (userDetails == null) return new ModelAndView("redirect:/signIn");
        service.createChat(userDetails.getUser().getId());
        return new ModelAndView("redirect:/chats");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/chat/{userId}")
    public ModelAndView getChatPage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @PathVariable Integer userId, @ModelAttribute("model") ModelMap model) {
        if (userDetails == null) return new ModelAndView("redirect:/signIn");
        Chat chat = repository.findChatByUserId(userId).get();
        ModelAndView maw = new ModelAndView();
        if (userDetails.getRole().equals("ADMIN")) {
            maw.setViewName("admin");
            maw.addObject("user", userId);
        } else {
            maw.setViewName("user");
        }
        maw.addObject("history", service.history(userId));
//        maw.addObject("pageId", chat.getId());
        maw.addObject("userId", userDetails.getUserId());

        return maw;
    }

}
