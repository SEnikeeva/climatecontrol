package ru.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.dto.UserConfirmDto;
import ru.itis.model.State;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;
import ru.itis.service.ConfirmService;

import java.util.Optional;

@Component
public class ConfirmServiceImpl implements ConfirmService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<UserConfirmDto> checkCode(String code, User user) {
        UserConfirmDto userConfirmDto = UserConfirmDto.from(user);
        if (code.equals(userConfirmDto.getConfirmCode())) {
            user.setState(State.CONFIRMED);
            userRepository.update(user);
            return Optional.of(userConfirmDto);
        }
        return Optional.empty();
    }
}
