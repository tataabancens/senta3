package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.enums.DishCategory;
import ar.edu.itba.paw.persistance.DishDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class DishJpaDao implements DishDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Dish> getDishById(long id) {
        return Optional.of(em.find(Dish.class, id));
    }

    @Override
    public Dish create(long restaurantId, String dishName, String dishDescription, double price, long imageId, DishCategory category) {
        return null;
    }

    @Override
    public void updateDish(long dishId, String dishName, String dishDescription, double price, DishCategory category, long restaurantId) {

    }

    @Override
    public void updateDishPhoto(long dishId, long imageId) {

    }

    @Override
    public void deleteDish(long dishId) {

    }

    @Override
    public Optional<Dish> getRecommendedDish(long reservationId) {
        return Optional.empty();
    }
}
