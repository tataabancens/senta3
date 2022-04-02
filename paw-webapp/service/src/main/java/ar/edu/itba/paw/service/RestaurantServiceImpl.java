package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.persistance.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantDao restaurantDao;

    @Autowired
    public RestaurantServiceImpl(final RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    @Override
    public Optional<Restaurant> getRestaurantById(long id) {
        return restaurantDao.getRestaurantById(id);
    }

    @Override
    public Restaurant create(String restaurantName, String phone, String mail) {
        return null;
    }

    @Override
    public List<Dish> getRestaurantDishes(long id) {
        return restaurantDao.getRestaurantDishes(id);
    }
}
