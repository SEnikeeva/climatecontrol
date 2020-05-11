package ru.itis.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryJpaImpl implements UserRepository {

    private static final String FIND_BY_EMAIL =
            "select u from User u where u.email = :email";
    private static final String FIND_ALL =
            "select u from User u";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> find(Integer id) {
        return Optional.of(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        Query query = entityManager.createQuery(FIND_ALL, User.class);
        return (List<User>) query.getResultList();
    }

    @Override
    public void save(User entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(User entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(FIND_BY_EMAIL, User.class);
        query.setParameter("email", email);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
