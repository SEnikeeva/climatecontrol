package ru.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.Dto.SignInDto;
import ru.itis.Dto.TokenDto;
import ru.itis.service.SignInService;


@RestController
public class SignInController {

    @Autowired
    private SignInService signInService;

    @PostMapping("/signIn")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInData) {
        return ResponseEntity.ok(signInService.signIn(signInData));
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public ModelAndView getSignIn() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signIn");
        return modelAndView;
    }
}
