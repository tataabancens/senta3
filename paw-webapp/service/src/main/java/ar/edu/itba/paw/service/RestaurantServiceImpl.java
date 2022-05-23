package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.DishCategory;
import ar.edu.itba.paw.persistance.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantDao restaurantDao;

    @Autowired
    public RestaurantServiceImpl(final RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
    }

    @Override
    public Optional<Restaurant> getRestaurantById(long id) {
        return restaurantDao.getRestaurantById(id);
    }

    @Transactional
    @Override
    public Restaurant create(String restaurantName, String phone, String mail) {
        return restaurantDao.create(restaurantName, phone, mail);
    }

    @Transactional
    @Override
    public void updateRestaurantHourAndTables(Restaurant restaurant, int newTotalChairs, int newOpenHour, int newCloseHour) {
        restaurant.setTotalChairs(newTotalChairs);
        restaurant.setOpenHour(newOpenHour);
        restaurant.setCloseHour(newCloseHour);
    }

    @Transactional
    @Override
    public void updateRestaurantName(Restaurant restaurant, String name) {
        restaurant.setRestaurantName(name);
    }

    @Transactional
    @Override
    public void updateRestaurantEmail(Restaurant restaurant, String mail) {
        restaurant.setMail(mail);
    }

    @Transactional
    @Override
    public void updatePhone(Restaurant restaurant, String phone) {
        restaurant.setPhone(phone);
    }

    @Transactional
    @Override
    public Optional<Restaurant> getRestaurantByUsername(String username) {
        return restaurantDao.getRestaurantByUsername(username);
    }

    @Transactional
    @Override
    public Dish createDish(Restaurant restaurant, String dishName, String dishDescription, double price, long imageId, DishCategory category){
        return restaurant.createDish(dishName, dishDescription, price, imageId, category);
    }

    @Transactional
    @Override
    public void deleteDish(Restaurant restaurant, long dishId) {
        restaurant.deleteDish(dishId);
    }
}
