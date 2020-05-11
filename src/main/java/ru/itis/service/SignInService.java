package ru.itis.service;

import ru.itis.dto.SignInDto;
import ru.itis.dto.TokenDto;

public interface SignInService {
    TokenDto signIn(SignInDto signInData);

   // UserConfirmDto signIn(HttpServletRequest request);
}
