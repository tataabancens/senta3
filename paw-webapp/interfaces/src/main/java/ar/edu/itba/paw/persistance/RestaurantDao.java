package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Restaurant;
import java.util.Optional;

public interface RestaurantDao {

    Optional<Restaurant> getRestaurantById(long id);

    Restaurant create(String restaurantName, String phone, String mail);
}
