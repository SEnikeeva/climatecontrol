package ru.itis.repository;

import ru.itis.model.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends CrudRepository<Long, Chat> {

    Optional<Chat> findChatByUserId(Integer user_id);

    Optional<Chat> findByName(String name);
}
