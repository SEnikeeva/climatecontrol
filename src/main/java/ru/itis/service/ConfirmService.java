package ru.itis.service;

import ru.itis.dto.UserConfirmDto;
import ru.itis.model.User;

import java.util.Optional;

public interface ConfirmService {
    Optional<UserConfirmDto> checkCode(String code, User user);
}
