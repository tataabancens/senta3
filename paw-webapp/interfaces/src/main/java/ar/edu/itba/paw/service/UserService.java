package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserByID(long id);

    User create(String username, String password, Roles role);

    User createAndLinkToCustomer(String username, String password, Roles role, long customerId);

    Optional<User> findByName(String username);

    void updatePassword(String username, String newPassword);

    void updateUsername(String oldUsername, String newUsername);

    Optional<User> getUserByUsername(String username);

    void createUserAndCustomer(String username, String password, Roles customer, String customerName, String phone, String mail);

    void deleteById(long id);
}
