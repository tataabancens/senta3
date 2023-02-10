package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.PasswordPair;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserByID(Long id);

    User create(String username, PasswordPair psPair, Roles role, Long customerId);

//    User createAndLinkToCustomer(String username, PasswordPair psPair, Roles role, Long customerId);

    Optional<User> findByName(String username);

    void updatePassword(String username, String newPassword);

    Optional<User> getUserByUsername(String username);

//    void createUserAndCustomer(String username, String password, Roles customer, String customerName, String phone, String mail);

    boolean deleteById(long id);

    List<User> getAll();

    boolean patchUser(long id, String username, PasswordPair psPair);
}
