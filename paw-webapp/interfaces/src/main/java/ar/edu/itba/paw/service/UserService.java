package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserByID(long id);

    User create(String username, String password);
}
