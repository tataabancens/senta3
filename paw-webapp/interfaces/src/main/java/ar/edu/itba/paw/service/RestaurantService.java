package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    Optional<Restaurant> getRestaurantById(long id);

    Restaurant create(String restaurantName, String phone, String mail);

    List<Dish> getRestaurantDishes(long id);

    List<Integer> getAvailableHours(long restaurantId);
}
