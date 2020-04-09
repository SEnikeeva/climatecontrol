package ru.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.Dto.UserConfirmDto;
import ru.itis.service.ConfirmService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class ConfirmController {

    @Autowired
    private ConfirmService service;

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView confirm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("confirm");
        return modelAndView;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    protected String confirm(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Optional<UserConfirmDto> user= service.checkCode(req);
        if (user.isPresent()) {
            session.setAttribute("current_user", user.get());
            return "redirect:" + req.getScheme() + "://localhost:8070/profile";
        } else {
            return "redirect:" + req.getScheme() + "://localhost:8070/confirm";
        }

    }
}
