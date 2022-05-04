package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Roles;
import ar.edu.itba.paw.model.User;

import java.util.Optional;

// DAO = Data Access Object
public interface UserDao {

    Optional<User> getUserById(long id);

    User create(String username, String password, Roles role);

    Optional<User> findByName(String username);

    void updatePassword(String username, String newPassword);

    void updateUsername(String oldUsername, String newUsername);
}
