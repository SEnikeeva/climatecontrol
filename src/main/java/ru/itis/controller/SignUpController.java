package ru.itis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.Dto.UserConfirmDto;
import ru.itis.service.SendEmailService;
import ru.itis.service.SignUpService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SignUpController {

    @Autowired
    private SendEmailService emailService;
    @Autowired
    private SignUpService service;


    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView signUp() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signUp");
        return modelAndView;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    protected ModelAndView register(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = req.getSession();
        UserConfirmDto user = service.signUp(req);

        Map<String, Object> root = new HashMap<>();
        root.put("user", user);

        emailService.sendCode("Подтверждение", user.getEmail(), root);

        session.setAttribute("current_user", user);
        modelAndView.setViewName("confirm");
        return modelAndView;
    }
}
