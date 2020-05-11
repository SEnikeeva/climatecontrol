package ru.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;
import ru.itis.service.UserService;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean checkUser(String login) {
        return userRepository.findByEmail(login).isPresent();
    }

    @Override
    public Optional<User> checkPassword(String login, String password) {
        if ( userRepository.findByEmail(login).isPresent()) {
            User user = userRepository.findByEmail(login).orElseThrow(IllegalArgumentException::new);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            } else
                return Optional.empty();
        }
        else return Optional.empty();
    }

    @Override
    public Optional<User> getUserByLogin(String login){
        User user = userRepository.findByEmail(login).orElseThrow(IllegalArgumentException::new);
        return Optional.of(user);
    }
}
