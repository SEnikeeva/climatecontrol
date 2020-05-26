package ru.itis.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.model.Chat;
import ru.itis.repository.ChatRepository;
import ru.itis.repository.UserRepository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ChatRepositoryJpaImpl implements ChatRepository {
    private static final String FIND_ALL =
            "select c from Chat c";
    private static final String FIND_BY_NAME =
            "select u from Chat u where u.name = :name";
    private static final String FIND_BY_USER = "select u from Chat u where u.user_id = :user_id";


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<Chat> find(Long id) {
        return Optional.of(entityManager.find(Chat.class, id));
    }

    @Override
    public List<Chat> findAll() {
        Query query = entityManager.createQuery(FIND_ALL, Chat.class);
        return (List<Chat>) query.getResultList();
    }

    @Override
    public void save(Chat entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Optional<Chat> findChatByUserId(Integer user_id) {
        TypedQuery<Chat> query = entityManager.createQuery(FIND_BY_USER, Chat.class);
        query.setParameter("user_id", userRepository.find(user_id).get());
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Chat entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<Chat> findByName(String name) {
        TypedQuery<Chat> query = entityManager.createQuery(FIND_BY_NAME, Chat.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
