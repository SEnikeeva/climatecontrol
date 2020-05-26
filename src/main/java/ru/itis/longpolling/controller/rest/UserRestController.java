package ru.itis.longpolling.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.longpolling.repository.Messages;
import ru.itis.longpolling.repository.UserRepository;


import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    private Messages m = Messages.getInstance();

    @PreAuthorize("permitAll()")
    @ResponseBody
    @RequestMapping(value = "/user/getall")
    public RestModel getAllMessage() {

        System.out.println("/user/getall, thread " + Thread.currentThread().getId());

        return new RestModel(1,"привет 1");
    }

    @PreAuthorize("permitAll()")
    @ResponseBody
    @RequestMapping(value = "/user/get")
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

    @PreAuthorize("permitAll()")
    @ResponseBody
    @RequestMapping(value = "/user/send")
    public RestModel getMessage(@RequestBody RestModel body) {

        System.out.println("/user/send " + body.getUserid() + "," + body.getMsg() +
                ", thread " + Thread.currentThread().getId());

        userRepository.sendMessage(body);

        return body;
    }
}
