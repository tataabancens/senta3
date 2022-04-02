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
    public Dish create(String dishName, int price){
        return null;
    }

}
