package ru.itis.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.model.Message;
import ru.itis.repository.ChatRepository;
import ru.itis.repository.MessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class MessageRepositoryJpaImpl implements MessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ChatRepository chatRepository;

    private static final String FIND_MESSAGES_BY_CHAT = "SELECT m FROM Message m WHERE m.chat_id = :chat_id";

    @Override
    public List<Message> findByChat(Long id) {
        Query query = entityManager.createQuery(FIND_MESSAGES_BY_CHAT, Message.class);
        query.setParameter("chat_id", chatRepository.find(id).get());
        return (List<Message>) query.getResultList();
    }

    @Override
    public Optional<Message> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Message> findAll() {
        return null;
    }

    @Override
    public void save(Message entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(Message entity) {
        entityManager.merge(entity);
    }
}
