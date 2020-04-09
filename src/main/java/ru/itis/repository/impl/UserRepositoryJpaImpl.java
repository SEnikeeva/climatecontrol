package ru.itis.repository.impl;

import org.springframework.stereotype.Component;
import ru.itis.model.User;
import ru.itis.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

////@Component
//public class UserRepositoryJpaImpl implements UserRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public Optional<User> find(Integer id) {
//        return Optional.of(entityManager.find(User.class, id));
//    }
//
//    @Override
//    public List<User> findAll() {
////        Query query = entityManager.createQuery("SELECT e FROM User e");
////        return (List<User>) query.getResultList();
//        return null;
//    }
//
//    @Override
//    @Transactional
//    public void save(User entity) {
//        entityManager.persist(entity);
//    }
//
//    @Override
//    public void delete(Integer integer) {
//
//    }
//
//    @Override
//    public void update(User entity) {
//
//    }
//
//    @Override
//    public Optional<User> findByEmail(String email) {
//        return Optional.empty();
//    }
//}
