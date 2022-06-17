package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.persistance.DishCategoryDao;
import ar.edu.itba.paw.persistance.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantDao restaurantDao;
    private final DishCategoryDao dishCategoryDao;

    @Autowired
    public RestaurantServiceImpl(final RestaurantDao restaurantDao, final DishCategoryDao dishCategoryDao) {
        this.restaurantDao = restaurantDao;
        this.dishCategoryDao = dishCategoryDao;
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

    @Override
    public Optional<DishCategory> getDishCategoryByName(String category) {
        return dishCategoryDao.getDishCategoryByName(category);
    }

    @Transactional
    @Override
    public void createDishCategory(Restaurant restaurant, String categoryName) {
        restaurant.createDishCategory(categoryName);
    }

    @Transactional
    @Override
    public void editDishCategory(DishCategory dishCategory, String categoryName) {
        dishCategory.setName(categoryName);
    }

    @Override
    public Optional<DishCategory> getDishCategoryById(long categoryId) {
        return dishCategoryDao.getDishCategoryById(categoryId);
    }

    @Transactional
    @Override
    public void deleteCategory(Restaurant restaurant, long categoryId) {
        restaurant.deleteCategory(categoryId);
    }
}
