package ru.itis.service;

import ru.itis.Dto.SignInDto;
import ru.itis.Dto.TokenDto;
import ru.itis.Dto.UserConfirmDto;

import javax.servlet.http.HttpServletRequest;

public interface SignInService {
    TokenDto signIn(SignInDto signInData);

    UserConfirmDto signIn(HttpServletRequest request);
}
