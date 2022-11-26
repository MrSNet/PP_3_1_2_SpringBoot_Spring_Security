package ru.kata.spring.boot_security.demo.repositories;
import ru.kata.spring.boot_security.demo.models.User;


public interface UserRepository {
    User findByUsername(String email);

    User findById (Long id);

}
