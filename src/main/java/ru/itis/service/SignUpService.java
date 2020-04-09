package ru.itis.service;

import ru.itis.Dto.UserConfirmDto;

import javax.servlet.http.HttpServletRequest;

public interface SignUpService {
    UserConfirmDto signUp(HttpServletRequest req);
}
