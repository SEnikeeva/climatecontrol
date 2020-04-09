package ru.itis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.Dto.UserConfirmDto;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView view(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        UserConfirmDto user = (UserConfirmDto) session.getAttribute("current_user");
        modelAndView.addObject("user", user);
        modelAndView.setViewName("profile");
        return modelAndView;
    }
}
