package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.model.Restaurant;
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
        if(id != 1){
            return Optional.empty();
        }
        Optional<Restaurant> ret = Optional.ofNullable(em.find(Restaurant.class, id));
        return ret;
    }

    @Override
    public Restaurant create(String restaurantName, String phone, String mail) {
        final Restaurant restaurant = new Restaurant(restaurantName, phone, mail, 20, 9, 13, 100);
        em.persist(restaurant);
        return restaurant;
    }

    @Override
    public Optional<Restaurant> getRestaurantByUsername(String username) {
        final TypedQuery<Restaurant> query = em.createQuery("from Restaurant as r where r.user.username = :username", Restaurant.class); //es hql, no sql
        query.setParameter("username", username);
        final List<Restaurant> list = query.getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.ofNullable(list.get(0));
    }
}
