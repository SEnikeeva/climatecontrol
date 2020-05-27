package ru.itis.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.RestModel;
import ru.itis.dto.Messages;
import ru.itis.service.impl.ChatUserService;


import java.util.List;

@RestController
public class ChatRestController {

    @Autowired
    private ChatUserService userRepository;

    private Messages m = Messages.getInstance();


    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @RequestMapping(value = "/api/user/get")
    public List<RestModel> getMessage(@RequestParam("userId") Integer userId) {

        System.out.println("/user/get , thread " + Thread.currentThread().getId());

        List<RestModel> msgs = null;

        synchronized (m) {
            try {
                m.wait();
                msgs = userRepository.getMessage(userId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        return msgs;
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @RequestMapping(value = "/api/user/send")
    public RestModel getMessage(@RequestBody RestModel body) {

        System.out.println("/user/send " + body.getUserid() + "," + body.getMsg() +
                ", thread " + Thread.currentThread().getId());

        userRepository.sendMessage(body);

        return body;
    }
}
