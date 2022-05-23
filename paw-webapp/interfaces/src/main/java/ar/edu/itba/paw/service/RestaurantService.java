package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.DishCategory;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    Optional<Restaurant> getRestaurantById(long id);

    Restaurant create(String restaurantName, String phone, String mail);

    void updateRestaurantHourAndTables(Restaurant restaurant, int newMaxTables, int openHour, int closeHour);

    void updateRestaurantName(Restaurant restaurant, String name);

    void updateRestaurantEmail(Restaurant restaurant, String mail);

    public void updatePhone(Restaurant restaurant, String phone);

    Optional<Restaurant> getRestaurantByUsername(String username);

    Dish createDish(Restaurant restaurant, String dishName, String dishDescription, double price, long imageId, DishCategory category);

    void deleteDish(Restaurant restaurant, long dishId);
}
