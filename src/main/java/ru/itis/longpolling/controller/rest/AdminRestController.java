package ru.itis.longpolling.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.longpolling.repository.AdminRepository;
import ru.itis.longpolling.repository.Messages;

import java.util.List;

@RestController
public class AdminRestController {

    private Messages m = Messages.getInstance();

    @Autowired
    private AdminRepository repository;

    @PreAuthorize("permitAll()")
    @ResponseBody
    @RequestMapping(value = "/admin/getall")
    public RestModel getAllMessage() {

        System.out.println("/admin/getall, thread " + Thread.currentThread().getId());

        return new RestModel(1,"привет 1");
    }
    @PreAuthorize("permitAll()")
    @ResponseBody
    @RequestMapping(value = "/admin/get")
    public List<RestModel> getMessage() {

        System.out.println("/admin/get , thread " + Thread.currentThread().getId());

        List<RestModel> msgs = null;

        synchronized (m) {
            try {
                m.wait();
                msgs = repository.getMessage();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        return msgs;
    }

    @PreAuthorize("permitAll()")
    @ResponseBody
    @RequestMapping(value = "/admin/send/{userId}")
    public RestModel getMessage(@RequestBody RestModel body, @PathVariable Integer userId) {

        System.out.println("/admin/send " + body.getUserid() + "," + body.getMsg() +
                ", thread " + Thread.currentThread().getId() + userId);

        repository.sendMessage(body, userId);
        return body;
    }
}
