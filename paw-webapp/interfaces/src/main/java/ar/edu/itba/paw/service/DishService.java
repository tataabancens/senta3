package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.DishCategory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface DishService {

    Optional<Dish> getDishById(long id);

    void updateDish(Dish dish, String dishName, String dishDescription, double price, DishCategory category);

    void updateDishPhoto(long dishId, CommonsMultipartFile imageId) throws IOException;

    void deleteDish(long dishId);

    Optional<Dish> getRecommendedDish(long reservationId);

    boolean isPresent(Dish recommendedDish);
}
