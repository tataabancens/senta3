package ar.edu.itba.paw.persistance;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.DishCategory;

import java.util.Optional;

public interface DishCategoryDao {

    Optional<DishCategory> getDishCategoryById(long id);

    Optional<DishCategory> getDishCategoryByName(String categoryName);
}
