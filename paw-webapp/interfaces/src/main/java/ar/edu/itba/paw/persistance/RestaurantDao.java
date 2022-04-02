package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface RestaurantDao {

    Optional<User> getRestaurantById(long id);

    User create(String restaurantName, String phone, String mail);
}
