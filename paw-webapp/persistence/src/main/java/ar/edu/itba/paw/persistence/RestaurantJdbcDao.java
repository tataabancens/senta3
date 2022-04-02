package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistance.RestaurantDao;

import java.util.Optional;

public class RestaurantJdbcDao implements RestaurantDao {
    @Override
    public Optional<User> getRestaurantById(long id) {
        return Optional.empty();
    }

    @Override
    public User create(String restaurantName, String phone, String mail) {
        return null;
    }
}
