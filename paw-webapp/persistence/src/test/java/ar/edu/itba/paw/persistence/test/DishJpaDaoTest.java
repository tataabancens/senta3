package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.persistence.jpa.DishJpaDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.edu.itba.paw.persistence.jpa.UserJpaDao;

import org.junit.Assert;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:sql/schema.sql")
@Transactional
public class DishJpaDaoTest {

    private static final long USERID_EXISTS = 1;
    private static final long USERID_NOT_EXISTS = 9999;

    private static final long CUSTOMERID_EXISTS = 1;

    private static final String USERNAME_EXIST = "Juancho";
    private static final String USERNAME_NOT_EXIST = "Juancho el inexistente";

    private static final String CATEGORY_EXIST = "testCategory";
    private static final String DISH_EXIST = "testFood";
    private static final String DISH_EXIST_DESC = "testDescription";



    @Autowired
    private DishJpaDao dishDao;

//    @Autowired
//    private UserJpaDao userDao;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsertCustomer;
    private SimpleJdbcInsert jdbcInsertUser;

    @PersistenceContext
    private EntityManager em;


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
