package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.DishCategory;

import java.util.Optional;

public interface RestaurantService {

    Optional<Restaurant> getRestaurantById(long id);

    Restaurant create(String restaurantName, String phone, String mail);

    void updateRestaurantHourAndTables(Restaurant restaurant, int newMaxTables, int openHour, int closeHour);

    Dish createDish(Restaurant restaurant, String dishName, String dishDescription, double price, long imageId, DishCategory category);

    void deleteDish(Restaurant restaurant, long dishId);

    Optional<DishCategory> getDishCategoryByName(String category);

    DishCategory createDishCategory(Restaurant restaurant, String categoryName);

     Optional<DishCategory> getDishCategoryById(long categoryId);

    void deleteCategory(Restaurant restaurant, long categoryId);

    boolean patchRestaurant(long id, String restaurantName, String phone, String mail, Integer totalChairs, Integer openHour, Integer closeHour);

    boolean patchDishCategory(long restaurantId, long categoryId, String newName);
}
