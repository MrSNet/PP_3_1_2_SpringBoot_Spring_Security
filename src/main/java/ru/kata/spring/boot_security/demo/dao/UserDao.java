package ru.kata.spring.boot_security.demo.dao;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


public interface UserDao {
    void add(User user, Long[] rolesId);
    User updateUser(User user, Long[] rolesId);
    List<User> listUsers();
    void deleteUserById(Long id);
    User findByUsername(String email);

    User findById (Long id);

}
