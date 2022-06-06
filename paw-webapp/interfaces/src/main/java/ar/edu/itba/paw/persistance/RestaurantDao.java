package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.DishCategory;

import java.util.List;
import java.util.Optional;

public interface RestaurantDao {

    Optional<Restaurant> getRestaurantById(long id);

    Restaurant create(String restaurantName, String phone, String mail);

    List<Dish> getRestaurantDishes(long restaurantId);

    Optional<Restaurant> getRestaurantByUsername(String username);

    List<Dish> getRestaurantDishesByCategory(long restaurantId, DishCategory category);

}
