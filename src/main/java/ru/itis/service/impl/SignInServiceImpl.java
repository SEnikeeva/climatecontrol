package ru.itis.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.Dto.SignInDto;
import ru.itis.Dto.TokenDto;
import ru.itis.Dto.UserConfirmDto;
import ru.itis.service.SignInService;
import ru.itis.service.UserService;

import javax.servlet.http.HttpServletRequest;

//@Component
public class SignInServiceImpl implements SignInService {

    @Autowired
    private UserService service;

    @Override
    public TokenDto signIn(SignInDto signInData) {
        return null;
    }

    @Override
    public UserConfirmDto signIn(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (service.checkPassword(login, password).isPresent())
            return UserConfirmDto.from(service.checkPassword(login, password).get());
        else return null;
    }
}
