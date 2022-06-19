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

//    @Rollback
//    @Test
//    public void testGetRecommendedDish() {
//        // 1. Precondiciones
////        Number customerId1 = insertCustomer("marcos", "54112457896", "marcospicapiedras@gmail.com");
////        Number customerId2 = insertCustomer("pedro", "54112457896", "pedropicapiedras@gmail.com");
////        Number reservationId1 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.OPEN.ordinal(), 1);
////        Number reservationId2 = insertReservation(1, 12, customerId2.intValue(), ReservationStatus.OPEN.ordinal(), 1);
////        Number dishId1 = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
////        Number dishId2 = insertDish("Empanada2", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
////        Number dishId3 = insertDish("Empanada3", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
////        insertOrderItem(dishId1.intValue(), reservationId1.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
////        insertOrderItem(dishId2.intValue(), reservationId1.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
////        insertOrderItem(dishId1.intValue(), reservationId2.intValue(), 100, 1, OrderItemStatus.SELECTED.ordinal());
//
//
//        // 2. Ejercitacion
//        Optional<Dish> dish = dishDao.getRecommendedDish(2);
//
//        // 3. PostCondiciones
//        Assert.assertTrue(dish.isPresent());
//        Assert.assertEquals(2, dish.get().getId());
//    }

//    @Test
//    @Rollback
//    public void testGetRecommendedDish_Empty() {
//        // 1. Precondiciones
//        cleanAllTables();
//        Number customerId1 = insertCustomer("marcos", "54112457896", "marcospicapiedras@gmail.com");
//        Number customerId2 = insertCustomer("pedro", "54112457896", "pedropicapiedras@gmail.com");
//        Number reservationId1 = insertReservation(1, 12, customerId1.intValue(), ReservationStatus.OPEN.ordinal(), 1);
//        Number reservationId2 = insertReservation(1, 12, customerId2.intValue(), ReservationStatus.OPEN.ordinal(), 1);
//        Number dishId1 = insertDish("Empanada", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
//        Number dishId2 = insertDish("Empanada2", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
//        Number dishId3 = insertDish("Empanada3", "sin pasas de uva", 100, 1, 1, MAIN_DISH);
//        insertOrderItem(dishId1.intValue(), reservationId1.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
//        insertOrderItem(dishId2.intValue(), reservationId1.intValue(), 100, 1, OrderItemStatus.ORDERED.ordinal());
//
//
//        // 2. Ejercitacion
//        Optional<Dish> dish = dishDao.getRecommendedDish(reservationId2.longValue());
//
//        // 3. PostCondiciones
//        Assert.assertFalse(dish.isPresent());
//    }
//
}
