package ru.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.Dto.UserConfirmDto;
import ru.itis.model.Role;
import ru.itis.model.State;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;
import ru.itis.service.SignUpService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public UserConfirmDto signUp(HttpServletRequest req) {
        
        userRepository.save( User.builder().email(req.getParameter("email"))
                .password(encoder.encode(req.getParameter("password")))
                .confirmCode(UUID.randomUUID().toString())
                .state(State.NOT_CONFIRMED)
                .role(Role.USER)
                .build());
        return UserConfirmDto.from(userRepository.findByEmail(req.getParameter("email")).get());
    }
}
