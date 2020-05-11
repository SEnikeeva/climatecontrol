package ru.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.TokenDto;
import ru.itis.service.SignUpService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignUpController {


    @Autowired
    private SignUpService service;

    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    public ModelAndView startUserRegistration(HttpServletResponse httpServletResponse,
                                              @RequestBody MultiValueMap<String, String> formData){
        TokenDto token = service.signUp(formData);
        if(token != null){
            Cookie cookie = new Cookie("cookieName", token.getToken());
            httpServletResponse.addCookie(cookie);
            return new ModelAndView("redirect:/confirm");
        }
        else {
            return new ModelAndView("redirect:/signUp");
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/signUp")
    public ModelAndView getRegistrationPage(Authentication authentication,
                                            @RequestParam(value = "error",required = false) String error){
        if(authentication != null) return new ModelAndView("redirect:/profile");
        return new ModelAndView("signUp");
    }
}
