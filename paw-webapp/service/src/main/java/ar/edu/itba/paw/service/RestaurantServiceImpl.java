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

    @Override
    public void updateRestaurantHourAndTables(long restaurantId, int newMaxTables, int newOpenHour, int newCloseHour) {
        restaurantDao.updateRestaurantHourAndTables(restaurantId, newMaxTables, newOpenHour, newCloseHour);
    }

    @Override
    public void updateRestaurantName(String name, long restaurantId) {
        restaurantDao.updateRestaurantName(name, restaurantId);
    }

    @Override
    public void updateRestaurantEmail(String mail, long restaurantId) {
        restaurantDao.updateRestaurantEmail(mail, restaurantId);
    }

    @Override
    public void updatePhone(String phone, long restaurantId) {
        restaurantDao.updatePhone(phone, restaurantId);
    }

    @Override
    public Optional<Restaurant> getRestaurantByUsername(String username) {
        return restaurantDao.getRestaurantByUsername(username);
    }
}
