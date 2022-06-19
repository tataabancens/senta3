package ar.edu.itba.paw.persistence.test;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.persistence.jpa.CustomerJpaDao;
import ar.edu.itba.paw.persistence.jpa.RestaurantJpaDao;
import ar.edu.itba.paw.persistence.jpa.UserJpaDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:sql/schema.sql")
@Transactional
public class RestaurantJpaDaoTest {

    @Autowired
    private RestaurantJpaDao restaurantDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Rollback
    public void testGetRestaurantById(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Restaurant> restaurant = restaurantDao.getRestaurantById(1L);

        // 3. PostCondiciones
        Assert.assertTrue(restaurant.isPresent());
    }

    @Test
    @Rollback
    public void testGetRestaurantById_notExists(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Restaurant> restaurant = restaurantDao.getRestaurantById(99L);

        // 3. PostCondiciones
        Assert.assertFalse(restaurant.isPresent());
    }

    @Test
    @Rollback
    public void getRestaurantByUsername(){
        // 1. Precondiciones

        // 2. Ejercitacion
        Optional<Restaurant> rest = restaurantDao.getRestaurantByUsername("Pepito");

        // 3. PostCondiciones
        Assert.assertTrue(rest.isPresent());
        Assert.assertEquals("Pepito", rest.get().getUser().getUsername());
        Assert.assertEquals("Pepito masterchef", rest.get().getRestaurantName());
    }
}
