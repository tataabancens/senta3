package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.persistance.DishCategoryDao;
import ar.edu.itba.paw.persistance.DishDao;
import ar.edu.itba.paw.persistance.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DishServiceImpl implements DishService {

    private final DishDao dishDao;
    private final ImageDao imageDao;
    private final DishCategoryDao dishCategoryDao;
    private final RestaurantService restaurantService;
    private final ReservationService reservationService;


    @Autowired
    public  DishServiceImpl(final DishDao dishDao, final ImageDao imageDao, DishCategoryDao dishCategoryDao, RestaurantService restaurantService, ReservationService reservationService){
        this.dishDao = dishDao;
        this.imageDao = imageDao;
        this.dishCategoryDao = dishCategoryDao;
        this.restaurantService = restaurantService;
        this.reservationService = reservationService;
    }

    @Override
    public Optional<Dish> getDishById(long id) {
        return dishDao.getDishById(id);
    }


    @Transactional
    @Override
    public boolean patchDish(long dishId, String dishName, String dishDescription, Integer price, Long categoryId, Long imageId) {
        Optional<Dish> maybeDish = dishDao.getDishById(dishId);
        if(!maybeDish.isPresent()){
            return false;
        }
        Dish dish = maybeDish.get();
        if(dishName != null){
            dish.setDishName(dishName);
        }
        if(dishDescription != null){
            dish.setDishDescription(dishDescription);
        }
        if(price != null){
            dish.setPrice(price);
        }
        if(categoryId != null){
            Optional<DishCategory> maybeCat = dishCategoryDao.getDishCategoryById(categoryId);
            if(!maybeCat.isPresent()){
                return false;
            }
            dish.setCategory(maybeCat.get());
        }
        if(imageId != null){
            dish.setImageId(imageId);
        }
        return true;
    }

    @Transactional
    @Override
    public void updateDishPhoto(long dishId, CommonsMultipartFile photo) throws IOException {
//        long imageId = imageDao.create(photo);
//        Optional<Dish> maybeDish = dishDao.getDishById(dishId);
//        if(maybeDish.isPresent()) {
//            Dish dish = maybeDish.get();
//            if(dish.getImageId() > 1) {
//                imageDao.deleteImageById(dish.getImageId());
//            }
//            dish.setImageId(imageId);
//        }
    }

    @Override
    public void deleteDish(long dishId) {
        dishDao.deleteDish(dishId);
    }

    @Override
    public Optional<Dish> getRecommendedDish(String securityCode) {
        Optional<Reservation> res = reservationService.getReservationBySecurityCode(securityCode);
        if(!res.isPresent()){
            return Optional.empty();
        }
        return dishDao.getRecommendedDish(res.get().getId());
    }

    @Override
    public boolean isPresent(Dish recommendedDish) {
        return recommendedDish != null;
    }

    @Override
    public Optional<List<Dish>> getDishes(long restaurantId, String dishCategory){
        final Optional<Restaurant> maybeRestaurant = restaurantService.getRestaurantById(restaurantId);
        if (!maybeRestaurant.isPresent()) {
            return Optional.empty();
        }
        if(Objects.equals(dishCategory, "")) {
            return Optional.ofNullable(maybeRestaurant.get().getDishes());
        }
        Optional<DishCategory> maybeCategory = restaurantService.getDishCategoryByName(dishCategory);
        return maybeCategory.map(category -> maybeRestaurant.get().getDishesByCategory(category));

    }
}
