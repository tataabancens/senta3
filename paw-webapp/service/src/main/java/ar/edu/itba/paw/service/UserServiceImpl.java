package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Roles;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistance.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUserByID(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User create(String username, String password, Roles role) {
        return userDao.create(username, passwordEncoder.encode(password), role);
    }

    @Override
    public Optional<User> findByName(String username) {
        return userDao.findByName(username);
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        userDao.updatePassword(username, passwordEncoder.encode(newPassword));
    }
}
