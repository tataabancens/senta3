package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.DishCategory;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    Optional<Restaurant> getRestaurantById(long id);

    Restaurant create(String restaurantName, String phone, String mail);

    List<Dish> getRestaurantDishes(long id);

    void updateRestaurantHourAndTables(long restaurantId, int newMaxTables, int openHour, int closeHour);

    void updateRestaurantName(String name, long restaurantId);

    void updateRestaurantEmail(String mail, long restaurantId);

    void updatePhone(String phone, long restaurantId);

    Optional<Restaurant> getRestaurantByUsername(String username);

    List<Dish> getRestaurantDishesByCategory(long restaurantId, DishCategory category);
}
