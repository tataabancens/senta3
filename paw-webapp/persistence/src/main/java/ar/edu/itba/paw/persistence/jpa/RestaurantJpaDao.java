package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.DishCategory;
import ar.edu.itba.paw.persistance.RestaurantDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class RestaurantJpaDao implements RestaurantDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Restaurant> getRestaurantById(long id) {
        return Optional.of(em.find(Restaurant.class, id));
    }

    @Override
    public Restaurant create(String restaurantName, String phone, String mail) {
        final Restaurant restaurant = new Restaurant(restaurantName, phone, mail, 20, 9, 13);
        em.persist(restaurant);
        return restaurant;
    }

    @Override
    public List<Dish> getRestaurantDishes(long restaurantId) {
        return null;
    }

    @Override
    public void updateRestaurantHourAndTables(long restaurantId, int newMaxTables, int newOpenHour, int newCloseHour) {

    }

    @Override
    public void updateRestaurantName(String name, long restaurantId) {

    }

    @Override
    public void updateRestaurantEmail(String mail, long restaurantId) {

    }

    @Override
    public void updatePhone(String phone, long restaurantId) {

    }

    @Override
    public Optional<Restaurant> getRestaurantByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public List<Dish> getRestaurantDishesByCategory(long restaurantId, DishCategory category) {
        return null;
    }
}
