package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.DishCategory;

import java.util.Optional;

public interface DishDao {

    Optional<Dish> getDishById(long id);

    void updateDishPhoto(long dishId, long imageId);

    void deleteDish(long dishId);

    Optional<Dish> getRecommendedDish(long reservationId);
}
