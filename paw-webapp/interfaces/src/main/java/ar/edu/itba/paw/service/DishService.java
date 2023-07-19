package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DishService {

    Optional<Dish> getDishById(long id);

    boolean patchDish(long dishId, String dishName, String dishDescription, Integer price, Long categoryId, Long imageId);

    void updateDishPhoto(long dishId, CommonsMultipartFile imageId) throws IOException;

    void deleteDish(long dishId);

    Optional<Dish> getRecommendedDish(String securityCode);

    boolean isPresent(Dish recommendedDish);

    Optional<List<Dish>> getDishes(long restaurantId, String dishCategory);
}
