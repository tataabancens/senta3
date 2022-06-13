package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.DishCategory;

import java.util.Optional;

public interface DishService {

    Optional<Dish> getDishById(long id);

    void updateDish(Dish dish, String dishName, String dishDescription, double price, DishCategory category);

    void updateDishPhoto(long dishId, long imageId);

    void deleteDish(long dishId);

    Optional<Dish> getRecommendedDish(long reservationId);

    boolean isPresent(Dish recommendedDish);
}
