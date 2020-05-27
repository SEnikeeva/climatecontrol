package ru.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.dto.SignUpForm;
import ru.itis.dto.TokenDto;
import ru.itis.service.SignUpService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.Validator;

@Controller
public class SignUpController {




    @Autowired
    private SignUpService service;

    @PreAuthorize("permitAll()")
    @PostMapping("/signUp")
    public String startUserRegistration(HttpServletResponse httpServletResponse,
                                              @Valid SignUpForm form, BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes, Model model){
        System.out.println(form);
        System.err.println(bindingResult.getAllErrors().toString());

        if(bindingResult.hasErrors()){
            return "signUp";
        }
        else {
            TokenDto token = service.signUp(form);
            if (token != null) {
                Cookie cookie = new Cookie("cookieName", token.getToken());
                httpServletResponse.addCookie(cookie);
                return "redirect:/confirm";
            } else {
                model.addAttribute("signUpForm", form);
                return "signUp";
            }
        }

    }

    @PreAuthorize("permitAll()")
    @GetMapping("/signUp")
    public String getRegistrationPage(Authentication authentication,
                                            @RequestParam(value = "error",required = false) String error, Model model){
        if (authentication != null) return "redirect:/profile";
        model.addAttribute("signUpForm", new SignUpForm());
        return "signUp";
    }
}
