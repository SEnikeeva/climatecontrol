package ru.itis.service;

import ru.itis.model.User;

import java.util.Optional;

public interface UserService {

    boolean checkUser(String login);

    Optional<User> checkPassword(String login, String password);

    Optional<User> getUserByLogin(String login);
}
