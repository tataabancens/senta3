package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.persistance.DishDao;
import ar.edu.itba.paw.persistance.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class DishServiceImpl implements DishService {

    private final DishDao dishDao;
    private final ImageDao imageDao;

    @Autowired
    public  DishServiceImpl(final DishDao dishDao, final ImageDao imageDao){
        this.dishDao = dishDao;
        this.imageDao = imageDao;
    }

    @Override
    public Optional<Dish> getDishById(long id) {
        return dishDao.getDishById(id);
    }

    @Transactional
    @Override
    public void updateDish(Dish dish, String dishName, String dishDescription, double price, DishCategory category) {
        dish.setDishName(dishName);
        dish.setDishDescription(dishDescription);
        dish.setPrice((int) price);
        dish.setCategory(category);
    }

    @Transactional
    @Override
    public void updateDishPhoto(long dishId, CommonsMultipartFile photo) throws IOException {
        long imageId = imageDao.create(photo);
        Optional<Dish> maybeDish = dishDao.getDishById(dishId);
        if(maybeDish.isPresent()) {
            Dish dish = maybeDish.get();
            if(dish.getImageId() > 1) {
                imageDao.deleteImageById(dish.getImageId());
            }
            dish.setImageId(imageId);
        }
    }

    @Override
    public void deleteDish(long dishId) {
        dishDao.deleteDish(dishId);
    }

    @Override
    public Optional<Dish> getRecommendedDish(long reservationId) {
        return dishDao.getRecommendedDish(reservationId);
    }

    @Override
    public boolean isPresent(Dish recommendedDish) {
        return recommendedDish != null;
    }
}
