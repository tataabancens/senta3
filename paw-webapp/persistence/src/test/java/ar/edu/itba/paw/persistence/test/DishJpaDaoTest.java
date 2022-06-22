package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.persistence.jpa.DishJpaDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import org.junit.Assert;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:sql/schema.sql")
@Transactional
public class DishJpaDaoTest {

    @Autowired
    private DishJpaDao dishDao;

    @Rollback
    @Test
    public void testGetDishById() {
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Dish> maybeDish = dishDao.getDishById(1);

        // 3. PostCondiciones
        Assert.assertTrue(maybeDish.isPresent());
        Assert.assertEquals(1, maybeDish.get().getId());
    }

    @Rollback
    @Test
    public void testGetDishById_invalid() {
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Dish> dish = dishDao.getDishById(999);

        // 3. PostCondiciones
        Assert.assertFalse(dish.isPresent());
    }

    @Rollback
    @Test
    public void testGetRecommendedDish() {
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Dish> dish = dishDao.getRecommendedDish(2);

        // 3. PostCondiciones
        Assert.assertTrue(dish.isPresent());
        Assert.assertEquals(2, dish.get().getId());
    }

    @Test
    @Rollback
    public void testGetRecommendedDish_Empty() {
        // 1. Precondiciones


        // 2. Ejercitacion
        Optional<Dish> dish = dishDao.getRecommendedDish(3L);

        // 3. PostCondiciones
        Assert.assertFalse(dish.isPresent());
    }

}
