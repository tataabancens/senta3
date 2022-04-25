package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;

import java.util.Optional;

public interface DishService {

    Optional<Dish> getDishById(long id);

    Dish create(long restaurantId, String dishName, String dishDescription, double price, long imageId);

    void updateDish(long dishId, String dishName, String dishDescription, double price, long restaurantId);

    void updateDishPhoto(long dishId, long imageId);

    void deleteDish(long dishId);
}
