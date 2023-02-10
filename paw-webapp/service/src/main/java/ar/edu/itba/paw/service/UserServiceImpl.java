package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.PasswordPair;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistance.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
    public Optional<User> getUserByID(Long id) {
        if(id == null){
            return Optional.empty();
        }
        return userDao.getUserById(id);
    }

    @Transactional
    @Override
    public User create(String username, PasswordPair psPair, Roles role, Long customerId) {
        if(!Objects.equals(psPair.getPassword(), psPair.getCheckPassword())){
            return null;
        }
        if(customerId == null){ // only create
            return userDao.create(username, passwordEncoder.encode(psPair.getPassword()), role);
        }
        // create and link
        User user = userDao.create(username, passwordEncoder.encode(psPair.getPassword()), role);
        cs.getCustomerById(customerId).ifPresent(c -> {
            c.setUser(user);
        });
        return user;
    }

//    @Transactional
//    @Override
//    public User createAndLinkToCustomer(String username, PasswordPair psPair, Roles role, Long customerId) {
//        User user = userDao.create(username, passwordEncoder.encode(psPair.getPassword()), role);
//        cs.getCustomerById(customerId).ifPresent(c -> {
//            c.setUser(user);
//        });
//        return user;
//    }

//    @Transactional
//    @Override
//    public void createUserAndCustomer(String username, String password, Roles role, String customerName, String phone, String mail) {
//        User user = userDao.create(username, password, role);
//        Customer customer = cs.create(customerName, phone, mail);
//        customer.setUser(user);
//    }

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
    public Optional<User> getUserByUsername(String username) {
        return userDao.findByName(username);
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        Optional<User> user = getUserByID(id);
        if(user.isPresent()){
            Optional<Customer> customer = cs.getCustomerByUsername(user.get().getUsername());
            boolean success = userDao.deleteUser(id);
            if(success && customer.isPresent()){
                customer.get().setUser(null);
            }
            return success;
        } else {
            return false;
        }
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Transactional
    @Override
    public boolean patchUser(long id, String username, PasswordPair psPair){
        Optional<User> maybeUser = userDao.getUserById(id);
        if(! maybeUser.isPresent())
            return false;

        if(username != null){
            maybeUser.get().setUsername(username);
        }
        if(psPair != null){
            if(Objects.equals(psPair.getPassword(), psPair.getCheckPassword())){
                maybeUser.get().setPassword(passwordEncoder.encode(psPair.getPassword()));
            } else {
                return false;
            }
        }
        return true;
    }
}
