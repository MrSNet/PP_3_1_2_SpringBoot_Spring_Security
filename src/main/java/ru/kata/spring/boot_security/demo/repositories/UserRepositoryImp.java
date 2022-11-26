package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class UserRepositoryImp implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByUsername(String email) {

        try {
           return entityManager.createQuery("select user from User user where user.email = :email", User.class)
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException nre){
            return null;
        }

    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }
}
