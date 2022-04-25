package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.persistance.DishDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DishServiceImpl implements DishService{

    private DishDao dishDao;

    @Autowired
    public  DishServiceImpl(final DishDao dishDao){
        this.dishDao = dishDao;
    }

    @Override
    public Optional<Dish> getDishById(long id) {
        return dishDao.getDishById(id);
    }

    @Override
    public Dish create(long restaurantId, String dishName, String dishDescription, double price){
        return dishDao.create(restaurantId, dishName, dishDescription, price);
    }

    @Override
    public void updateDish(long dishId, String dishName, String dishDescription, double price, long restaurantId) {
        dishDao.updateDish(dishId, dishName, dishDescription, price, restaurantId);
    }

    @Override
    public void deleteDish(long dishId) {
        dishDao.deleteDish(dishId);
    }


}
