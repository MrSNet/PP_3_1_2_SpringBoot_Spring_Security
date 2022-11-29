package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    final BCryptPasswordEncoder bCryptPasswordEncoder;
    final RoleDao roleDao;

    @Autowired
    public UserDaoImp(BCryptPasswordEncoder bCryptPasswordEncoder, RoleDao roleDao) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleDao = roleDao;
    }

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

    @Override
    public void add(User user, Long[] rolesId) {

        User userFromDB = findByUsername(user.getUsername());
        if (userFromDB != null) {
            return;
        }

        if (rolesId != null) {
            List<Role> roleList = new ArrayList<>();
            for (int i = 0; i < rolesId.length; i++) {
                roleList.add(roleDao.findRoleById(rolesId[i]));
            }
            user.setRoles(roleList);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        entityManager.persist(user);
    }


    @Override
    public User updateUser(User user, Long[] rolesId) {

        if (rolesId != null) {
            List<Role> roleList = new ArrayList<>();
            for (int i = 0; i < rolesId.length; i++) {
                roleList.add(roleDao.findRoleById(rolesId[i]));
            }
            user.setRoles(roleList);
        } else {
            user.setRoles(findById(user.getId()).getRoles());
        }

        if (!user.getPassword().matches("")) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(findById(user.getId()).getPassword());
        }

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

}
