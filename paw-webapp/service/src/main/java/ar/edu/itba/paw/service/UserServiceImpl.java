package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistance.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService cs;

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder, final CustomerService cs) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.cs = cs;
    }

    @Transactional
    @Override
    public Optional<User> getUserByID(long id) {
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public User create(String username, String password, Roles role) {
        return userDao.create(username, passwordEncoder.encode(password), role);
    }

    @Transactional
    @Override
    public User createAndLinkToCustomer(String username, String password, Roles role, long customerId) {
        User user = userDao.create(username, password, role);
        cs.getCustomerById(customerId).ifPresent(c -> {
            c.setUser(user);
        });
        return user;
    }

    @Transactional
    @Override
    public void createUserAndCustomer(String username, String password, Roles role, String customerName, String phone, String mail) {
        User user = userDao.create(username, password, role);
        Customer customer = cs.create(customerName, phone, mail);
        customer.setUser(user);
    }

    @Transactional
    @Override
    public Optional<User> findByName(String username) {
        return userDao.findByName(username);
    }

    @Transactional
    @Override
    public void updatePassword(String username, String newPassword) {
        getUserByUsername(username).ifPresent( u -> {
            u.setPassword(passwordEncoder.encode(newPassword));
        });
//        userDao.updatePassword(username, passwordEncoder.encode(newPassword));
    }

    @Override
    public void updateUsername(String oldUsername, String newUsername) {
//        userDao.updateUsername(oldUsername, newUsername);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userDao.findByName(username);
    }
}
