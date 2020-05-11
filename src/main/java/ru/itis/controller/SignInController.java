package ru.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.SignInDto;
import ru.itis.dto.TokenDto;
import ru.itis.service.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
public class SignInController {

    @Autowired
    private SignInService signInService;

    @PreAuthorize("permitAll()")
    @PostMapping("/signIn")
    public ModelAndView signIn(HttpServletResponse httpServletResponse,
                               @RequestParam(value = "email") String email,
                               @RequestParam(value = "password") String password) {
        TokenDto token = signInService.signIn(SignInDto.builder().email(email).password(password).build());
        Cookie cookie = new Cookie("cookieName", token.getToken());
        httpServletResponse.addCookie(cookie);
        return new ModelAndView("redirect:/profile");
    }


    @PreAuthorize("permitAll()")
    @GetMapping(value = "/signIn")
    public ModelAndView getSignIn() {
        return new ModelAndView("signIn");
    }
}
