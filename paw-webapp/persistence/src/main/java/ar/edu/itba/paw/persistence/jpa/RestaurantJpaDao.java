package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.DishCategory;
import ar.edu.itba.paw.persistance.RestaurantDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
    public Optional<Restaurant> getRestaurantByUsername(String username) {
        final TypedQuery<Restaurant> query = em.createQuery("from Restaurant as r where r.user.username = :username", Restaurant.class); //es hql, no sql
        query.setParameter("username", username);
        final List<Restaurant> list = query.getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.ofNullable(list.get(0));
    }

    @Override
    public List<Dish> getRestaurantDishesByCategory(long restaurantId, DishCategory category) {
        return null;
    }
}