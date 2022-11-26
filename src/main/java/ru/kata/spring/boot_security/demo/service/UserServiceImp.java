package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
@Transactional
@Service
public class UserServiceImp implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void add(User user) {

        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        entityManager.persist(user);
    }


    @Override
    public User updateUser(User user) {
        return entityManager.merge(user);
    }

    @Override
    public List<User> listUsers() {

        String jpql = "select u from User u";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);

        return query.getResultList();
    }

    @Override
    public void deleteUserById(Long id) {
        entityManager.remove(findById(id));
    }

    @Override
    public User findById(Long id) {
       return entityManager.find(User.class, id);
    }
}
