package ru.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.Dto.UserConfirmDto;
import ru.itis.model.State;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;
import ru.itis.service.ConfirmService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class ConfirmServiceImpl implements ConfirmService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<UserConfirmDto> checkCode(HttpServletRequest req) {
        HttpSession session = req.getSession();
        UserConfirmDto userConfirmDto = (UserConfirmDto) session.getAttribute("current_user");
        if (req.getParameter("code").equals(userConfirmDto.getConfirmCode())) {
            User user = userRepository.find(userConfirmDto.getId()).get();
            user.setState(State.CONFIRMED);
            userRepository.update(user);
            return Optional.of(userConfirmDto);
        }
        return Optional.empty();
    }
}
