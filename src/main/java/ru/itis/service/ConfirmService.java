package ru.itis.service;

import ru.itis.Dto.UserConfirmDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface ConfirmService {
    Optional<UserConfirmDto> checkCode(HttpServletRequest req);
}
