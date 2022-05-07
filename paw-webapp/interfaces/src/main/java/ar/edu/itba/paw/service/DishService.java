package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.enums.DishCategory;

import java.util.Optional;

public interface DishService {

    Optional<Dish> getDishById(long id);

    Dish create(long restaurantId, String dishName, String dishDescription, double price, long imageId, DishCategory category);

    void updateDish(long dishId, String dishName, String dishDescription, double price, DishCategory category, long restaurantId);

    void updateDishPhoto(long dishId, long imageId);

    void deleteDish(long dishId);

    Dish getRecommendedDish(long reservationId);
}
