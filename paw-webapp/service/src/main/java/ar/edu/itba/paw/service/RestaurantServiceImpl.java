package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.model.exceptions.CategoryNameAlreadyExistsException;
import ar.edu.itba.paw.persistance.DishCategoryDao;
import ar.edu.itba.paw.persistance.RestaurantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public DishCategory createDishCategory(Restaurant restaurant, String categoryName) {
        if (restaurant.getDishCategories().stream().anyMatch(dishCategory -> dishCategory.getName().equals(categoryName))) {
            throw new CategoryNameAlreadyExistsException();
        }
        return restaurant.createDishCategory(categoryName);
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

    @Transactional
    @Override
    public boolean patchRestaurant(long id, String restaurantName, String phone, String mail, Integer totalChairs, Integer openHour, Integer closeHour, Integer pointsForDiscount) {
        Optional<Restaurant> maybeRestaurant = restaurantDao.getRestaurantById(id);
        if(!maybeRestaurant.isPresent()){
            return false;
        }
        Restaurant restaurant = maybeRestaurant.get();

        if(restaurantName != null){
            restaurant.setRestaurantName(restaurantName);
        }
        if(phone != null){
            restaurant.setPhone(phone);
        }
        if(mail != null){
            restaurant.setMail(mail);
        }
        if(totalChairs != null){
            restaurant.setTotalChairs(totalChairs);
        }
        if(openHour != null){
            restaurant.setOpenHour(openHour);
        }
        if(closeHour != null){
            restaurant.setCloseHour(closeHour);
        }
        if(pointsForDiscount != null){
            restaurant.setPointsForDiscount(pointsForDiscount);
        }
        return true;
    }

    @Transactional
    @Override
    public boolean patchDishCategory(long restaurantId, long categoryId, String newName){
        Optional<Restaurant> maybeRestaurant = getRestaurantById(restaurantId);
        Optional<DishCategory> maybeCategory = getDishCategoryById(categoryId);
        if (!maybeRestaurant.isPresent() || !maybeCategory.isPresent() || maybeCategory.get().getRestaurant().getId() != restaurantId) {
            return false;
        }

        maybeCategory.get().setName(newName);
        return true;
    }
}
