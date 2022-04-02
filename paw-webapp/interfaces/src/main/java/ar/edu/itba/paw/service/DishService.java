package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Dish;

import java.util.Optional;

public interface DishService {

    Optional<Dish> getDishById(long id);

    Dish create(String dishName, int price);
}
