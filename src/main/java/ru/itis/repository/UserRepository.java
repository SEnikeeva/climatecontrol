package ru.itis.repository;

import ru.itis.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Integer, User> {
    Optional<User> findByEmail(String email);


}
